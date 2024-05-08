package org.zerogravitysolutions.digitalschool.instructors;

import java.util.Set;
import org.zerogravitysolutions.digitalschool.groups.GroupEntity;

public interface InstructorService {

    InstructorEntity findById(Long id);
    InstructorEntity create(InstructorEntity instructor);
    Set<GroupEntity> findGroupsByInstructorId(Long id);
}
