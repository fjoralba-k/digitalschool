package org.zerogravitysolutions.digitalschool.groups;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    
    Set<GroupEntity> findAllByInstructorGroupSetInstructorId(Long id);
}
