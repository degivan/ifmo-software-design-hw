package ru.itmo.degtiarenko.react.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.itmo.degtiarenko.react.model.UserInfo;
import ru.itmo.degtiarenko.react.repository.UserRepository;

import javax.validation.Valid;

@RestController
public class RegisterController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public Mono<UserInfo> createUser(@Valid @RequestBody UserInfo user)  {
        return userRepository.save(user);
    }
}
