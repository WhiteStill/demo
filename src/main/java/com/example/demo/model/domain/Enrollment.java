package com.example.demo.model.domain;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Entity // [11주차] 단계4 : 신청내역(회원1명이모집글1개에신청) : enrollments 테이블
@Table(name = "enrollments")
@Data
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) // 신청한회원(N : 1)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY) // 신청한모집글(N : 1)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @Column(nullable = false) // 신청시간
    private LocalDateTime createdAt;
}
