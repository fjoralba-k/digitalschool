package org.zerogravitysolutions.digitalschool.students;

import java.io.IOException;
import java.util.Set;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.zerogravitysolutions.digitalschool.exceptions.StudentNotFoundException;
import org.zerogravitysolutions.digitalschool.feignclients.EmailSenderFeignClient;
import org.zerogravitysolutions.digitalschool.groups.GroupDto;
import org.zerogravitysolutions.digitalschool.groups.GroupEntity;
import org.zerogravitysolutions.digitalschool.groups.GroupMapper;
import org.zerogravitysolutions.digitalschool.groups.GroupRepository;
import org.zerogravitysolutions.digitalschool.rabbitmq_sample.SenderExample;
import org.zerogravitysolutions.digitalschool.students.commons.StudentMapperMapStruct;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final StudentMapperMapStruct studentMapperMapStruct;
    private final RabbitTemplate rabbitTemplate;

    private final GroupMapper groupMapper;
    private final EmailSenderFeignClient emailSenderFeignClient;

    private final SenderExample senderExample;

    public StudentServiceImpl(
        final StudentRepository studentRepository,
        final StudentMapperMapStruct studentMapperMapStruct,
        final GroupRepository groupRepository,
        final GroupMapper groupMapper,
        final EmailSenderFeignClient emailSenderFeignClient,
        final RabbitTemplate rabbitTemplate,
        final SenderExample senderExample
    ) {
        this.studentRepository = studentRepository;
        this.studentMapperMapStruct = studentMapperMapStruct;
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.emailSenderFeignClient = emailSenderFeignClient;
        this.rabbitTemplate = rabbitTemplate;
        this.senderExample = senderExample;
    }

    @Override
    public StudentDto findById(Long id) {

        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(
            () -> new StudentNotFoundException("Student with id " + id + " is not found."));

        // this.sendRabbitMessage(studentEntity.getFirstName());
        // senderExample.sendDirectMessage();
        // senderExample.sendFanoutMessage();
        senderExample.sendTopicMessage();


        // Example of how to get user data form UserContext
        // String email = UserContextHolder.getContext().getUserEmail();
        // Long userId = UserContextHolder.getContext().getUserId();
        // String authToken = UserContextHolder.getContext().getAuthToken();

        // Uncomment the code below to test feign client requests toward email service sender service
        // ResponseEntity<Void> responseEntity = emailSenderFeignClient.send("Hello hello", "qenndrimm@gmail.com", "Email plain text body goes here.");

        // if(responseEntity.getStatusCode().is2xxSuccessful()) {

        // } else {
        //     throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error sending email to client.");
        // }

        return studentMapperMapStruct.mapEntityToDto(studentEntity);
    }

    @Override
    public StudentEntity save(StudentEntity studentEntity) {

        return studentRepository.save(studentEntity);
    }

    @Override
    public Page<StudentDto> findAll(Pageable pageable) {

        Page<StudentEntity> studentEntities = studentRepository.findAll(pageable);

        return studentEntities.map(studentMapperMapStruct::mapEntityToDto);
    }

    public Set<StudentDto> findByNameOrEmail(String name, String email) {

        Set<StudentEntity> studentEntities = studentRepository.findByFirstNameOrEmailIgnoreCase(name, email);

        if (studentEntities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with name " + name + " not found.");
        }

        return studentMapperMapStruct.mapEntitiesToDtos(studentEntities);
    }

    @Override
    public void delete(Long id) {

        studentRepository.deleteById(id);
    }

    @Override
    public StudentDto update(Long id, StudentDto studentDto) {

        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " is not found."));

        StudentEntity updated = studentRepository.save(studentMapperMapStruct.mapDtoToEntity(studentDto));

        StudentDto updatedDto = studentMapperMapStruct.mapEntityToDto(updated);

        rabbitTemplate.convertAndSend("studentUpdated", updatedDto);
        return updatedDto;
    }

    @Override
    public StudentDto patch(Long id, StudentDto studentDto) {

        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with this id " + id + " is not found"));

        // StudentMapper.mapDtoToEntity(studentDto, studentEntity);
        studentMapperMapStruct.mapDtoToEntity(studentDto, studentEntity);

        StudentEntity patched = studentRepository.save(studentEntity);

        // return StudentMapper.mapEntityToDto(patched);
        return studentMapperMapStruct.mapEntityToDto(patched);
    }

    @Override
    public Set<GroupDto> getGroupsByStudentId(Long id) {

        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " is not found."));


        return groupMapper.mapEntitiesToDtos(studentEntity.getGroups());
    }

    @Override
    public void addStudentToGroup(Long studentId, Long groupId) {

        StudentEntity studentEntity = studentRepository.findById(studentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + studentId + " is not found."));

        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group with id " + groupId + " is not found."));

        groupEntity.getStudents().add(studentEntity);

        groupRepository.save(groupEntity);
    }

    @Override
    public Set<StudentDto> getStudentsByGroupId(Long id) {

        GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group with id " + id + " is not found."));

        return studentMapperMapStruct.mapEntitiesToDtos(groupEntity.getStudents());
    }

    @Override
    public void removeStudentFromGroup(Long studentId, Long groupId) {

        StudentEntity studentEntity = studentRepository.findById(studentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + studentId + " is not found."));

        GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group with id " + groupId + " is not found."));

        groupEntity.getStudents().remove(studentEntity);
        studentEntity.getGroups().remove(groupEntity);

        studentRepository.save(studentEntity);
    }

    @Override
    public void uploadImage(Long id, MultipartFile image) {
        
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " is not found."));

        try {
            byte[] imageBytes = image.getBytes();
            studentEntity.setProfilePicture(imageBytes);

            studentRepository.save(studentEntity);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public ByteArrayResource readImage(Long id) {
        
        StudentEntity studentEntity = studentRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " is not found."));

         byte[] image = studentEntity.getProfilePicture();

         if(image != null) {

            ByteArrayResource resource = new ByteArrayResource(image);
            return resource;
         } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile picture not found for the given student id: " + id);
         }
    }

    @Override
    public StudentEntity findByEmail(String email) {

        return studentRepository.findByEmailIgnoreCase(email).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Student with id " + email + " is not found."));
    }

    public void sendRabbitMessage(String message) {
        
        rabbitTemplate.convertAndSend("myQueue", message);
        System.out.println("Message sent: " + message);
    }
}
