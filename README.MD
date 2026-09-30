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
