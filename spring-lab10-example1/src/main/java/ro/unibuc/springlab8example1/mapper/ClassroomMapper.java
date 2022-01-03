package ro.unibuc.springlab8example1.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ro.unibuc.springlab8example1.domain.Classroom;
import ro.unibuc.springlab8example1.dto.ClassroomDto;

@Mapper(componentModel = "spring")
public interface ClassroomMapper {
    @Named("idClassroom")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idClassroom", source = "idClassroom")
    @Mapping(target = "classroomName", source = "classroomName")
    ClassroomDto toDtoId(Classroom classroom);

    ClassroomDto mapToDto(Classroom classroom);

    Classroom mapToEntity(ClassroomDto classroom);
}
