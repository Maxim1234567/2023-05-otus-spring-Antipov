package ru.otus.service.impl;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.convert.BookConvertBookDto;
import ru.otus.convert.BookDtoConvertBook;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.exception.NotFoundException;
import ru.otus.exception.ValidationErrorException;
import ru.otus.repository.AuthorRepository;
import ru.otus.domain.Book;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.BookService;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookConvertBookDto convertBookDto;

    private final BookDtoConvertBook convertBook;

    private final MutableAclService mutableAclService;

    @Override
    @Transactional(readOnly = true)
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public BookDto getBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        return convertBookDto.convert(book);
    }

    @Override
    @Transactional(readOnly = true)
    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(convertBookDto::convert).toList();
    }

    @Override
    @Transactional
    public BookDto create(BookDto book) {
        book.setComments(List.of());

        Book bookDomain = convertBook.convert(book);
        BookDto newBook = convertBookDto.convert(save(bookDomain));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid(authentication);
        ObjectIdentity oid = new ObjectIdentityImpl(newBook.getClass(), newBook.getId());

        final Sid admin = new GrantedAuthoritySid("ROLE_EDITOR");

        MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setOwner(owner);
        acl.setEntriesInheriting(false);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true);

        mutableAclService.updateAcl(acl);

        return newBook;
    }

    @Override
    @Transactional
    @PreAuthorize("hasPermission(#book, 'WRITE')")
    public BookDto update(BookDto book) {
        bookRepository.findById(book.getId())
                .orElseThrow(NotFoundException::new);

        if (Objects.isNull(book.getComments())) {
            book.setComments(List.of());
        }

        book.getComments().forEach(c -> c.setBook(book));

        Book bookDomain = convertBook.convert(book);

        return convertBookDto.convert(
                save(bookDomain)
        );
    }

    private Book save(Book book) {
        book.getAuthors().forEach(a -> checkValidation(a.getId()));
        book.getGenres().forEach(g -> checkValidation(g.getId()));

        List<Author> authors = authorRepository.findByIds(
                book.getAuthors().stream().map(Author::getId).toList()
        );

        checkExists(
                authors.stream().map(Author::getId).collect(Collectors.toSet()),
                book.getAuthors().stream().map(Author::getId).collect(Collectors.toSet()));

        List<Genre> genres = genreRepository.findByIds(
                book.getGenres().stream().map(Genre::getId).toList()
        );

        checkExists(
                genres.stream().map(Genre::getId).collect(Collectors.toSet()),
                book.getGenres().stream().map(Genre::getId).collect(Collectors.toSet()));

        book.setAuthors(authors);
        book.setGenres(genres);

        return bookRepository.save(book);
    }

    @Override
    @Transactional
    @PreAuthorize("hasPermission(#book, 'WRITE')")
    public void delete(BookDto book) {
        bookRepository.deleteById(book.getId());
    }

    private void checkExists(Set<Long> ids1, Set<Long> ids2) {
        if (!equalsIds(ids1, ids2)) {
            throw new NotFoundException();
        }
    }

    private void checkValidation(Long id) {
        if (Objects.isNull(id)) {
            throw new ValidationErrorException("ID author is null");
        }
    }

    private boolean equalsIds(Set<Long> ids1, Set<Long> ids2) {
        return Sets.symmetricDifference(ids1, ids2).isEmpty();
    }
}