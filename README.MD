# SPRING BOOT 프로젝트시작! (학번:20231405 이름: 박원빈 )

## 2주차스프링부트개발환경
index.html: 실행/수정완료
hello.html: 실행/수정완료
hello.html: 실행/과제내용추가완료
hello2.html: 실행/과제내용추가완료

<!-- <body>
    <h1>안녕하세요!</h1>
    <p th:text="${name} + '님, 방갑습니다.'"></p>
    <p th:text="'나이: ' + ${age}"></p>
    <p th:text="'오늘은 ' + ${today} + '입니다.'"></p>
    <p th:text="'날씨는 ' + ${weather}"></p>
    <p th:text="'취미는 ' + ${hobby}"></p>

    <a th:href="@{/hello}">홈페이지 메인</a> -->

    DemoController.java 에 getmapping에 추가 내용 작성

            <!-- @GetMapping("/hello2")
        public String hello2(Model model) {
            model.addAttribute("name", "홍길동");
            model.addAttribute("age", 25);
            model.addAttribute("today", "2026-09-09");
            model.addAttribute("weather", "매우 좋습니다");
            model.addAttribute("hobby", "코딩");
            return "hello2";
        } -->

## 3주차 포트폴리오 작성하기 완료
templatemo_578_first_portfolio를 사용하여 개인 템플릿 포트폴리오 작성
상단 네비게이션 바에 글씨를 수정 폰트도 수정하여 크기를 키움
기술/경험 부분을 전체적으로 수정
<!-- 외부 <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css" rel="stylesheet"> -->를 cdn 주소를 가져와서 아이콘을 추가교체하였다.
기술 상세페이지를 추가하였다.
resources 하위에폴더에 public 폴더를 생성하고 detailed_web.html를 생성하였다.
나의 4가지 영역을 수정하였다 (웹, AI, 레벨 디자인, 모델링), 각 4가지의 영역의 이미지를 교체하였다.
프로필 사진을 변경하였다.
상세 페이지를 추가하였다(detailed_AI.html, detailed_Leveldesig.html, detailed_Modeling.html).
Lighthouse 성능 분석을 해보았는데 49가 나왔다.
성능을 저하시키는 것은 Render-blocking requests (렌더링 차단 요청)과 Improve image delivery (이미지 전달 개선)이 가장 성능을 감소시키는 것이로 나타나있다.

## 4주차 데이터베이스 수정하기 완료
mysql를 설치하였다.
mysql의 spring 데이터베이스를 생성하여 데이터베이스 계정 정보를 추가하였다.
프로젝트 폴더 구조를 변경하였다.
service, repository, domain을 생성하였다.
테스트 페이지를 추가하기 위해
데이터베이스 구조를 작성하였고, 리포지토리 클래스 추가하였고, 서비스 클래스를 추가하였고, 컨트롤러 클래스를 추가하였다.
화면 출력 기능을 추가하기 위해 testdb.html를 생성하였다.
localhost:8080/testdb접속하기위해 데이터베이스에서 직접 사용자를 추가하였고 다수의 사용자를 추가하기 위해 DemoController.java을 수정하고, TestService.java을 수정했다.
Templates 폴더에 testdb.html를 오늘의 문법으로 수정하였다.
추가 실습 사용자 추가를 위해 TestDB.java에 아래의 코드를 추가하였다.
@Column(name = "age")
private Integer age;

@Column(name = "gender")
private String gender;
testdb.html를 다음과 같이 수정하였다.
<!-- <table border="1">
        <tr>
            <th>아이디</th>
            <th>이름</th>
            <th>나이</th>
            <th>성별</th>
        </tr>
        <tr th:each="user : ${users}">
            <td th:text="${user.id}"></td>
            <td th:text="${user.name}"></td>
            <td th:text="${user.age}"></td>
            <td th:text="${user.gender}"></td>
        </tr>
    </table>
