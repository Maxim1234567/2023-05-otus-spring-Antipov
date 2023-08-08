package ru.otus.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.Utils.assertEqualsAuthor;
import static ru.otus.Utils.assertEqualsAuthorList;

@DisplayName("Dao to work with authors should")
@DataMongoTest
public class AuthorRepositoryJpaTest {
    private static final Author EXISTING_AUTHOR = new Author(
            "400", "Irvine", "Welsh", 64, 1958
    );

    private static final List<Author> EXPECTED_AUTHORS = List.of(
            new Author("100", "Herbert", "Shieldt", 72, 1951),
            new Author("200", "Ivan", "Efremov", 64, 1908),
            new Author("300", "Isaac", "Asimov", 72, 1919),
            new Author("400", "Irvine", "Welsh", 64, 1958),
            new Author("500", "Lyubov", "Voronkova", 70, 1906)
    );

    private static final Author NOT_EXISTS_AUTHOR = new Author(
            null, "Lyubov", "Voronkova", 70, 1906
    );

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    public void setUp() {

    }

    @DisplayName("correctly save the genre without a given ID in the database")
    @Test
    public void shouldCorrectSaveAuthorWithoutId() {
        Author author = authorRepository.save(NOT_EXISTS_AUTHOR);
        Author result = authorRepository.findById(author.getId()).get();
        authorRepository.delete(author);

        assertEqualsAuthor(author, result);
    }

    @DisplayName("correctly return author by id")
    @Test
    public void shouldCorrectReturnAuthorById() {
        Author result = authorRepository.findById(EXISTING_AUTHOR.getId()).get();
        assertEqualsAuthor(EXISTING_AUTHOR, result);
    }

    @DisplayName("correctly return authors by ids")
    @Test
    public void shouldCorrectReturnAuthorsByIds() {
        List<String> authorIds = List.of(
                EXPECTED_AUTHORS.get(0).getId(),
                EXPECTED_AUTHORS.get(1).getId(),
                EXPECTED_AUTHORS.get(2).getId(),
                EXPECTED_AUTHORS.get(3).getId()
        );

        List<Author> excepted = List.of(
                EXPECTED_AUTHORS.get(0),
                EXPECTED_AUTHORS.get(1),
                EXPECTED_AUTHORS.get(2),
                EXPECTED_AUTHORS.get(3)
        );

        List<Author> result = authorRepository.findByIds(authorIds);
        assertEqualsAuthorList(excepted, result);
    }

    @DisplayName("correctly returns the expected list of authors")
    @Test
    public void shouldCorrectReturnExceptedAuthorList() {
        List<Author> result = authorRepository.findAll();
        assertEqualsAuthorList(EXPECTED_AUTHORS, result);
    }

    @DisplayName("correctly delete a genre by its id")
    @Test
    @Rollback
    public void shouldCorrectDeleteAuthorById() {
        assertThat(authorRepository.findById(EXISTING_AUTHOR.getId()))
                .isNotNull();

        authorRepository.deleteById(EXISTING_AUTHOR.getId());

        assertThat(authorRepository.findById(EXISTING_AUTHOR.getId()))
                .isEqualTo(Optional.empty());

        authorRepository.save(EXISTING_AUTHOR);
    }

    @Test
    public void shouldCorrectReturnEmptyOptionalIfAuthorNotExists() {
        assertThat(authorRepository.findById("111"))
                .isEqualTo(Optional.empty());
    }
}