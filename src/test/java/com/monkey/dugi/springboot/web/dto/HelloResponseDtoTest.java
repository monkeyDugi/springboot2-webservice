package com.monkey.dugi.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void lombok_기능_테스트() {
        // given
        String name = "test";
        int amount = 1000;

        // when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // then
        assertThat(dto.getName()).isEqualTo(name);
        // assertThat
        // 1. assertj라는 테스트 검증 라이브러리의 검증 메서드
        // 2. 검증하고 싶은 대상을 메서드 인자로 받음
        // 3. 메서드 체이닝이 지원되어 isEqualTo와 같이 메서드를 이어서 사용 가능
        // 4. JUnit의 assertThat을 사용하면 is()와 같이 CoreMatchers 라이브러리도 필요하다

        // isEqualTo
        // 1. assertj의 동등 비교 메서드
        // 2. assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 때만 테스트 성공

        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
