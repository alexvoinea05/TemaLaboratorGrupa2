package ro.unibuc.springlab8example1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.springlab8example1.domain.Classroom;
import ro.unibuc.springlab8example1.dto.ClassroomDto;
import ro.unibuc.springlab8example1.mapper.ClassroomMapper;
import ro.unibuc.springlab8example1.repository.ClassroomRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    @Autowired
    private final ClassroomMapper classroomMapper;

    public ClassroomService(ClassroomRepository classroomRepository, ClassroomMapper classroomMapper) {
        this.classroomRepository = classroomRepository;
        this.classroomMapper = classroomMapper;
    }

    public ClassroomDto addClassroom(ClassroomDto classroomDto){

        Classroom classroom = classroomMapper.mapToEntity(classroomDto);
        Classroom savedClassroom = classroomRepository.save(classroom);

        return classroomMapper.mapToDto(savedClassroom);
    }

    public List<ClassroomDto> getAllClassroomWithNumberGreaterThan(Long no){

        List<Classroom> classroomDtoList = classroomRepository.findClassroomsByClassroomNumberGreaterThan(no);

        return classroomDtoList.stream().map(c -> classroomMapper.mapToDto(c)).collect(Collectors.toList());
    }
}
