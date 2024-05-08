package org.zerogravitysolutions.digitalschool.imagestorage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    
    String saveImage(MultipartFile file, String existingFileName);
    Resource loadImage(String fileName, ImageSize imageSize);
    void delete(String fileName);
}
