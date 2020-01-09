package com.monkey.dugi.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class) // 1. 테스트 진행 시 JUnit에 내장된 실행자 외에 다른 실행자를 실행한다
                             // 2. 여기서는 SpringRunner라는 스프링 실행자를 사용
                             // 3. 즉, 스프링 부트 테스트와 JUnit 사이에 연결자 역할을 한다.
@WebMvcTest // 1. 여러가지 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중
            // 2. 선언 시 @Controller, @ControllerAdvice 등 사용 가능
            // 3. 단, @Service, @Component, @Repository 등은 사용 불가
            // 4. 여기서는 Controller만 사용하기 때문에 사용
public class HelloControllerTest {

    @Autowired // 1. 스프링이 관리하는 Bean 주입 받기
    private MockMvc mvc; // 1. 웹 API 테스트 시 사용
                         // 2. 스프링 MVC 테스트 시작점
                         // 3. HTTP GET, POST 등에 대한 API 테스트 가능

    @Test
    public void hello가_리턴된다()  throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) //1. MockMvc를 통해 /hello 주소로 HTTP GET 요청
                                              // 2. 체이닝 지원으로 아래와 같이 여러 검증 기능을 이어서 선언 가능
                .andExpect(status().isOk()) // 1. mvc.perform의 결과 검증
                                            // 2. HTTP Header의 Status 검증
                                            // 3. 우리가 흔히 알고 있는 200, 404, 500 등의 상태 검증
                                            // 4. 여기서는 OK 즉, 200인지 아닌지 검증
                .andExpect(content().string(hello)); // 1. mvc.perform의 결과 검증
                                                     // 2. 응답 본문의 내용 검증
                                                     // 3. Controller에서 "hello"를 리턴하기 때문에 이 값이 맞는지 검증
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                            .param("name", name) // param
                                                        // 1. API 테스트 시 사용될 요청 파라미터 설정
                                                        // 2. 단, 값은 String만 허용한다.
                                                        // 3. 즉, 숫자/날짜 등의 데이터도 등록할 때는 문자열로 변경해야 한다.
                            .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) // jsonPath
                                                                   // 1. JSON 응답값을 필으별로 검증할 수 있는 메서드
                                                                   // 2. $를 기준으로 필드명 명시
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