</body> -->
그리고 데이터 베이스 편집기에 들어가서
UPDATE testdb SET name = '박원빈', age = 26, gender = '남' WHERE id = 1;

INSERT INTO testdb (name, age, gender) VALUES ('아저씨', 40, '남');
INSERT INTO testdb (name, age, gender) VALUES ('김영희', 30, '여');
INSERT INTO testdb (name, age, gender) VALUES ('이철수', 22, '남');

SELECT * FROM testdb;
을 추가하여 수정하였다.

## 5주차 로그인/로그아웃, 암호화완료
보안 설정을 위해 의존성 모듈 2개를 추가하였다.
로그인/로그아웃을 위한 회원을 추가하였다.
보안 설정을 위해 SecurityConfig.java를, 요청 페이지를 위해 MemberController.java를, 회원 엔티티를 위해 Member.java를, 회원 리포지토리를 위해 MemberRepository.java를, 가입 데이터를 위해 Dto 폴더에 MemberForm.java를, 회원가입 서비스를 위해 MemberService.java를 추가하였다.
회원가입 컨트롤러를 추가하기 위해 MemberController.java를 수정하였다.
로그인 연결 시큐리티에 회원 조회 방법 제공을 위해 MemberService.java를 수정하였다.
추가적으로 SecurityConfig.java를 수정하여 로그아웃을 추가하였다.
또 추가적으로 DB 비밀번호 깃허브에 업로드가 되지 않도록 수정하였다.
index.html을 기존에 있던 내 포트폴리오 내용으로 수정하였다.
로그인 상태 유지하는 로직을 개선했는데 SecurityConfig.java에
.rememberMe(remember -> remember // 4. 로그인 상태 유지
                        .key("원하는비밀키문자열")
                        .tokenValiditySeconds(60 * 60 * 24 * 7)); // 7일
