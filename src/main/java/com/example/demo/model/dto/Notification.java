package com.example.demo.model.dto;

// [12주차] 단계4 : 개인알림(서버→ 한사람) : to = 받을사람아이디
public record Notification(String to, String text, Long postId) {
}