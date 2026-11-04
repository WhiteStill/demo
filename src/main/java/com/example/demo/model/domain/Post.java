package com.example.demo.model.domain;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Entity // 게시글엔티티: posts 테이블
@Table(name = "posts")
@Data // set/get/tostring 등필수메서드자동생성
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가(1, 2, 3 ...)
    private Long id;
    @Column(nullable = false, length = 100) // 제목: 필수, 100자까지
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT") // 내용: 긴글도저장
    private String content;
    @ManyToOne(fetch = FetchType.LAZY) // 작성자: 회원1명이글여러개(N: 1)
    @JoinColumn(name = "member_id", nullable = false) // 외래키(FK) 컬럼이름
    private Member author;
    @Column(nullable = false) // 작성시간
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt; // 수정시간(수정한적없으면null)
    @Column(nullable = false) // 조회수(연습문제에서사용)
    private int viewCount = 0;
}