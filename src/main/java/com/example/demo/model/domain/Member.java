package com.example.demo.model.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Member 객체와DB 테이블을매핑. JPA가관리
@Table(name = "member") // 테이블이름은member
@Data // set/get/tostring 등필수메서드자동생성
public class Member {
    @Id // 해당변수가PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가
    private Long id;
    @Column(nullable = false, unique = true, length = 50) // 아이디: 중복불가
    private String username;
    @Column(nullable = false) // 비밀번호: BCrypt 암호화값(60자)
    private String password;
    @Column(nullable = false, length = 50) // 이름
    private String name;
    @Column(nullable = false, length = 20) // 권한: USER (6주차ADMIN)
    private String role;
}