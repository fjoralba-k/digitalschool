package org.zerogravitysolutions.digitalschool.instructors;

import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.zerogravitysolutions.digitalschool.groups.GroupEntity;
import org.zerogravitysolutions.digitalschool.groups.GroupRepository;

@Service
public class InstructorServiceImpl implements InstructorService {
    
    private InstructorRepository instructorRepository;
    private GroupRepository groupRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository, GroupRepository groupRepository) {
        this.instructorRepository = instructorRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public InstructorEntity findById(Long id) {
        
        InstructorEntity instructorEntity = instructorRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor with id " + id + " is not found."));

            return instructorEntity;
    }

    @Override
    public InstructorEntity create(InstructorEntity instructor) {
        
        return instructorRepository.save(instructor);
    }

    @Override
    public Set<GroupEntity> findGroupsByInstructorId(Long id) {
        
        InstructorEntity instructorEntity = instructorRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor with id " + id + " is not found."));

        return groupRepository.findAllByInstructorGroupSetInstructorId(instructorEntity.getId());
    }
}
