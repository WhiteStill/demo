package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.domain.TestDB;
import com.example.demo.model.service.TestService; // 최상단서비스클래스연동추가
import java.util.List;

@Controller // 컨트롤러 어노테이션 명시
public class DemoController {
    @Autowired
    TestService testService; // DemoController클래스아래객체주입

    @GetMapping("/hello") // 전송방식GET
    // public String hello(Model model) {
    //     model.addAttribute("data", " 반갑습니다."); // model 설정
    //     return "hello"; // hello.html연결
    // }
        public String hello(Model model) {
        model.addAttribute("name", "홍길동");
        model.addAttribute("today", "2026-09-09");
        model.addAttribute("weather", "매우 좋습니다");
        return "hello";
        }

        @GetMapping("/hello2")
        public String hello2(Model model) {
            model.addAttribute("name", "홍길동");
            model.addAttribute("age", 25);
            model.addAttribute("today", "2026-09-09");
            model.addAttribute("weather", "매우 좋습니다");
            model.addAttribute("hobby", "코딩");
            return "hello2";
        }

        @GetMapping("/testdb")
        public String getAllTestDBs(Model model) {
            // TestDB test = testService.findByName("박원빈");
            // model.addAttribute("data4", test);
            // System.out.println("데이터 출력 디버그: " + test);
            List<TestDB> users = testService.findAll();
            model.addAttribute("users", users);
            return "testdb";
        }
}