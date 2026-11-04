package com.example.demo.model.service;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.domain.Member;
import com.example.demo.model.domain.Post;
import com.example.demo.model.dto.PostForm;
import com.example.demo.model.repository.MemberRepository;
import com.example.demo.model.repository.PostRepository;
@Service // 게시판서비스
public class PostService {
@Autowired
private PostRepository postRepository;
@Autowired
private MemberRepository memberRepository;

public List<Post> findAll() { // 전체목록(최신글이위로)
    return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
}

public Post write(PostForm form, String username) { // 글쓰기(작성자= 로그인한회원)
    validate(form);
    Member author = memberRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("회원이존재하지않습니다."));
    Post post = new Post();
    post.setTitle(form.getTitle().trim());
    post.setContent(form.getContent());
    post.setAuthor(author); // 작성자는화면이아니라서버가정한다
    post.setCreatedAt(LocalDateTime.now());
    return postRepository.save(post); // INSERT
}

private void validate(PostForm form) { // 빈값검사(5주차회원가입과같은방식)
    if (form.getTitle() == null || form.getTitle().isBlank()
            || form.getContent() == null || form.getContent().isBlank()) {
        throw new IllegalArgumentException("제목과 내용을 모두 입력하세요.");
    }
    if (form.getTitle().trim().length() > 100) {
        throw new IllegalArgumentException("제목은 100자까지 입력할 수 있습니다.");
    }
}
public Post findById(Long id) { // 글1개(없으면404 → templates/error/404.html)
return postRepository.findById(id)
.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "글이 존재하지 않습니다."));
}

public Post read(Long id) { // 상세보기: 조회수 1 증가
    Post post = findById(id);
    post.setViewCount(post.getViewCount() + 1);
    return postRepository.save(post); // id가 있으니 UPDATE
}

public Post findForEdit(Long id) { // 수정화면에보여줄글(본인글만)
    Post post = findById(id);
    checkWriter(post); // ★추가
    return post;
}


public Post edit(Long id, PostForm form) { // 글수정
    Post post = findForEdit(id);
    validate(form);
    post.setTitle(form.getTitle().trim());
    post.setContent(form.getContent());
    post.setUpdatedAt(LocalDateTime.now()); // 수정시간기록
    return postRepository.save(post); // id 가있으니UPDATE
}

public void delete(Long id) { // 글삭제(본인또는관리자)
    Post post = findById(id);
    if (!isAdmin())
        checkWriter(post); // ★추가: 관리자는 누구 글이든 삭제가능
    postRepository.delete(post);
}

public List<Post> findMine(String username) { // 내가 쓴 글(최신 글이 위로)
    return postRepository.findByAuthor_UsernameOrderByIdDesc(username);
}

private void checkWriter(Post post) { // 로그인 한사람이 글쓴이인지→ 아니면403
    String loginUsername = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!post.getAuthor().getUsername().equals(loginUsername)) {
        throw new AccessDeniedException("본인이쓴글만수정·삭제할수있습니다.");
    }
}
private boolean isAdmin() { // 로그인한 사람이 관리자(ROLE_ADMIN)인지
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
}
}
