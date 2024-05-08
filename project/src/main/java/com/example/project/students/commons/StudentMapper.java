package org.zerogravitysolutions.digitalschool.students.commons;

import org.zerogravitysolutions.digitalschool.students.StudentDto;
import org.zerogravitysolutions.digitalschool.students.StudentEntity;

public class StudentMapper {
    
    public static void mapDtoToEntity(StudentDto dto, StudentEntity entity) {

        if(dto.getFirstName() != null) {
            entity.setFirstName(dto.getFirstName());
        }

        if(dto.getLastName() != null) {
            entity.setLastName(dto.getLastName());
        }

        if(dto.getProfilePicture() != null) {
            entity.setProfilePicture(dto.getProfilePicture());
        }

        if(dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }

        if(dto.getPhoneNumber() != null) {
            entity.setPhoneNumber(dto.getPhoneNumber());
        }

        if(dto.getAddress() != null) {
            entity.setAddress(dto.getAddress());
        }
    }

    public static StudentDto mapEntityToDto(StudentEntity entity) {
        StudentDto dto = new StudentDto();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setProfilePicture(entity.getProfilePicture());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setDeletedAt(entity.getDeletedAt());
        dto.setDeletedBy(entity.getDeletedBy());

        return dto;
    }
}
