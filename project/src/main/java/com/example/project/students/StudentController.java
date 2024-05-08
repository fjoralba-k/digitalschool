package org.zerogravitysolutions.digitalschool.students;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
public class StudentController {

    private StudentService studentService;

    public StudentController(final StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "/students/{id}")
    // @RolesAllowed("STUDENT")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable(name = "id") Long id) {

        // import org.springframework.security.access.prepost.PreAuthorize;
        // import org.springframework.security.core.Authentication;
        // import org.springframework.security.core.context.SecurityContextHolder;
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        StudentDto foundStudent = studentService.findById(id);
        return ResponseEntity.ok(foundStudent);
    }

    @GetMapping(path = "/students", params = {"name"})
    public ResponseEntity<Set<StudentDto>> findByNameOrEmail(@RequestParam(name = "name") String name, @RequestParam(name = "email", required = false) String email) {

        Set<StudentDto> studentsFound = studentService.findByNameOrEmail(name, email);

        return ResponseEntity.ok(studentsFound);
    }

    @GetMapping(path = "/students")
    public ResponseEntity<Page<StudentDto>> findAllStudents(Pageable pageable) {

        // http://localhost:8085/students?page=1&sort=firstName,desc&sort=id,asc&size=1
        Page<StudentDto> studentDtos = studentService.findAll(pageable);

        return ResponseEntity.ok(studentDtos);
    }

    @PostMapping(path = "/students")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StudentEntity> createStudent(@RequestBody StudentEntity studentEntity) {

        StudentEntity created = studentService.save(studentEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping(path = "/students/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAllStudents(@PathVariable(name = "id") Long id) {

        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/students/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable(name = "id") Long id, @RequestBody StudentDto studentDto) {

        if(id == null || studentDto == null) {
            // throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }

        if(!id.equals(studentDto.getId())) {

        }

        return ResponseEntity.ok(studentService.update(id, studentDto));
    }

    @PatchMapping(path = "/students/{id}")
    public ResponseEntity<StudentDto> patch(@PathVariable(name = "id") Long id, @RequestBody StudentDto studentDto) {

        StudentDto patched = studentService.patch(id, studentDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(patched);
    }

    @PostMapping(path = "/students/{id}/image")
    public ResponseEntity<Void> uploadImage(@PathVariable(name = "id") Long id, @RequestParam("file") MultipartFile image) {

        studentService.uploadImage(id, image);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/students/{id}/image")
    public ResponseEntity<ByteArrayResource> readImage(@PathVariable(name = "id") Long id) {

        ByteArrayResource profilePicture = studentService.readImage(id);

        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "student-" + id + "-image.jpeg" + "\"")
            .body(profilePicture);
    }
}