을 뒤에 추가하였다. (7일 로그인 상태 유지)
비밀 번호 확인 검증을 위한 로직으로 MemberController.java에 
    public String signup(MemberForm memberForm, Model model) {
            // 비밀번호 확인 검증 (저장 로직보다 먼저)
    if (!memberForm.getPassword().equals(memberForm.getPasswordConfirm())) {
        model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
        return "signup"; // DB 저장 없이 가입 화면으로 복귀 }}
로 추가 과제를 완료하였다.

## 6주차권한(ROLE) 접근제어, 관리자 페이지 분리완료
메뉴에 내 정보 + USER 배지를 추가하였다.
내 정보 페이지를 구현하기 위해 MemberService와MemberController에 코드를 추가였다.
admin 계정을 생성하였다. 그리고 데이터 테이블에서 admin 계정을 관리자 계정으로 승격하였다.
MemberService에 findAll()을 추가하고 관리자가 회원 관리를 위해 AdminController.java 을 생성하였다.
권한 변경·삭제 서비스를 추가하기 위해 MemberService.java에 코드를 추가하였다.
관리자 컨트롤러 추가를 위해 AdminController.java에 코드르 추가하였다.
관리자가 회원관리 (삭제)를 할 수 있는지 확인하였다.
하지만 /admin/members를 주소에 추가하면 회원도 관리자 권한 페이지를 사용할 수 있어
이를 방지 하기 위해 SecurityConfig.java에 URL 권한 규칙을 추가하였다.
메서드 보안을 위해 SecurityConfig.java에 @EnableMethodSecurity (@PreAuthorize 사용) 를 추가하고
MemberService.java에 @PreAuthorize("hasRole('ADMIN')")를 추가하였다.
그럼 회원이 우연히 관리자 권한 페이지를 사용하더라도 이중 잠금 처리가 되어서 회원 정보를 수정, 삭제를 할 수 없다.
관리자 자신을 보호하기 위해 MemberService.java에 import org.springframework.security.core.context.SecurityContextHolder;를 추가하고 
// 대상이 로그인한 본인이면 거부
private void checkNotMyself(Member member) {
    String me = SecurityContextHolder.getContext().getAuthentication().getName();
    if (member.getUsername().equals(me)) {
        throw new IllegalArgumentException("자기 자신의 권한 변경·삭제는 할 수 없습니다.");
    }
}
를 추가하였다. 추가적으로 changeRole과 deleteMember에 checkNotMyself(member);를 추가하였다.
MANAGER 권한 추가을 추가하기 위해 private static final List<String> ROLES = List.of("USER", "MANAGER", "ADMIN");로 변경하였고 SecurityConfig.java에 .requestMatchers("/testdb").hasAnyRole("ADMIN", "MANAGER")를 추가하였다.
회원목록 숨김은 index.html에 <li class="nav-item" sec:authorize="hasAnyRole('ADMIN','MANAGER')">으로 수정하였다.

## 7주차회원관리REST API(JSON) 완료
첫번째 Api를 추가하기 위해 컨트롤러에 MemberApiController.java를 추가하였다.
내 정보를 API에 등록하기 위해 MemberApiController.java에 코드를 추가하였다.
하지만 비밀번호까지 드대로 노출이 된다. 그래서 비밀 번호를 보호하기 위해
MemberResponse.java를 추가하여 필요한 값만 불러내도록 하였다.
Postman : API 를 호출해 보는 프로그램을 설치 및 실행하여 테스트 하였다.
API 보안 설정을 위해 SecurityConfig.java에 코드를 추가하였다.
상태 코드 응답 = 상태 코드 + 헤더 + 본문로 알 수 있다.
관리자가 API – 조회, 검사를 할 수 있도록 controller 폴더에 AdminApiController.java 를 추가하였다.
Postman에서 GET /api/admin/members를 하면 조회가 되는걸 확인 할 수 있다.
관리자가 API - 등록을 추가하기 위해 AdminApiController.java에 코드를 추가하였다.
Postman에서 POST로 회원등록을 하면 추가되는 걸 볼 수 있다.
관리자가 API - 수정 · 삭제를 하기 위해 model/dto 에 RoleRequest.java를 생성하고, MemberService.java 의 changeRole() 을 수정하고 AdminApiController.java에 코드를 추가하였다.
그럼 Postman에서 수정, 삭제, 조회를 할 수 있다.
화면에서 API 사용하기 위해 AdminController.java에 코드를 추가하였다.
그럼 홈페이지 관리자 API에서 간단하게 수정, 삭제, 변경, 조회를 할 수 있다.
회원검색, 아이디나 이름에 검색어가 들어간 회원을 DB에서 찾기 위해 MemberRepository.java에 
import java.util.List와 import org.springframework.data.domain.Sort를 추가하였다.
또한 회원 검색, 검색어가 있을 때와 없을 때를 Service에서 나눠 처리하기 위해 MemberService.java에
public List<Member> search(String keyword) { // 검색어가 없으면 전체, 있으면 아이디·이름 검색
    if (isBlank(keyword)) {
         return findAll();
    }
    String k = keyword.trim();
    return memberRepository.findByUsernameContainingOrNameContaining(k, k, Sort.by("id"));
}
를 추가하였다.
회원 검색 GET /api/admin/members?keyword=test(저는 test로 키워드가 되어 있습니다)를 받기위해 AdminApiController.java에 list()에 @RequestParam(required = false) String keyword 추가하고 search() 호출하도록 하고 오류 처리를 한곳으로 모으기 위해 create(), changeRole(), delete()에서 반복되던 try-catch 3개 제거하였다.
오류 처리를 한곳에서 처리하도록 controller 폴더에 ApiExceptionHandler.java 파일을 새로 만들고
package com.example.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = AdminApiController.class) // 관리자 API 의 오류를 한 곳에서 처리
public class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage())); // 400 {"error": "..."}
    }
}
를 추가하여 에러 같은걸 한곳에서 처리하도록 추가하였다.