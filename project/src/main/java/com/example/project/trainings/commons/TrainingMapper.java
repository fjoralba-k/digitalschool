package org.zerogravitysolutions.digitalschool.trainings.commons;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.zerogravitysolutions.digitalschool.trainings.TrainingDto;
import org.zerogravitysolutions.digitalschool.trainings.TrainingEntity;

@Mapper(componentModel = "spring")
public interface TrainingMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapDtoToEntity(TrainingDto source, @MappingTarget TrainingEntity target);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TrainingDto mapEntityToDto(TrainingEntity source);

}
