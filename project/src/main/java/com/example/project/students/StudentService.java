package org.zerogravitysolutions.digitalschool.students;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerogravitysolutions.digitalschool.groups.GroupDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Set;

public interface StudentService {

    StudentDto findById(Long id);
    StudentEntity save(StudentEntity studentEntity);
    Page<StudentDto> findAll(Pageable pageable);
    Set<StudentDto> findByNameOrEmail(String name, String email);
    void delete(Long id);
    StudentDto update(Long id, StudentDto studentDto);

    StudentDto patch(Long id, StudentDto dto);
    Set<GroupDto> getGroupsByStudentId(Long id);
    void addStudentToGroup(Long studentId, Long groupId);
    Set<StudentDto> getStudentsByGroupId(Long id);
    void removeStudentFromGroup(Long studentId, Long groupId);
    void uploadImage(Long id, MultipartFile image);
    ByteArrayResource readImage(Long id);

    StudentEntity findByEmail(String email);
}
