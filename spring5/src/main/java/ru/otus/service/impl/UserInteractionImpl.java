package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.IOService;
import ru.otus.service.UserInteraction;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserInteractionImpl implements UserInteraction {
    private final IOService ioService;

    @Override
    public Genre createGenre() {
        ioService.print("Enter name genre: ");
        String genre = ioService.readLine();

        return new Genre(null, genre);
    }

    @Override
    public Author createAuthor() {
        ioService.print("Enter first name: ");
        String firstName = ioService.readLine();

        ioService.print("Enter last name: ");
        String lastName = ioService.readLine();

        ioService.print("Enter age: ");
        int age = Integer.parseInt(ioService.readLine());

        ioService.print("Enter year birthdate: ");
        int yearBirthdate = Integer.parseInt(ioService.readLine());

        return new Author(null, firstName, lastName, age, yearBirthdate);
    }

    @Override
    public Book createBook() {
        ioService.print("Enter name: ");
        String name = ioService.readLine();

        ioService.print("Enter year issue: ");
        int yearIssue = Integer.parseInt(ioService.readLine());

        ioService.print("Enter number pages: ");
        int numberPages = Integer.parseInt(ioService.readLine());

        return new Book(null, name, yearIssue, numberPages, new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public Long getId() {
        ioService.print("Enter id: ");
        return Long.parseLong(ioService.readLine());
    }
}
