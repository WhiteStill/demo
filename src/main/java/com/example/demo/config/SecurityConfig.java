package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 스프링 설정 클래스 등록
@EnableWebSecurity // 스프링 시큐리티 활성화
public class SecurityConfig {
    @Bean // 비밀번호 암호화 객체등록(BCrypt 해시)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // 보안필터 체인등록
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth // 1. URL 접근규칙(인가)
                        .requestMatchers("/", "/hello", "/detailed_web.html",
                                "/login", "/signup", "/error")
                        .permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form // 2. 폼 로그인설정(인증)
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                        .permitAll())
                        .logout(logout -> logout // 3. 로그아웃설정
                        .logoutUrl("/logout") // 로그아웃처리URL (POST)
                        .logoutSuccessUrl("/login?logout") // 로그아웃후이동
                        .invalidateHttpSession(true) // 세션삭제
                        .deleteCookies("JSESSIONID", "remember-me")) // 쿠키삭제
                        .rememberMe(remember -> remember // 4. 로그인 상태 유지
                        .key("원하는비밀키문자열")
                        .tokenValiditySeconds(60 * 60 * 24 * 7)); // 7일
        return http.build();
    }
}