package com.monkey.dugi.springboot.web;

import com.monkey.dugi.springboot.config.auth.dto.SessionUser;
import com.monkey.dugi.springboot.domain.posts.User;
import com.monkey.dugi.springboot.service.posts.PostsService;
import com.monkey.dugi.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user"); // 1. CustomOAuth2UserService에서 로그인 성공 시 세션에 SessionUser를 저장하도록 했음
                                                                                 // 2. 즉, 로그인 성공 시 httpSession.getAttribute("user")에서 값을 가져울 수 있음
        if (user != null) { // 세션에 값이 있을 때만 model에 userName으로 등록
           model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
