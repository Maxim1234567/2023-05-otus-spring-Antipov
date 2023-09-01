package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Person;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
}
