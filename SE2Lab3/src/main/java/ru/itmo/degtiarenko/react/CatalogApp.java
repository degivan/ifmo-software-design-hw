package ru.itmo.degtiarenko.react;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class CatalogApp {
    public static void main(String[] args)  {
        SpringApplication.run(CatalogApp.class, args);
    }
}