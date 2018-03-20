package ru.itmo.degtiarenko.react.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.itmo.degtiarenko.react.model.UserInfo;

public interface UserRepository extends ReactiveMongoRepository<UserInfo, String> {
}
