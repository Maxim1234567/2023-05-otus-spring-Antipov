package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dto.AuthorDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.CommentDto;
import ru.otus.dto.GenreDto;
import ru.otus.service.IOService;
import ru.otus.service.UserInteraction;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserInteractionImpl implements UserInteraction {
    private final IOService ioService;

    @Override
    public GenreDto createGenre() {
        ioService.print("Enter name genre: ");
        String genre = ioService.readLine();

        return new GenreDto(null, genre);
    }

    @Override
    public AuthorDto createAuthor() {
        ioService.print("Enter first name: ");
        String firstName = ioService.readLine();

        ioService.print("Enter last name: ");
        String lastName = ioService.readLine();

        ioService.print("Enter age: ");
        int age = Integer.parseInt(ioService.readLine());

        ioService.print("Enter year birthdate: ");
        int yearBirthdate = Integer.parseInt(ioService.readLine());

        return new AuthorDto(null, firstName, lastName, age, yearBirthdate);
    }

    @Override
    public BookDto createBook() {
        ioService.print("Enter name: ");
        String name = ioService.readLine();

        ioService.print("Enter year issue: ");
        int yearIssue = Integer.parseInt(ioService.readLine());

        ioService.print("Enter number pages: ");
        int numberPages = Integer.parseInt(ioService.readLine());

        return new BookDto(null, name, yearIssue, numberPages, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public CommentDto createComment() {
        ioService.print("Enter comments: ");
        String comment = ioService.readLine();

        return new CommentDto(null, comment, null);
    }

    @Override
    public Long getId() {
        ioService.print("Enter id: ");
        return Long.parseLong(ioService.readLine());
    }
}