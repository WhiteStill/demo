package com.example.demo.model.dto;

// 권한변경요청DTO : 본문{"role": "ADMIN"} 을받는다
public record RoleRequest(String role) {
}