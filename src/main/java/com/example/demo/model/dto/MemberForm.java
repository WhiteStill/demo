package com.example.demo.model.dto;

import lombok.Data;

@Data // set/get/tostring등필수메서드자동생성
public class MemberForm{ // 회원가입화면→ 컨트롤러로전달되는데이터
    private String username; // inputname="username"
    private String password; // inputname="password"
    private String passwordConfirm; // inputname="passwordConfirm"
    private String name; // inputname="name"
}