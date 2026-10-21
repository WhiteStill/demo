package com.example.demo.model.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Member;

@Repository // 리포지토리 등록
public interface MemberRepository extends JpaRepository<Member, Long> {
    // SELECT * FROM member WHERE username = ?
    Optional<Member> findByUsername(String username);

    // 아이디 중복확인(존재하면true)
    boolean existsByUsername(String username);

    // 아이디 또는 이름에 검색어가 들어간 회원 (LIKE %?%)
    List<Member> findByUsernameContainingOrNameContaining(String username, String name, Sort sort);
}