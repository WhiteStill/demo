package com.example.demo.model.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.domain.Enrollment;
import com.example.demo.model.domain.Member;
import com.example.demo.model.domain.Post;

@Repository // [11주차] 단계4 : 신청내역리포지토리
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // 이미신청했는지: SELECT ... WHERE member_id = ? AND post_id = ?
    boolean existsByMemberAndPost(Member member, Post post);

    // 내신청내역(최근신청이위로) : member → username 으로찾기
    List<Enrollment> findByMember_UsernameOrderByIdDesc(String username);
    
    boolean existsByPost(Post post); // [연습문제 2] 이 모집글에 신청자가 있는지

    boolean existsByMember(Member member); // [연습문제 2] 이 회원의 신청 내역이 있는지
}