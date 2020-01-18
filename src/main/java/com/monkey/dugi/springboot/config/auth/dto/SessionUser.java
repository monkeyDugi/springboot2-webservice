package com.monkey.dugi.springboot.config.auth.dto;

import com.monkey.dugi.springboot.domain.posts.User;
import lombok.Getter;

import java.io.Serializable;

/*
세션에는 인증된 사용자 정보만 필요하기 때문에 필요한 것만 쑉!!
 */

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email= user.getEmail();
        this.picture = user.getPicture();
    }
}
