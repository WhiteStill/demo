package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.domain.Post;
import com.example.demo.model.dto.PostForm;
import com.example.demo.model.service.CommentService;
import com.example.demo.model.service.PostService;
@Controller // 게시판화면컨트롤러
@RequestMapping("/board") // 모든URL 앞에/board
public class BoardController {
    @Autowired
    PostService postService;
    @Autowired // ①필드추가(postService 아래)
    CommentService commentService;

    @GetMapping // 목록: GET /board?page=0&keyword=스프링&type=writer(누구나)
    public String list(@RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "all") String type, Model model) {
        Page<Post> posts = postService.findPage(keyword, type, page);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("page", posts);
        model.addAttribute("keyword", keyword); // 검색창에검색어유지, 페이지링크에도붙인다
        model.addAttribute("type", type); // [연습문제 2] 선택 상자 표시 + 페이지 링크에 type 유지
        return "board/list";
    }
    
    @GetMapping("/my") // 내가 쓴 글: GET /board/my?page=0 (로그인 필요)
    public String myList(@RequestParam(defaultValue = "0") int page, Principal principal, Model model) {
        Page<Post> posts = postService.findMinePage(principal.getName(), page);
        model.addAttribute("posts", posts.getContent()); // 이번 페이지 글 10개
        model.addAttribute("page", posts); // 페이지 번호 표시용
        model.addAttribute("pageUrl", "/board/my"); // 페이지 링크를 /board/my?page=1 로
        model.addAttribute("listTitle", "내가 쓴 글"); // 목록 화면 재사용, 제목만 변경
        return "board/list";
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
        Post post = postService.read(id);
        model.addAttribute("post", post);
        model.addAttribute("comments", commentService.findByPost(post)); // ②댓글목록→ 화면에댓글영역
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
