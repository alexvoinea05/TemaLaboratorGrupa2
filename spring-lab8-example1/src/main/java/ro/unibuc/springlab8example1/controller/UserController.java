package ro.unibuc.springlab8example1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/student")
    public ResponseEntity<UserDto> createStudent(@RequestBody UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(userService.create(userDto, UserType.PROFESSOR));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> get(@PathVariable String username) {
        return ResponseEntity
                .ok()
                .body(userService.getOne(username));
    }

    // TODO: homework: endpoints for updating a user, deleting one, get all users filtered by tupe

    @PutMapping("/student")
    public ResponseEntity<UserDto> updateStudent(@RequestBody UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(userService.update(userDto, UserType.STUDENT));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<UserDto>> getByType(@PathVariable String type) {
        return ResponseEntity
                .ok()
                .body(userService.getAllWithType(type));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
