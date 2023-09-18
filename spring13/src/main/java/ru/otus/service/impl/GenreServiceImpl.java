package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
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
import ru.otus.convert.GenreConvertGenreDto;
import ru.otus.convert.GenreDtoConvertGenre;
import ru.otus.dto.GenreDto;
import ru.otus.domain.Genre;
import ru.otus.exception.NotFoundException;
import ru.otus.repository.GenreRepository;
import ru.otus.service.GenreService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    private final GenreDtoConvertGenre convertGenre;

    private final GenreConvertGenreDto convertGenreDto;

    private final MutableAclService mutableAclService;

    @Override
    @PostFilter("hasPermission(filterObject, 'READ')")
    @Transactional(readOnly = true)
    public List<GenreDto> getAll() {
        List<GenreDto> lists = genreRepository.findAll().stream().map(convertGenreDto::convert).toList();
        return new ArrayList<>(lists);
    }

    @Override
    @Transactional
    public GenreDto create(GenreDto genre) {
        GenreDto newGenre = save(genre);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid(authentication);
        ObjectIdentity oid = new ObjectIdentityImpl(newGenre.getClass(), newGenre.getId());

        final Sid admin = new GrantedAuthoritySid("ROLE_EDITOR");

        MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setOwner(admin);
        acl.setEntriesInheriting(false);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, owner, true);

        mutableAclService.updateAcl(acl);

        return newGenre;
    }

    @Override
    @PreAuthorize("hasPermission(#genre, 'WRITE')")
    public GenreDto update(GenreDto genre) {
        return save(genre);
    }

    public GenreDto save(GenreDto genre) {
        Genre genreDomain = convertGenre.convert(genre);
        Genre genreSave = genreRepository.save(genreDomain);

        return convertGenreDto.convert(genreSave);
    }

    @Override
    @Transactional
    @PreAuthorize("hasPermission(#genre, 'WRITE')")
    public void delete(GenreDto genre) {
        genreRepository.deleteById(genre.getId());
    }

    @Override
    @Transactional(readOnly = true)
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public GenreDto getGenreById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(NotFoundException::new);
        return convertGenreDto.convert(genre);
    }
}