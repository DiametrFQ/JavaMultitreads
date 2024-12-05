package com.example.demo;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> getUser(int id);
    UserEntity addUser(UserRequest userRequest);
}

