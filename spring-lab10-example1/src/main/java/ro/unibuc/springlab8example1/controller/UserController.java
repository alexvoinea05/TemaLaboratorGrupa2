package ro.unibuc.springlab8example1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.springlab8example1.domain.UserType;
import ro.unibuc.springlab8example1.dto.UserDto;
import ro.unibuc.springlab8example1.exception.UserNotFoundException;
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
                .body(userService.create(userDto, UserType.STUDENT));
    }

    @PostMapping("/professor")
    public ResponseEntity<UserDto> createProfessor(@RequestBody UserDto userDto) {
        return ResponseEntity
                .ok()
                .body(userService.create(userDto, UserType.PROFESSOR));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> get(@PathVariable String username) {
        try {
            return ResponseEntity
                    .ok()
                    .body(userService.getOne(username));
        } catch (UserNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<UserDto>> getFilteredUsers(@RequestParam String name,
                                                          @RequestParam String userType) {
        return ResponseEntity
                .ok()
                .body(userService.getFilteredUsers(name, UserType.valueOf(userType)));
    }

    @DeleteMapping("/cleanup")
    public void cleanupUsers(@RequestParam Long years) {
        userService.removeOlderUsers(years);
    }

    @PostMapping("/testingTransactional")
    public void testingTransactional(@RequestParam boolean ok) {
        userService.generateRandomUsers(ok);
    }
}
