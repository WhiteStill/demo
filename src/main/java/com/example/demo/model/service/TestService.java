package com.example.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.TestDB;
import com.example.demo.model.repository.TestRepository;
import java.util.List;

@Service // 서비스등록, 자동등록됨
public class TestService{
    @Autowired // 객체의존성주입DI(컨테이너내부등록)
    private TestRepository testRepository;
    public TestDB findByName(String name) { // 이름찾기
        return(TestDB) testRepository.findByName(name);
    }

    public List<TestDB> findAll() {
        return testRepository.findAll();
    }
}