package org.zerogravitysolutions.digitalschool.instructorgroup;

import java.util.Set;

import org.zerogravitysolutions.digitalschool.instructors.InstructorEntity;

public interface InstructorGroupService {

    InstructorGroupEntity addInstructorToGroup(Long instructorId, Long groupId);
    Set<InstructorEntity> findInstructorsByGroupId(Long id);
    void removeByInstructorIdAndGroupid(Long instructorId, Long groupId);
}
