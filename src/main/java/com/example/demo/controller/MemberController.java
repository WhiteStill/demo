package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.domain.Member;
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
            // 비밀번호 확인 검증 (저장 로직보다 먼저)
    if (!memberForm.getPassword().equals(memberForm.getPasswordConfirm())) {
        model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
        return "signup"; // DB 저장 없이 가입 화면으로 복귀
    }
    try {
        memberService.signup(memberForm);
    } catch (IllegalArgumentException e) { // 중복아이디등
        model.addAttribute("error", e.getMessage()); // ${error}
        return "signup"; // 입력값유지한채다시가입화면
    }
    return "redirect:/login?signup"; // 성공→ 로그인화면
    }

    // MemberController (import java.security.Principal, Member)
    @GetMapping("/mypage") // 내정보: 로그인한 사람만
    public String mypage(Principal principal, Model model) { // 현재 로그인 사용자
        Member member = memberService.findByUsername(principal.getName());
        model.addAttribute("member", member);
        return "mypage"; // mypage.html 연결
    }
}