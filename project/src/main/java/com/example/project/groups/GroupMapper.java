package org.zerogravitysolutions.digitalschool.groups;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.zerogravitysolutions.digitalschool.students.StudentDto;
import org.zerogravitysolutions.digitalschool.students.StudentEntity;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapDtoToEntity(GroupDto source, @MappingTarget GroupEntity target);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GroupDto mapEntityToDto(GroupEntity source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Set<GroupDto> mapEntitiesToDtos(Set<GroupEntity> sourceList);
}
