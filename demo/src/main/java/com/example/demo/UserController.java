package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserEntity> getUserByIdFromQuery(@RequestParam("id") int id) {
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserEntity> getUserByIdFromJson(@RequestBody UserIdRequest userIdRequest) {
        int id = userIdRequest.getId();
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserRequest userRequest) {
        UserEntity savedUser = userService.addUser(userRequest);
        return ResponseEntity.ok(savedUser);
    }
}
