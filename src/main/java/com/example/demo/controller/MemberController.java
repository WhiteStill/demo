package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.dto.MemberForm;
import com.example.demo.model.service.MemberService;
import org.springframework.ui.Model;

@Controller // 컨트롤러어노테이션명시
public class MemberController {
    @GetMapping("/login") // 로그인화면(POST /login 은시큐리티가처리)
    public String login() {
        return "login"; // login.html 연결
    }

    @Autowired
    MemberService memberService; // 클래스상단에객체주입

    @GetMapping("/signup") // 회원가입화면
    public String signupForm() {
        return "signup"; // signup.html 연결
    }

    @PostMapping("/signup") // 회원가입처리
    public String signup(MemberForm memberForm, Model model) {
    try {
        memberService.signup(memberForm);
    } catch (IllegalArgumentException e) { // 중복아이디등
        model.addAttribute("error", e.getMessage()); // ${error}
        return "signup"; // 입력값유지한채다시가입화면
    }
    return "redirect:/login?signup"; // 성공→ 로그인화면
    }
}