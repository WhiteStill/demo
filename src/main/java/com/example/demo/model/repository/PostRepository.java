package com.example.demo.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Post;

@Repository // 게시글리포지토리: save, findAll, findById, delete 는이미있다
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor_UsernameOrderByIdDesc(String username);
}
