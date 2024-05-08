package org.zerogravitysolutions.digitalschool.trainings;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.zerogravitysolutions.digitalschool.imagestorage.ImageSize;

public interface TrainingService {

    TrainingEntity findById(Long id);
    List<TrainingEntity> findAll();
    TrainingEntity save(TrainingEntity trainingEntity);

    List<TrainingEntity> search(String keyword);

    TrainingEntity update (Long id, TrainingEntity training);

    TrainingDto patch(Long id, TrainingDto trainingDto);

    void delete(Long id);
    TrainingEntity uploadCover(Long id, MultipartFile cover);
    Resource readCover(Long id, ImageSize size);
    TrainingEntity removeCover(Long id);
}
