package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.domain.Post;
import com.example.demo.model.dto.PostForm;
import com.example.demo.model.service.PostService;
@Controller // 게시판화면컨트롤러
@RequestMapping("/board") // 모든URL 앞에/board
public class BoardController {
    @Autowired
    PostService postService;
    @GetMapping // 목록: GET /board(누구나)
    public String list(Model model) {
        List<Post> posts= postService.findAll();
        model.addAttribute("posts", posts);
        return"board/list";
    }
    
    @GetMapping("/write") // 글쓰기화면: GET /board/write (로그인필요)
    public String writeForm() {
        return "board/form";
    }

    @PostMapping("/write") // 글쓰기처리: POST /board/write
    public String write(PostForm postForm, Principal principal, Model model) {
        try {
            Post post = postService.write(postForm, principal.getName()); // 작성자= 로그인한사람
            return "redirect:/board/" + post.getId(); // 저장후 상세화면으로
        } catch (IllegalArgumentException e) { // 빈제목·내용
            model.addAttribute("error", e.getMessage());
            return "board/form"; // 입력값 유지한채 다시 글쓰기화면
        }
    }
    
    @GetMapping("/{id}") // 상세: GET /board/3 (누구나)
    public String detail(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "board/detail";
    }
    
    @GetMapping("/{id}/edit") // 수정화면: GET /board/3/edit
    public String editForm(@PathVariable Long id, Model model) {
        Post post = postService.findForEdit(id);
        PostForm postForm = new PostForm(); // 기존내용을폼에채워서보여준다
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());
        model.addAttribute("postForm", postForm);
        model.addAttribute("postId", id); // 화면이"수정" 모드로바뀐다
        return "board/form";
    }

    @PostMapping("/{id}/edit") // 수정처리: POST /board/3/edit
    public String edit(@PathVariable Long id, PostForm postForm, Model model) {
        try {
            postService.edit(id, postForm);
            return "redirect:/board/" + id;
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("postId", id);
            return "board/form";
        }
    }

    @PostMapping("/{id}/delete") // 삭제처리: POST /board/3/delete
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/board?deleted"; // 목록+ "삭제되었습니다"
    }
}
