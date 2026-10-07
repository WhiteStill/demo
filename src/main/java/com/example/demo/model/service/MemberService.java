package com.example.demo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.Member;
import com.example.demo.model.dto.MemberForm;
import com.example.demo.model.repository.MemberRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.*; // User, UserDetails등

@Service // 서비스등록
public class MemberService implements UserDetailsService { // 로그인을위한인터페이스제공
    @Autowired
    private MemberRepository memberRepository;
    @Autowired // SecurityConfig 에 등록한BCryptPasswordEncoder 주입
    private PasswordEncoder passwordEncoder;
    private static final List<String> ROLES = List.of("USER", "ADMIN");

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

    // MemberService : 로그인한아이디로회원조회
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException(
        "회원이존재하지않습니다: " + username));   
    }

    public List<Member> findAll() { // MemberService : 전체회원(id 순서)
        return memberRepository.findAll(Sort.by("id"));
    }

    // 관리자가부여할수있는권한(화면에서 넘어온 값을 그대로 믿지않는다)
    @PreAuthorize("hasRole('ADMIN')") // 관리자만실행가능(2차잠금)
    public void changeRole(Long id, String role) { // 권한변경
        if (!ROLES.contains(role)) { // 허용된 권한만
            throw new IllegalArgumentException("허용되지 않는권한 입니다: " + role);
        }
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        member.setRole(role);
        memberRepository.save(member); // UPDATE (id 가있으면수정)
    }

    @PreAuthorize("hasRole('ADMIN')") // 관리자만실행가능(2차잠금)
    public void deleteMember(Long id) { // 회원삭제
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        memberRepository.delete(member); // DELETE
    }
}