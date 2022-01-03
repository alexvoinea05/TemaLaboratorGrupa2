package ro.unibuc.springlab8example1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.springlab8example1.domain.Classroom;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.ClassroomDto;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.service.ClassroomService;

import java.util.List;

@RestController
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping()
    public ResponseEntity<ClassroomDto> createClassroom(@RequestBody ClassroomDto classroomDto) {
        return ResponseEntity
                .ok()
                .body(classroomService.addClassroom(classroomDto));
    }

    @GetMapping("/greater")
    public ResponseEntity<List<ClassroomDto>> getClassroomsGreaterThan(@RequestParam Long no)
    {
        return ResponseEntity.ok().
                body(classroomService.getAllClassroomWithNumberGreaterThan(no));
    }
}
