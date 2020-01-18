package com.monkey.dugi.springboot.domain.posts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    // 스프링 시큐리티에서는 권한 코드에 항상 ROLE_이 앞에 있어야 하므로 코드별 키 값을 ROLE_...으로 지정
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
