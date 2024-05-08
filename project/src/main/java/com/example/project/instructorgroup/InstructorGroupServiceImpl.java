package org.zerogravitysolutions.digitalschool.instructorgroup;

import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.zerogravitysolutions.digitalschool.groups.GroupEntity;
import org.zerogravitysolutions.digitalschool.groups.GroupRepository;
import org.zerogravitysolutions.digitalschool.instructors.InstructorEntity;
import org.zerogravitysolutions.digitalschool.instructors.InstructorRepository;

@Service
public class InstructorGroupServiceImpl implements InstructorGroupService {
    
    private InstructorGroupRepository instructorGroupRepository;
    private InstructorRepository instructorRepository;
    private GroupRepository groupRepository;

    public InstructorGroupServiceImpl(InstructorRepository instructorRepository, GroupRepository groupRepository, InstructorGroupRepository instructorGroupRepository) {
        this.instructorRepository = instructorRepository;
        this.groupRepository = groupRepository;
        this.instructorGroupRepository = instructorGroupRepository;
    }

    @Override
    public InstructorGroupEntity addInstructorToGroup(Long instructorId, Long groupId) {
        
        InstructorEntity instructorEntity = instructorRepository.findById(instructorId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor with id " + instructorId + " is not found."));


        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group with id " + groupId + " is not found."));
        

        InstructorGroupEntity instructorGroupEntity = new InstructorGroupEntity();
        instructorGroupEntity.setInstructor(instructorEntity);
        instructorGroupEntity.setGroup(groupEntity);
        
        return instructorGroupRepository.save(instructorGroupEntity);
    }

    @Override
    public Set<InstructorEntity> findInstructorsByGroupId(Long id) {
        
        GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group with id " + id + " is not found."));
        
        Set<InstructorGroupEntity> instructorGroupSet = instructorGroupRepository.findAllByGroup(groupEntity);

        Set<InstructorEntity> instructorSet = new HashSet<>();

        instructorGroupSet.forEach(instructorGroup -> {
            instructorSet.add(instructorGroup.getInstructor());
        });

        return instructorSet;
    }

    @Override
    public void removeByInstructorIdAndGroupid(Long instructorId, Long groupId) {

                
        InstructorEntity instructorEntity = instructorRepository.findById(instructorId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor with id " + instructorId + " is not found."));


        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group with id " + groupId + " is not found."));
        
        Optional<InstructorGroupEntity> instructorGroupEntityOptional = instructorGroupRepository.findByInstructorAndGroup(instructorEntity, groupEntity);

        // instructorGroupEntityOptional.ifPresent(instructorGroupRepository::delete);
        if(instructorGroupEntityOptional.isPresent()) {

            InstructorGroupEntity instructorGroupEntity = instructorGroupEntityOptional.get();
            instructorGroupRepository.delete(instructorGroupEntity);
        }
    }
}
