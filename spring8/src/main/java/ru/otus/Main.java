package ru.otus;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.repository.AuthorRepository;

@EnableMongock
@EnableMongoRepositories
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}