package org.zerogravitysolutions.digitalschool.instructorgroup;

import java.util.List;
import java.util.Set;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerogravitysolutions.digitalschool.groups.GroupEntity;
import org.zerogravitysolutions.digitalschool.instructors.InstructorEntity;

@Repository
public interface InstructorGroupRepository extends JpaRepository<InstructorGroupEntity, Long> {
    
    List<InstructorGroupEntity> findByInstructor(InstructorEntity instructor);
    Set<InstructorGroupEntity> findAllByGroup(GroupEntity group);
    Optional<InstructorGroupEntity> findByInstructorAndGroup(InstructorEntity instructor, GroupEntity group);
}