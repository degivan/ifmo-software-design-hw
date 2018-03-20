package ru.itmo.degtiarenko.react.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.itmo.degtiarenko.react.model.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
