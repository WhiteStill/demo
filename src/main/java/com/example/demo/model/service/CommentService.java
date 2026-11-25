package com.example.demo.model.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import com.example.demo.model.domain.Comment;
import com.example.demo.model.domain.Member;
import com.example.demo.model.domain.Post;
import com.example.demo.model.repository.CommentRepository;
import com.example.demo.model.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.model.dto.Notification;

@Service // 댓글서비스
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostService postService; // 글찾기는9주차PostService 를그대로쓴다
    @Autowired // [12주차 연습 2] 이벤트 발행
    private ApplicationEventPublisher eventPublisher;
    
    public List<Comment> findByPost(Post post) { // 이글의댓글목록
        return commentRepository.findByPostOrderByIdAsc(post);
    }

    @Transactional // [12주차 연습 2] 트랜잭션이 있어야 @TransactionalEventListener 가 실행된다
    public Comment write(Long postId, String content, String username) { // 댓글쓰기
        if (content == null || content.isBlank() || content.length() > 500) {
            throw new IllegalArgumentException("댓글은1~500자로입력하세요.");
        }
        Post post = postService.findById(postId); // 없는글이면404
        Member author = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("회원이존재하지않습니다."));
        Comment comment = new Comment();
        comment.setContent(content.trim());
        comment.setPost(post); // 어느글의댓글인지
        comment.setAuthor(author); // 작성자는서버가정한다
        comment.setCreatedAt(LocalDateTime.now());
        Comment saved = commentRepository.save(comment); // INSERT
        String writer = post.getAuthor().getUsername();
        if (!writer.equals(username)) { // [12주차 연습 2] 내 글에 내 댓글은 알림 없음
            eventPublisher.publishEvent(new Notification(writer,
                    author.getName() + "님이 '" + post.getTitle() + "' 에 댓글을 남겼습니다.", post.getId()));
        }
        return saved;
    }

    public void delete(Long id) { // 댓글삭제(본인또는관리자)
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."));
        if (!isAdmin()) checkWriter(comment);
        commentRepository.delete(comment); // DELETE
    }

    private void checkWriter(Comment comment) { // 로그인한사람이댓글작성자인지→ 아니면403
        String loginUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!comment.getAuthor().getUsername().equals(loginUsername)) {
            throw new AccessDeniedException("본인이 쓴댓글만 삭제할 수 있습니다.");
        }
    }

    private boolean isAdmin() { // 로그인한사람이관리자인지
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}