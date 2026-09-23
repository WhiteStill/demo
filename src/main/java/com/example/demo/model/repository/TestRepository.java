package com.example.demo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository; // JPA 필수등록
import org.springframework.stereotype.Repository; // 빈등록
import com.example.demo.model.domain.TestDB; // 도메인연동

@Repository // 리포지토리등록
public interface TestRepository extends JpaRepository<TestDB,Long> {
// Find all TestDBentities by a name
    TestDB findByName(String name);
}