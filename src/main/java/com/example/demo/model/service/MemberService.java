package com.example.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.Member;
import com.example.demo.model.dto.MemberForm;
import com.example.demo.model.repository.MemberRepository;
import org.springframework.security.core.userdetails.*; // User, UserDetails등

@Service // 서비스등록
public class MemberService implements UserDetailsService { // 로그인을위한인터페이스제공
    @Autowired
    private MemberRepository memberRepository;
    @Autowired // SecurityConfig 에 등록한BCryptPasswordEncoder 주입
    private PasswordEncoder passwordEncoder;

    public Member signup(MemberForm form) { // 회원가입
        if (memberRepository.existsByUsername(form.getUsername())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }
        Member member = new Member();
        member.setUsername(form.getUsername());
        member.setPassword(passwordEncoder.encode(form.getPassword())); // ★암호화
        member.setName(form.getName());
        member.setRole("USER"); // 기본권한
        return memberRepository.save(member); // INSERT
    }

    @Override // 로그인시 시큐리티가 자동호출
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("회원없음: " + username));
        return User.builder() // 시큐리티용사용자객체로변환
                .username(member.getUsername())
                .password(member.getPassword()) // 암호화값→ matches()로비교
                .roles(member.getRole()) // USER → ROLE_USER
                .build();
         
    }
}