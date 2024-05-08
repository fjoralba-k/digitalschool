package org.zerogravitysolutions.digitalschool.studentgroup;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.zerogravitysolutions.digitalschool.groups.GroupDto;
import org.zerogravitysolutions.digitalschool.groups.GroupEntity;
import org.zerogravitysolutions.digitalschool.groups.GroupService;
import org.zerogravitysolutions.digitalschool.students.StudentDto;
import org.zerogravitysolutions.digitalschool.students.StudentEntity;
import org.zerogravitysolutions.digitalschool.students.StudentService;

@RestController
public class StudentGroupController {
    
    private StudentService studentService;
    private GroupService groupService;

    public StudentGroupController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping(path = "/students/{id}/groups")
    public ResponseEntity<Set<GroupDto>> getGroupsByStudentId(@PathVariable(name = "id") Long id) {
        Set<GroupDto> groups = studentService.getGroupsByStudentId(id);

        return ResponseEntity.ok(groups);
    }

    @GetMapping(path = "/groups/{id}/students")
    public ResponseEntity<Set<StudentDto>> getStudentsByGroupId(@PathVariable(name = "id") Long id) {
        Set<StudentDto> groups = studentService.getStudentsByGroupId(id);

        return ResponseEntity.ok(groups);
    }

    @PostMapping(path = "/students/{sid}/groups/{gid}")
    public ResponseEntity<Void> addStudentToGroup(@PathVariable(name = "sid")Long studentId, @PathVariable(name = "gid")Long groupId){

        studentService.addStudentToGroup(studentId,groupId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(path = "/students/{sid}/groups/{gid}")
    public ResponseEntity<Void> removeStudentFromGroup(@PathVariable(name = "sid")Long studentId, @PathVariable(name = "gid")Long groupId){
        studentService.removeStudentFromGroup(studentId,groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*
     * 
     * GET /students/{id}/groups
     * GET /groups/{id}/students
     * POST /students/{sid}/groups/{gid}
     * DELETE /students/{sid}/groups/{gid}
     * 
     */
}
