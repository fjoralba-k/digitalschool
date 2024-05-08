package org.zerogravitysolutions.digitalschool.students;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Set<StudentEntity> findByFirstNameOrEmailIgnoreCase(String firstName, String email);
    Optional<StudentEntity> findByEmailIgnoreCase(String email);
}
