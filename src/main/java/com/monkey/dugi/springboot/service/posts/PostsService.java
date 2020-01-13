package com.monkey.dugi.springboot.service.posts;

import com.monkey.dugi.springboot.domain.posts.Posts;
import com.monkey.dugi.springboot.domain.posts.PostsRepository;
import com.monkey.dugi.springboot.web.dto.PostsResponseDto;
import com.monkey.dugi.springboot.web.dto.PostsSaveRequestDto;
import com.monkey.dugi.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    // repository에 update 날리는 부분이 없는데 update가 가능한 이유
    // 1. JPA의 영속성 컨텍스트 이기 때문이다.
    // 2. 영속성 컨텍스트란 엔티티를 영구 저장하는 환경이다.
    // 3. JPA의 핵심은 엔티티가 영속성 컨테이너에 있냐 아니냐 이다.
    // 4. JPA의 엔티티 매니저가 활성화된 상태(Sptring Boot Jpa는 디폴트)로 트랜잭션 안에서 DB에서 데이터를 가져오면
    //    영속성 컨텍스트가 유지된 상태이다.
    // 5. 이 상태에서 Entity 객체 값을 변경하면 트랜잭션이 끝나는 시점에 자동으로 update를 해서 update 쿼리를 날릴 필요가 없는 것 이다.
    // 6. 이러한 개념을 더티체킹 이라고 한다.
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
}
