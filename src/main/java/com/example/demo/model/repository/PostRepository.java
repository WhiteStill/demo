package com.example.demo.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.domain.Post;
import com.example.demo.model.domain.Member; // 두 파일 모두

@Repository // 게시글리포지토리: save, findAll, findById, delete 는이미있다
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor_UsernameOrderByIdDesc(String username);
    Page<Post> findByAuthor_Username(String username, Pageable pageable);
    
    // [10주차] 단계2 : 제목또는내용에검색어가들어간글(JPQL)
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    Page<Post> search(@Param("keyword") String keyword, Pageable pageable);
        // [10주차 연습문제 2] 작성자 이름으로 검색
    @Query("SELECT p FROM Post p WHERE p.author.name LIKE %:name%")
    Page<Post> searchByWriter(@Param("name") String name, Pageable pageable);

    boolean existsByAuthor(Member author); // 이회원이쓴글이있는지
}




