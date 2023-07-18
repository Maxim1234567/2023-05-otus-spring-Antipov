package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.IOService;
import ru.otus.service.LibraryFacade;
import ru.otus.service.UserInteraction;
import ru.otus.service.impl.IOServiceStreams;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("integration")
public class MainTest {

    private static final String TEXT_WILL_BE = "Create genre!\n" +
            "Create author!\n" +
            "Create book!\n" +
            "Show all genres\n" +
            "1. | Psychology\n" +
            "100. | Fiction\n" +
            "200. | Novel\n" +
            "300. | Thriller\n" +
            "400. | Tale\n" +
            "500. | Comedy\n" +
            "600. | Drama\n" +
            "700. | Popular science literature\n" +
            "800. | Art and culture\n" +
            "900. | Reference books and professional literature\n" +
            "1000. | Hobbies, skills\n" +
            "1100. | Modern domestic prose\n" +
            "Choice genre. For exit enter -1!\n" +
            "Show all authors\n" +
            "1. | Jen Sincero | 1965 | 57\n" +
            "100. | Herbert Shieldt | 1951 | 72\n" +
            "200. | Ivan Efremov | 1908 | 64\n" +
            "300. | Isaac Asimov | 1919 | 72\n" +
            "400. | Irvine Welsh | 1958 | 64\n" +
            "500. | Lyubov Voronkova | 1906 | 70\n" +
            "Choice author. For exit enter -1!\n" +
            "Show all books\n" +
            "400. | Alice's Adventures in Wonderland | 1865 | 225\n" +
            "\n" +
            "\n" +
            "1. | uni corn | 2023 | 320\n" +
            "----------------------------------------\n" +
            "  1. | Jen Sincero | 1965 | 57\n" +
            "----------------------------------------\n" +
            "  1. | Psychology\n" +
            "\n" +
            "100. | Java. Complete guide | 2022 | 1344\n" +
            "----------------------------------------\n" +
            "  100. | Herbert Shieldt | 1951 | 72\n" +
            "----------------------------------------\n" +
            "  900. | Reference books and professional literature  1000. | Hobbies, skills\n" +
            "\n" +
            "200. | Starships. Andromeda's nebula | 1987 | 400\n" +
            "----------------------------------------\n" +
            "  200. | Ivan Efremov | 1908 | 64\n" +
            "----------------------------------------\n" +
            "  200. | Novel  600. | Drama  700. | Popular science literature\n" +
            "\n" +
            "300. | FOUNDATION | 2022 | 320\n" +
            "----------------------------------------\n" +
            "  300. | Isaac Asimov | 1919 | 72\n" +
            "----------------------------------------\n" +
            "  200. | Novel  600. | Drama  700. | Popular science literature\n" +
            "\n";

    private static final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    private static final Genre genre = new Genre(
            null, "Psychology"
    );

    private static final Author author = new Author(
            null, "Jen", "Sincero", 57, 1965
    );

    private static final Book book = new Book(
            null, "uni corn", 2023, 320
    );

    @MockBean
    private UserInteraction userInteraction;

    @Autowired
    private LibraryFacade libraryFacade;

    @TestConfiguration
    static class MainTestConfiguration {
        @Bean
        public IOService ioService() {
            return new IOServiceStreams(new PrintStream(baos), System.in);
        }
    }

    @BeforeEach
    public void setUp() {
        given(userInteraction.createGenre())
                .willReturn(genre);

        given(userInteraction.createAuthor())
                .willReturn(author);

        given(userInteraction.createBook())
                .willReturn(book);

        given(userInteraction.getId())
                .willReturn(1L, -1L, 1L, -1L);
    }

    @Test
    public void integrationTest() {
        libraryFacade.createGenre();

        libraryFacade.createAuthor();

        libraryFacade.createBook();

        libraryFacade.showBooks();

        assertEquals(baos.toString().replace("\r", ""), TEXT_WILL_BE);
    }
}