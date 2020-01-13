package com.monkey.dugi.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter //
@NoArgsConstructor // 1. 기본생성자 자동 추가 => public Posts() {}
@Entity // 1. 테이블과 매핑될 클래스르 나타냄
        // 2. 클래스명의 카멜케이스와 테이블명 언더스코어 네이밍으로 매핑
public class Posts {

    // 1. 해당 테이블의 PK
    // 2. 참고
    //    - 웬만하면 PK는 Long타입의 auto_increment로 한다. MySQL 기준 bigint로 설정 됨.
    //    - 주민번호나 비지니스상 유니크 키나 여러가지 키를 조합한 복합키로 PK를 잡지 않는다.
    //      1. 인덱싱에 좋지 않다.
    //      2. FK를 맺을 시 다른 테이블에서 복합키를 ㄹ전부 갖고 있거나, 중간 테이블을 하나 더 둬야 하는 상황이 생길 수 있다.
    //      3. 유니크 조건이 변경될 경우 PK 전체를 수정해야 하는 일이 생길 수 있다.
    //      4. 즉, 주민번호, 복합키 등은 유니크 키로 별도로 잡는다.
    @Id
    // 1. PK 생성규칙
    // 2. auto_increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1. 테이블의 컬럼을 나타내지만 선언하지 않아도 된다.
    // 2. 사용할 상황은 추가 옵션이 필요할 때 사용한다.
    // 3. ex: title) 문자열 기본값은 varchar(255) -> 500
    //    ex: content) 타입을 text로 바꾼다거나
    @Column(length = 500, nullable = false) //
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 1. 해당 클래스의 빌더 패턴 클래스 생성
             // 2. 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
