package com.example.demo.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.service.CommentService;

@Controller // 댓글컨트롤러: 처리후글상세화면으로돌아간다
@RequestMapping("/board/{postId}/comments") // 댓글은글아래에있다→ 주소도글아래
public class CommentController {
    @Autowired
    CommentService commentService;
    
    @PostMapping // 댓글쓰기: POST /board/3/comments (로그인필요)
    public String write(@PathVariable Long postId,
            @RequestParam(defaultValue = "") String content,
            Principal principal) {
        try {
            commentService.write(postId, content, principal.getName());
            return "redirect:/board/" + postId + "#comments"; // 댓글위치로
        } catch (IllegalArgumentException e) { // 빈댓글, 500자초과
            return "redirect:/board/" + postId + "?commentError#comments";
        }
    }

    @PostMapping("/{id}/delete") // 댓글삭제: POST /board/3/comments/7/delete
    public String delete(@PathVariable Long postId, @PathVariable Long id) {
        commentService.delete(id);
        return "redirect:/board/" + postId + "?commentDeleted#comments";
    }
}