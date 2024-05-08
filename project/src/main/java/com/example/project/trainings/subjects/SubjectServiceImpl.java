package org.zerogravitysolutions.digitalschool.trainings.subjects;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.zerogravitysolutions.digitalschool.trainings.TrainingEntity;
import org.zerogravitysolutions.digitalschool.trainings.TrainingRepository;

@Service
public class SubjectServiceImpl implements SubjectService {

    private TrainingRepository trainingRepository;
    private SubjectRepository subjectRepository;
    private SubjectMapper subjectMapper;

    public SubjectServiceImpl(TrainingRepository trainingRepository, SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.trainingRepository = trainingRepository;
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public SubjectDto addSubjectToTraining(Long trainingId, SubjectDto subject) {

        TrainingEntity trainingEntity = trainingRepository.findById(trainingId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Training with id " + trainingId + " is not found."));
        
        SubjectEntity subjectEntity = subjectMapper.mapDtoToEntity(subject);
        subjectEntity.setTraining(trainingEntity);

        SubjectEntity saved = subjectRepository.save(subjectEntity);
        return subjectMapper.mapEntityToDto(saved);
    }

    @Override
    public Set<SubjectDto> getSubjectsByTrainingId(Long trainingId) {

        TrainingEntity trainingEntity = trainingRepository.findById(trainingId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Training with id " + trainingId + " is not found."));

        return subjectMapper.mapEntitiesToDtos(trainingEntity.getSubjects());
    }

    @Override
    public SubjectDto update(Long subjectId, SubjectDto subject) {
        
        SubjectEntity subjectEntity = subjectRepository.findById(subjectId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject with id " + subjectId + " is not found.")
        );

        SubjectEntity merged = subjectMapper.mapDtoToEntity(subject);
        SubjectEntity updated = subjectRepository.save(merged);
        return subjectMapper.mapEntityToDto(updated);
    }

    @Override
    public void delete(Long subjectId) {
        
        SubjectEntity subjectEntity = subjectRepository.findById(subjectId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject with id " + subjectId + " is not found.")
        );

        subjectRepository.delete(subjectEntity);
    }
}
