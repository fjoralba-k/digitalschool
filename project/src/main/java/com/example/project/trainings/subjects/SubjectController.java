package org.zerogravitysolutions.digitalschool.trainings.subjects;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/trainings/{id}/subjects")
public class SubjectController {

    private SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<Set<SubjectDto>> getSubjectsByTrainingId(@PathVariable(name = "id") Long trainingId) {

        Set<SubjectDto> subjects = subjectService.getSubjectsByTrainingId(trainingId);

        return ResponseEntity.ok().body(subjects);
    }

    @PostMapping
    public ResponseEntity<SubjectDto> addSubjectToTraining(@PathVariable(name = "id") Long trainingId, @RequestBody @Valid SubjectDto subject) {

        SubjectDto created = subjectService.addSubjectToTraining(trainingId, subject);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping(path = "/{sid}")
    public ResponseEntity<SubjectDto> update(@PathVariable(name = "sid") Long subjectId, @RequestBody SubjectDto subject) {

        SubjectDto updated = subjectService.update(subjectId, subject);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{sid}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long subjectId) {

        subjectService.delete(subjectId);
        return ResponseEntity.noContent().build();
    }
}
