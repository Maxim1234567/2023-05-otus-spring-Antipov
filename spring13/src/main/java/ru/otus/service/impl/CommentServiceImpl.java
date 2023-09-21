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
import ru.otus.convert.CommentConvertCommentDto;
import ru.otus.convert.CommentDtoConvertComment;
import ru.otus.domain.Comment;
import ru.otus.dto.CommentDto;
import ru.otus.exception.NotFoundException;
import ru.otus.repository.CommentRepository;
import ru.otus.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final CommentConvertCommentDto convertCommentDto;

    private final CommentDtoConvertComment convertComment;

    private final MutableAclService mutableAclService;


    @Override
    @Transactional
    public CommentDto create(CommentDto comment) {
        CommentDto newComment = save(comment);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid(authentication);
        ObjectIdentity oid = new ObjectIdentityImpl(newComment.getClass(), newComment.getId());

        final Sid admin = new GrantedAuthoritySid("ROLE_EDITOR");

        MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setOwner(owner);
        acl.setEntriesInheriting(false);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, admin, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, admin, true);

        mutableAclService.updateAcl(acl);

        return newComment;
    }

    @Override
    @Transactional
    @PreAuthorize("hasPermission(#comment, 'WRITE')")
    public CommentDto update(CommentDto comment) {
        return save(comment);
    }

    public CommentDto save(CommentDto comment) {
        Comment commentDomain = convertComment.convert(comment);
        Comment commentSave = commentRepository.save(commentDomain);

        return convertCommentDto.convert(commentSave);
    }

    @Override
    @Transactional
    @PreAuthorize("hasPermission(#comment, 'WRITE')")
    public void delete(CommentDto comment) {
        commentRepository.deleteById(comment.getId());
    }

    @Override
    @Transactional(readOnly = true)
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public CommentDto getCommentById(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(NotFoundException::new);
        return convertCommentDto.convert(comment);
    }

    @Override
    @Transactional(readOnly = true)
    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<CommentDto> getAllCommentsByBookId(long bookId) {
        return commentRepository.findAllByBookId(bookId).stream().map(convertCommentDto::convert).toList();
    }
}