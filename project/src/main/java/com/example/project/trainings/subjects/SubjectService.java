package org.zerogravitysolutions.digitalschool.trainings.subjects;

import java.util.Set;

public interface SubjectService {
    
    SubjectDto addSubjectToTraining(Long trainingId, SubjectDto subject);
    Set<SubjectDto> getSubjectsByTrainingId(Long trainingId);
    SubjectDto update(Long subjectId, SubjectDto subject);
    void delete(Long subjectId);
}
