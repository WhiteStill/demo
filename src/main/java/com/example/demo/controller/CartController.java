package com.example.demo.controller;

import java.security.Principal;
import java.util.LinkedHashSet;
import java.util.Set;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.domain.Post;
import com.example.demo.model.service.EnrollmentService;
import com.example.demo.model.service.PostService;

@Controller // [11주차] 단계3 : 신청바구니(세션에모집글번호를담아둔다)
@RequestMapping("/cart")
public class CartController {
    @Autowired
    PostService postService;
    @Autowired
    EnrollmentService enrollmentService; // 단계4

    @GetMapping // 바구니화면: GET /cart (로그인필요)
    public String cart(HttpSession session, Principal principal, Model model) {
        model.addAttribute("cartPosts", postService.findAllById(getCart(session))); // 담은모집글
        model.addAttribute("enrollments", enrollmentService.findMine(principal.getName())); // 단계4 : 내신청내역
        return "cart"; // templates/cart.html
    }
    @PostMapping("/{postId}") // 담기: POST /cart/3
    public String add(@PathVariable Long postId, HttpSession session) {
        Post post = postService.findById(postId); // 없는글이면404
        if (!post.isRecruit()) {
            return "redirect:/board/" + postId; // 일반글은담지않는다
        }
        getCart(session).add(postId); // Set : 같은글을두번담아도1개
        return "redirect:/cart";
    }

    @PostMapping("/{postId}/remove") // 빼기: POST /cart/3/remove
    public String remove(@PathVariable Long postId, HttpSession session) {
        getCart(session).remove(postId);
        return "redirect:/cart";
    }
    
    // 세션에서바구니꺼내기(처음이면빈바구니를만들어넣는다)
    @SuppressWarnings("unchecked")
    private Set<Long> getCart(HttpSession session) {
        Set<Long> cart = (Set<Long>) session.getAttribute("cart");
        if (cart == null) {
            cart = new LinkedHashSet<>(); // 담은순서를지키는Set
            session.setAttribute("cart", cart);
        }
        return cart;
    }
    
    @PostMapping("/confirm") // 단계4 : 신청확정: POST /cart/confirm
    public String confirm(HttpSession session, Principal principal, RedirectAttributes redirect) {
        Set<Long> cart = getCart(session);
        try {
            int count = enrollmentService.confirm(principal.getName(), cart);
            cart.clear(); // 성공하면바구니를비운다
            redirect.addFlashAttribute("message", count + "개 모집글 신청이 확정되었습니다.");
        } catch (IllegalStateException e) {
            redirect.addFlashAttribute("error", e.getMessage()); // 실패이유(바구니는그대로)
        }
        return "redirect:/cart";
    }

    @PostMapping("/enrollments/{id}/cancel") // [연습문제 1] 신청 취소: POST /cart/enrollments/3/cancel
    public String cancel(@PathVariable Long id, Principal principal, RedirectAttributes redirect) {
        enrollmentService.cancel(id, principal.getName());
        redirect.addFlashAttribute("message", "신청이 취소되었습니다.");
        return "redirect:/cart";
    }
}
