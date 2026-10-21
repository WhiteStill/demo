package com.example.demo.controller;

import java.net.URI;
import java.util.List;
// import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.domain.Member;
import com.example.demo.model.dto.MemberForm;
import com.example.demo.model.dto.MemberResponse;
import com.example.demo.model.dto.RoleRequest;
import com.example.demo.model.service.MemberService;

@RestController // 관리자회원관리API (JSON)
@RequestMapping("/api/admin/members") // 주소는하나, 할 일은 메서드로 구분
public class AdminApiController {
    @Autowired
    MemberService memberService;

    @GetMapping // 회원목록: GET /api/admin/members?keyword=student
    public List<MemberResponse> list(@RequestParam(required = false) String keyword) {
        List<Member> members = memberService.search(keyword); // keyword 없으면 전체
        return members.stream().map(MemberResponse::from).toList();
    }

    @GetMapping("/{id}") // 회원1명: GET /api/admin/members/3
    public ResponseEntity<MemberResponse> get(@PathVariable Long id) {
        return memberService.findById(id)
                .map(member -> ResponseEntity.ok(MemberResponse.from(member))) // 있으면200 OK
                .orElse(ResponseEntity.notFound().build()); // 없으면404
    }

    @PostMapping // 회원등록: POST /api/admin/members (본문JSON → MemberForm)
    public ResponseEntity<?> create(@RequestBody MemberForm form) {
            Member saved = memberService.signup(form); // 5주차회원가입로직재사용(BCrypt)
            return ResponseEntity
                    .created(URI.create("/api/admin/members/" + saved.getId())) // 201 + Location
                    .body(MemberResponse.from(saved));
    }

    @PatchMapping("/{id}/role") // 권한변경: PATCH /api/admin/members/3/role {"role":"ADMIN"}
    public ResponseEntity<?> changeRole(@PathVariable Long id, @RequestBody RoleRequest request) {
            Member member = memberService.changeRole(id, request.role());
            return ResponseEntity.ok(MemberResponse.from(member)); // 200 OK + 바뀐회원
    }

    @DeleteMapping("/{id}") // 회원삭제: DELETE /api/admin/members/3
    public ResponseEntity<?> delete(@PathVariable Long id) {
            memberService.deleteMember(id);
            return ResponseEntity.noContent().build(); // 204 No Content (돌려줄 본문 없음)
    }
}