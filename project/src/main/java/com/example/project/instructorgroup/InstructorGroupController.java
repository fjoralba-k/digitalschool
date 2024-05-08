package org.zerogravitysolutions.digitalschool.instructorgroup;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.zerogravitysolutions.digitalschool.groups.GroupEntity;
import org.zerogravitysolutions.digitalschool.groups.GroupService;
import org.zerogravitysolutions.digitalschool.instructors.InstructorEntity;
import org.zerogravitysolutions.digitalschool.instructors.InstructorService;

@RestController
public class InstructorGroupController {

    private InstructorService instructorService;
    private GroupService groupService;
    private InstructorGroupService instructorGroupService;

    public InstructorGroupController(
        InstructorService instructorService,
        GroupService groupService,
        InstructorGroupService instructorGroupService
    ) {
        this.instructorService = instructorService;
        this.groupService = groupService;
        this.instructorGroupService = instructorGroupService;
    }

    @GetMapping(path = "/instructors/{id}/groups")
    public Set<GroupEntity> findGroupsByInstructorId(@PathVariable(name = "id") Long id) {

        return instructorService.findGroupsByInstructorId(id);
    }

    @GetMapping(path = "/groups/{id}/instructors")
    public Set<InstructorEntity> findInstructorsByGroypId(@PathVariable(name = "id") Long id) {

        return instructorGroupService.findInstructorsByGroupId(id);
    }

    @PostMapping(path = "/instructors/{instructorId}/groups/{groupId}")
    @ResponseStatus(HttpStatus.CREATED)
    public InstructorGroupEntity addInstructorToGroup(@PathVariable(name = "instructorId") Long instructorId, @PathVariable(name = "groupId") Long groupId) {

        return instructorGroupService.addInstructorToGroup(instructorId, groupId);
    }

    @DeleteMapping(path = "/instructors/{instructorId}/groups/{groupId}")
    public ResponseEntity<Void> removeByInstructorIdAndGroupid(@PathVariable(name = "instructorId") Long instructorId, @PathVariable(name = "groupId") Long groupId) {

        instructorGroupService.removeByInstructorIdAndGroupid(instructorId, groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*
     * 
     * GET /instructors/{id}/groups
     * GET /groups/{id}/instructors
     * POST /instructors/{sid}/groups/{gid}
     * DELETE /instructors/{iid}/groups/{gid}
     * 
     */
}
