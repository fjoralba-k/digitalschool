package org.zerogravitysolutions.digitalschool.trainings;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.zerogravitysolutions.digitalschool.imagestorage.ImageSize;
import org.zerogravitysolutions.digitalschool.imagestorage.ImageStorageService;
import org.zerogravitysolutions.digitalschool.trainings.commons.TrainingMapper;

@Service
public class TrainingServiceImpl implements TrainingService {

    private TrainingRepository trainingRepository;
    private TrainingMapper trainingMapper;
    private ImageStorageService imageStorageService;

    public TrainingServiceImpl(TrainingRepository trainingRepository, TrainingMapper trainingMapper, ImageStorageService imageStorageService) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public TrainingEntity findById(Long id) {
        
        return trainingRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Training with id " + id + " is not found."));
    }

    @Override
    public List<TrainingEntity> findAll() {
        
        return trainingRepository.findAll();
    }

    @Override
    public TrainingEntity save(TrainingEntity trainingEntity) {
        
        return trainingRepository.save(trainingEntity);
    }

    @Override
    public List<TrainingEntity> search(String keyword) {
        return trainingRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword,keyword);
    }

    @Override
    public TrainingEntity update(Long id, TrainingEntity training) {
        trainingRepository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Training with this id is not found"
                ));
        return trainingRepository.save(training);
    }

    @Override
    public TrainingDto patch(Long id, TrainingDto trainingDto) {

        TrainingEntity trainingEntity = this.findById(id);

        trainingMapper.mapDtoToEntity(trainingDto, trainingEntity);

        TrainingEntity patched = trainingRepository.save(trainingEntity);

        return trainingMapper.mapEntityToDto(patched);

    }

    @Override
    public void delete(Long id) {
        trainingRepository.deleteById(id);
    }

    @Override
    public TrainingEntity uploadCover(Long id, MultipartFile cover) {
        
        TrainingEntity trainingEntity = trainingRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Training with id " + id + " is not found."));

        String fileName = imageStorageService.saveImage(cover, trainingEntity.getCover());

        trainingEntity.setCover(fileName);

        return trainingRepository.save(trainingEntity);
    }

    @Override
    public Resource readCover(Long id, ImageSize size) {
        
        TrainingEntity trainingEntity = trainingRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Training with id " + id + " is not found."));

        return imageStorageService.loadImage(trainingEntity.getCover(), size);
    }

    @Override
    public TrainingEntity removeCover(Long id) {
        
        TrainingEntity trainingEntity = trainingRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Training with id " + id + " is not found."));

        imageStorageService.delete(trainingEntity.getCover());

        trainingEntity.setCover(null);
        TrainingEntity updated = trainingRepository.save(trainingEntity);

        return updated;
    }
}
