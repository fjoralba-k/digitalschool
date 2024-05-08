package org.zerogravitysolutions.digitalschool.students.commons;

import java.util.Set;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;
import org.zerogravitysolutions.digitalschool.students.StudentDto;
import org.zerogravitysolutions.digitalschool.students.StudentEntity;

@Mapper(componentModel = "spring")
public interface StudentMapperMapStruct {
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapDtoToEntity(StudentDto source, @MappingTarget StudentEntity target);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StudentDto mapEntityToDto(StudentEntity source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Set<StudentDto> mapEntitiesToDtos(Set<StudentEntity> sourceList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StudentEntity mapDtoToEntity(StudentDto source);
}
