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

## 3주차 포트폴리오작성하기완료
templatemo_578_first_portfolio를 사용하여 개인 템플릿 포트폴리오 작성
상단 네비게이션 바에 글씨를 수정 폰트도 수정하여 크기를 키움
기술/경험 부분을 전체적으로 수정
<!-- 외부 <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css" rel="stylesheet"> -->를 cdn 주소를 가져와서 아이콘을 추가교체하였다.
기술 상세페이지를 추가하였다.
resources 하위에폴더에 public 폴더를 생성하고 detailed_web.html를 생성하였다.