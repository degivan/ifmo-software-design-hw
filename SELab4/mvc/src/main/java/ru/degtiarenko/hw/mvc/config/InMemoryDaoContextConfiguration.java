package ru.degtiarenko.hw.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.degtiarenko.hw.mvc.dao.BusinessDao;
import ru.degtiarenko.hw.mvc.dao.BusinessInMemoryDao;


@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public BusinessDao businessDao() {
        return new BusinessInMemoryDao();
    }
}
