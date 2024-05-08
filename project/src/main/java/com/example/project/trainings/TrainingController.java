package org.zerogravitysolutions.digitalschool.trainings;

import java.util.List;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerogravitysolutions.digitalschool.imagestorage.ImageSize;

@RestController
public class TrainingController {

    private TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping(path = "/trainings/{id}")
    public ResponseEntity<TrainingEntity> findById(@PathVariable(name = "id") Long id) {
        TrainingEntity foundTraining = trainingService.findById(id);
        return ResponseEntity.ok(foundTraining);
    }

    @GetMapping(path = "/trainings")
    public ResponseEntity<List<TrainingEntity>> findAll() {

        List<TrainingEntity> trainings = trainingService.findAll();
        return ResponseEntity.ok(trainings);
    }

    @PostMapping(path = "/trainings")
    public ResponseEntity<TrainingEntity> save(@RequestBody TrainingEntity trainingEntity) {

        TrainingEntity saved = trainingService.save(trainingEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping(path = "/trainings",params = "search")
    public ResponseEntity<List<TrainingEntity>> search(@RequestParam("search")String keyword){
        List<TrainingEntity> searchedTraining = trainingService.search(keyword);
        return ResponseEntity.ok(searchedTraining);

        //http://localhost:8085/trainings?search=
    }

    @PutMapping(path = "/trainings/{id}")
    public ResponseEntity<TrainingEntity> update(@PathVariable(name = "id") Long id ,@RequestBody TrainingEntity trainingEntity){
        if(id==null || trainingEntity==null){
            return ResponseEntity.notFound().build();
        }

//        if(id.equals(trainingEntity.getId())){
//
//        }
//
//        trainingEntity.setId(id);
//        in case if you do not send the id in the request body

        return ResponseEntity.ok(trainingService.update(id,trainingEntity));
    }

    @PatchMapping(path = "/trainings/{id}")
    public ResponseEntity<TrainingDto> patch(@PathVariable(name = "id") Long id, @RequestBody TrainingDto trainingDto){
        TrainingDto patched = trainingService.patch(id,trainingDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(patched);
    }

    @DeleteMapping(path = "/trainings/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id){
        trainingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/trainings/{id}/cover")
    public ResponseEntity<TrainingEntity> uploadCover(@PathVariable(name = "id") Long id, @RequestParam("file") MultipartFile cover) {

        return ResponseEntity.ok(trainingService.uploadCover(id, cover));
    }

    @GetMapping(path = "/trainings/{id}/cover")
    public ResponseEntity<Resource> readCover(@PathVariable(name = "id") Long id, @RequestParam(name = "size") ImageSize size) {

        Resource resource = trainingService.readCover(id, size);

        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(resource);
    }

    @DeleteMapping(path = "/trainings/{id}/cover")
    public ResponseEntity<TrainingEntity> removeCover(@PathVariable Long id) {

        TrainingEntity trainingEntity = trainingService.removeCover(id);
        
        return ResponseEntity.ok(trainingEntity);
    }
}
