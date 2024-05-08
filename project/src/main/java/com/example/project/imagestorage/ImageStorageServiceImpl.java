package org.zerogravitysolutions.digitalschool.imagestorage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.zerogravitysolutions.digitalschool.configs.ImageProperties;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {

    private ImageProperties imageProperties;

    private final Logger logger = LoggerFactory.getLogger(ImageStorageServiceImpl.class);

    public ImageStorageServiceImpl(ImageProperties imageProperties) {
        this.imageProperties = imageProperties;
    }

    @Override
    public String saveImage(MultipartFile file, String existingFileName) {

        logger.info("Trying to save images on storage with this file name: {}", existingFileName);
        try {

            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            String fileName;

            if(existingFileName != null && !existingFileName.isEmpty()) {

                logger.info("File name already existed and used as it is: {}", existingFileName);
                fileName = existingFileName;
            } else {

                fileName = generateUniqueFileName(Objects.requireNonNull(file.getOriginalFilename()));
                logger.info("New file name has been gnerated {}", fileName);
            }

            for(ImageSize imageSize : ImageSize.values()) {

                // image/small/123412321432123123.jpeg
                String imagePath = imageProperties.getStoragePath() + imageSize.name().toLowerCase() + "/" + fileName;
                
                BufferedImage resizedImage = resizeImage(originalImage, imageSize);

                logger.info("Trying to save image with name {}, at path: {}", fileName, imagePath);
                saveImageToStorage(resizedImage, imagePath);
            }

            return fileName;

        } catch (IllegalArgumentException iae) {

            logger.error("Invalid image dimensions {}", iae);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid image", iae);
        } catch (IOException e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save image", e);
        }
    }

    private String generateUniqueFileName(String originalFileName) {

        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // Generate a random UUID
        UUID uuid = UUID.randomUUID();

        return uuid.toString() + fileExtension;
    }

    private BufferedImage resizeImage(BufferedImage originImage, ImageSize imageSize) {

        int targetWidth = imageProperties.getTargetWidth(imageSize);
        int targetHeight = imageProperties.getTargetHeight(imageSize);

        if(targetWidth == 0 || targetHeight == 0) {
            return originImage;
        }

        try {

            BufferedImage resizedImage = Thumbnails.of(originImage)
                .size(targetWidth, targetHeight)
                .keepAspectRatio(true)
                .asBufferedImage();

            return resizedImage;

        } catch (IllegalArgumentException iae) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid image", iae);
        } catch (IOException e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save image", e);
        }
    }

    private void saveImageToStorage(BufferedImage image, String imagePath) {

        Path path = Paths.get(imagePath);

        try {

            Files.createDirectories(path.getParent());

            ImageIO.write(image, "jpeg", path.toFile());
        } catch (SecurityException se) {

            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permission denied to save image to selected path.", se);
        } catch (DirectoryNotEmptyException dnee) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server errror");
        } catch (IOException e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save image", e);
        }
    }

    @Override
    public Resource loadImage(String fileName, ImageSize imageSize) {
        
        // image/small/123412321432123123.jpeg
        String imagePath = imageProperties.getStoragePath() + imageSize.name().toLowerCase() + "/" + fileName;
        Path path = Paths.get(imagePath);

        try {
            Resource resource = new UrlResource(path.toUri());

            if(resource.exists() && resource.isReadable()) {

                return resource;
            } else {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found.");
            }
        } catch (MalformedURLException e) {
            
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to load image", e);
        }
    }

    @Override
    public void delete(String fileName) {
        
        // image/small/123412321432123123.jpeg
        for(ImageSize imageSize : ImageSize.values()) {

            String imagePath = imageProperties.getStoragePath() + imageSize.name().toLowerCase() + '/' + fileName;
            Path path = Paths.get(imagePath);

            if(Files.exists(path)) {

                try {
                    Files.delete(path);
                } catch (IOException e) {
                    
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete image", e);
                }
            } else {

                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
            }
        }
    }
}
