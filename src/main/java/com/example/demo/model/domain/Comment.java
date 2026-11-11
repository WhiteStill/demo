package com.example.demo.model.domain;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Entity // 댓글엔티티: comments테이블
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 500) // 댓글내용: 필수, 500자까지
    private String content;
    @ManyToOne(fetch = FetchType.LAZY) // 어느글의댓글인지(글1 : 댓글N)
    @JoinColumn(name = "post_id", nullable = false) // 외래키→ posts.id
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY) // 댓글작성자(9주차글작성자와같은방법)
    @JoinColumn(name = "member_id", nullable = false) // 외래키→ member.id
    private Member author;
    @Column(nullable = false) // 작성시간
    private LocalDateTime createdAt;
}