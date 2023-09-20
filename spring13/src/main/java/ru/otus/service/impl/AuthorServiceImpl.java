package ru.otus.service.impl;

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
import ru.otus.convert.AuthorConvertAuthorDto;
import ru.otus.convert.AuthorDtoConvertAuthor;
import ru.otus.dto.AuthorDto;
import ru.otus.domain.Author;
import ru.otus.exception.NotFoundException;
import ru.otus.repository.AuthorRepository;
import ru.otus.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final AuthorDtoConvertAuthor convertAuthor;

    private final AuthorConvertAuthorDto convertAuthorDto;

    private final MutableAclService mutableAclService;

    @Override
    @Transactional(readOnly = true)
    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<AuthorDto> getAll() {
        return authorRepository.findAll().stream().map(convertAuthorDto::convert).toList();
    }

    @Override
    @Transactional
    public AuthorDto create(AuthorDto author) {
        AuthorDto newAuthor = save(author);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid(authentication);
        ObjectIdentity oid = new ObjectIdentityImpl(newAuthor.getClass(), newAuthor.getId());

        final Sid admin = new GrantedAuthoritySid("ROLE_EDITOR");

        MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setOwner(owner);
        acl.setEntriesInheriting(false);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true);

        mutableAclService.updateAcl(acl);

        return newAuthor;
    }

    @Override
    @Transactional
    @PreAuthorize("hasPermission(#author, 'WRITE')")
    public AuthorDto update(AuthorDto author) {
        return save(author);
    }

    private AuthorDto save(AuthorDto author) {
        Author authorDomain = convertAuthor.convert(author);
        Author authorSave = authorRepository.save(authorDomain);

        return convertAuthorDto.convert(authorSave);
    }

    @Override
    @Transactional
    @PreAuthorize("hasPermission(#author, 'WRITE')")
    public void delete(AuthorDto author) {
        authorRepository.deleteById(author.getId());
    }

    @Override
    @Transactional(readOnly = true)
    @PostAuthorize("hasPermissio(returnObject, 'READ')")
    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(NotFoundException::new);
        return convertAuthorDto.convert(author);
    }
}