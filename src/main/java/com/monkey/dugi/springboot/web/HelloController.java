package com.monkey.dugi.springboot.web;

import com.monkey.dugi.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello") // JSON반환 컨트롤러
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    // @RequestParam
    // 1. 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
    // 2. 외부에서 name이라는 이름으로(name = 값) 넘긴 파라미터를 String name에 받는다.
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
