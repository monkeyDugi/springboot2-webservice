/*
- 무중단 배포 시 8081을 쓸지, 8082를 쓸지 판단하는 API
 */

package com.monkey.dugi.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        // env.getActiveProfiles()
        // - 현재 실행 중인 ActiveProfile을 모두 가져옴
        // - 즉, real, oauth, real-db 등이 활성화 되어 있다면(active) 3개가 모듀 담겨 있음.
        // - 여기서 real, real1, real2는 모두 배포에 사용될 profile이라 이 중 하나라도 있으면 그 값을 반환
        // - 실제로 이번 무중단 배포(step3)에서는 real1과 real2만 사용되지만, step2를 다시 사용해 볼 수도 있으니 real도 남겨 둠
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");

        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

        return profiles.stream()
                .filter(realProfiles::contains)
                .findAny() // 병렬처리 시 멀티쓰레드에서 탐색이 수행 되기 때문에 가장 먼저 찾는 값을 리턴하지만 findFirst()는 무조건 순서에 우선순위를 둔다. 병렬처리가 아니면 같은 기능
                .orElse(defaultProfile);

    }
}
