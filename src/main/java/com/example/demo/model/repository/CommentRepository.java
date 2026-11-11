package com.example.demo.model.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Comment;
import com.example.demo.model.domain.Post;
import com.example.demo.model.domain.Member; // 두 파일 모두

@Repository // 댓글리포지토리
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByIdAsc(Post post); // 이글의댓글(오래된순)

    boolean existsByAuthor(Member author); // 이회원이쓴댓글이있는지
}
