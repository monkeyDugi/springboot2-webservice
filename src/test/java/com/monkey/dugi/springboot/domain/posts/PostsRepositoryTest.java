package com.monkey.dugi.springboot.domain.posts;

import javafx.geometry.Pos;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest // H2 DB 자동 실행
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    // @After
    // 1. JUnit에서 단위 테스트가 끝날 때마다 수행되는 메서드를 지정
    // 2. 보통 배포 전 전체 테스트 수행 시 테스트간 데이터 침범을 막기 위해 사용
    // 3. 여러 테스트 진행 시 테스트용 DB인 H2에 데이터가 남아 있어 테스트 실패 가능성이 있다.
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // save
        // 1. posts 테이블에 insert/update 쿼리 실행
        // 2. id 값이 있다면 update, 없다면 insert
        postsRepository.save(Posts.builder()
                    .title(title)
                    .content(content)
                    .author("monkeyDugi@github.com")
                    .build());

        // when
        List<Posts> postsList = postsRepository.findAll(); // posts 테이블의 모든 데이터 조회 쿼리 실행

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        // given
        LocalDateTime now = LocalDateTime.of(2019, 6, 4, 0,0 , 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>>>>>>>>>>>>> createDate=" + posts.getCreateDate() + ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
