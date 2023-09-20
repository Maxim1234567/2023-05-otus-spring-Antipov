package ru.otus;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.otus.nonrelation.repository.CharacterNonRelationRepository;
import ru.otus.relation.repository.CharacterRelationalRepository;

@EnableMongock
@EnableMongoRepositories(basePackageClasses = CharacterNonRelationRepository.class)
@EnableJpaRepositories(basePackageClasses = CharacterRelationalRepository.class)
@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
