package com.monkey.dugi.springboot.web;

import com.monkey.dugi.springboot.service.posts.PostsService;
import com.monkey.dugi.springboot.web.dto.PostsResponseDto;
import com.monkey.dugi.springboot.web.dto.PostsSaveRequestDto;
import com.monkey.dugi.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    // @Autowire가 없는데 의존성 주입이 된다. 이유는 간단하다
    // @RequiredArgsConstructor가 final이 선언된 모든 필드를 생성자에 포함한다.
    // @Autowire는 지양하며, 생성자 주입 방식을 권장한다.
    // lombok을 사용하여 의존성 주입을 하는 이유는 해당 클래스의 의존성이 변경되면 매번 변경해줄 필요가 없기 때문이다.
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);

        return id;
    }
}
