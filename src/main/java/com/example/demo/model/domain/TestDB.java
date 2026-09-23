package com.example.demo.model.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity // TestDB라는객체와DB 테이블을매핑. JPA가관리
@Table(name = "testdb") // 테이블이름은testdb
@Data // set/get/tostring등필수메서드자동생성
public class TestDB {
    @Id // 해당변수가PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 값이없어도자동으로할당
    private Long id;
    @Column(nullable = true) // 테이블의컬럼설정값을명시
    private String name;
    @Column(nullable = true)
    private Integer age;
    @Column(nullable = true)
    private String gender;
}