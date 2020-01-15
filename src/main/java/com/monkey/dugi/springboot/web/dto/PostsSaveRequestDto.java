/*
Entity 클래스와 거의 비슷한 형태인데 따로 Dto 클래스를 생성한 이유
1. Entity 클래스는 DB와 연결된 핵심 클래스로서 테이블이 생성되고, 스키마가 변경되는 클래스이다
2. 화면 변경은 굉장히 사소한 기능 변경인데 이를 위해 테이블과 연결된 Entity 클래스를 변경하는 것은 너무 큰 변경임
3. 수많은 서비스 클래스나 비지니스 로직들이 Entity 클래스를 기준으로 동작하기 때문에, Entity 클래스가 변경되면 많은 영향을 끼친다.
4. 반면, Request, Response용으로 사용되는 Dot는 View를 위한 클래스라 수시로 변경되어도 된다.
5. 즉, View Layer와 DB Layer는 꼭 분리를 하자
 */
package com.monkey.dugi.springboot.web.dto;

import com.monkey.dugi.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class  PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
