# 스프링부트 + JPA + AWS 기초
# 버전
- SpringBoot : 2.7.1.RELEASE
- lombok : 1.18.8
- gradle : 4.10.2
- Junit : 4.12

# 정리
### - [에러](https://github.com/monkeyDugi/springboot2-webservice#%EC%97%90%EB%9F%AC)
- [@RequiredArgsConstructor 에러](https://github.com/monkeyDugi/springboot2-webservice#%EC%97%90%EB%9F%AC)

### - [setter는 지양한다](https://github.com/monkeyDugi/springboot2-webservice#setter%EB%8A%94-%EC%A7%80%EC%96%91%ED%95%9C%EB%8B%A4)
### - [생성자 보다 Builder를 지향한다](https://github.com/monkeyDugi/springboot2-webservice#%EC%83%9D%EC%84%B1%EC%9E%90-%EB%B3%B4%EB%8B%A4-builder%EB%A5%BC-%EC%A7%80%ED%96%A5%ED%95%9C%EB%8B%A4)

### - [domain패키지](https://github.com/monkeyDugi/springboot2-webservice#domain-%ED%8C%A8%ED%82%A4%EC%A7%80)
### - [application.properties](https://github.com/monkeyDugi/springboot2-webservice#applicationproperties-%EC%84%A4%EC%A0%95)
- [쿼리로그 보기 설정](https://github.com/monkeyDugi/springboot2-webservice#--%EC%BF%BC%EB%A6%AC%EB%A1%9C%EA%B7%B8-%EB%B3%B4%EA%B8%B0-%EC%84%A4%EC%A0%95)

### - [등록/수정/조회 API 만들기](https://github.com/monkeyDugi/springboot2-webservice#%EB%93%B1%EB%A1%9D%EC%88%98%EC%A0%95%EC%A1%B0%ED%9A%8C-api-%EB%A7%8C%EB%93%A4%EA%B8%B0)
- [API를 만들기 위해서는 총 3개의 클래스가 필요하다](https://github.com/monkeyDugi/springboot2-webservice#--api%EB%A5%BC-%EB%A7%8C%EB%93%A4%EA%B8%B0-%EC%9C%84%ED%95%B4%EC%84%9C%EB%8A%94-%EC%B4%9D-3%EA%B0%9C%EC%9D%98-%ED%81%B4%EB%9E%98%EC%8A%A4%EA%B0%80-%ED%95%84%EC%9A%94%ED%95%98%EB%8B%A4page101)
- [로직 처리는 어디서 할까?](https://github.com/monkeyDugi/springboot2-webservice#--%EB%A1%9C%EC%A7%81-%EC%B2%98%EB%A6%AC%EB%8A%94-%EC%96%B4%EB%94%94%EC%84%9C-%ED%95%A0%EA%B9%8Cpage101)
- [Dto와 Entity 클래스](https://github.com/monkeyDugi/springboot2-webservice#--dto%EC%99%80-entity-%ED%81%B4%EB%9E%98%EC%8A%A4page108)

### - [TestRestTemplate](https://github.com/monkeyDugi/springboot2-webservice#--%EC%98%81%EC%86%8D%EC%84%B1-%EC%BB%A8%ED%85%8D%EC%8A%A4%ED%8A%B8page113)
### - [HttpEntity란](https://github.com/monkeyDugi/springboot2-webservice#--testresttemplate%EB%9E%80)
### - [영속성 컨텍스트](https://github.com/monkeyDugi/springboot2-webservice#--httpentity%EB%9E%80-1)
### - [IOC란](https://github.com/monkeyDugi/springboot2-webservice#--ioc%EB%9E%80inversion-of-control-%EC%A0%9C%EC%96%B4%EC%9D%98-%EC%97%AD%EC%A0%84)
### - [DI란](https://github.com/monkeyDugi/springboot2-webservice#--didependency-injection-%EC%9D%98%EC%A1%B4%EC%84%B1-%EC%A3%BC%EC%9E%85)
### - [JPA Auditing으로 생성시간/수정시간 자동화 하기](https://github.com/monkeyDugi/springboot2-webservice#--jpa-auditing%EC%9C%BC%EB%A1%9C-%EC%83%9D%EC%84%B1%EC%8B%9C%EA%B0%84%EC%88%98%EC%A0%95%EC%8B%9C%EA%B0%84-%EC%9E%90%EB%8F%99%ED%99%94-%ED%95%98%EA%B8%B0-1)
#

### 에러
####   - @RequiredArgsConstructor 에러
- 에러 메세지
```
variable amount not initialized in the default constructor private final int amount;
```

<br/>

- 원인
  - gradle -> wrapper -> gradle-wrapper.properties에 가보면 
    gradle 버전이 5.2.1이라서 gradle이 lombok을 제대로 가져오지 못했다. build.gradle 파일의 내용을 보면
    ```
    compile('org.projectlombok:lombok')
    ```
    이렇게 되어 있는데 4.10.2까지만 정상으로 동작한다.

<br/>

- 해결
  - gradle을 다운 그레이드 한다.
    ```
    $ gradlew wrapper --gradle-version 4.10.2
    ```
#

### setter는 지양한다.
- 이유 : 
  - 해당 인스턴스 값들이 언제 어디서 변해야 하는지 코드상으로 구분이 불가능 하다.

<br/>

- 방안
  - 필드의 값 변경이 필요한 경우 명확한 목적과 의도를 나타내는 메서드를 추가해야만 한다.

<br/>

- 예시 : 주문 취소 메서드를 만든다
  - 잘못된 예
    ```java
    public class Order {
        public void setStatus(boolean status) {
            this.status = status;
        }
    }

    public void 주문서비스의_취소이벤트 () {
        order.setStatus(false);
    }
    ```

  - 올바른 예
    ```java
    public class Order {
        public void cancelOrder() {
            this.status = false;
        }
    }

    public void 주문서비스의_취소이벤트 () {
        order.cancelOrder();
    }
    ```

#

### 생성자 보다 Builder를 지향한다
- 생성자는 각 매개변수가 뭘 말하는지 알기 어렵다. 실수로 인자를 잘못 넣어도 전혀 에러가 아니기 때문에 실수가 생긴다.
  ```java
  public Example(string a, String b) {
      this.a = a;
      this.b = b;
  }
  // 이렇게 넣어도 전혀 문제가 없기 때문에 개발자가 실수를 범할 가능성이 높다
  new Example(b, a);
  ```

<br/>

- 빌더는 명확하게 어느 필드에 어떤 값을 넣어야 할지 알 수 있다.
  ```java
  Example.builder()
      .a(a)
      .b(b)
      .build();
  ```
#

### domain 패키지
- MyBatis에서 Dao
- Entity와 Repository를 같이 묶는 이유
  - Entity 클래스는 DB와 매핑되기 때문에 Repository 없이는 의미가 없기 때문에 항상 같이 다녀야 한다.
    나중에 프로젝트가 커져 분리를 할 때도 항상 같이 다녀야 한다.

#

### application.properties 설정
#### - 쿼리로그 보기 설정
```
# jpa 쿼리 로그
spring.jpa.show_sql=true
# MySQL 버전으로 쿼리 로그 설정
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
# 로컬에서만 사용할 테스트용 H2 웹 콘솔 활성화
spring.h2.console.enabled=true
```

#

### 등록/수정/조회 API 만들기
#### - API를 만들기 위해서는 총 3개의 클래스가 필요하다.(page.101)
1. Request 데이터를 받을 Dto
2. API 요청을 받을 Controller
3. 트랜잭션, 도메인 기능 간의 순서를 보장하는 Service

 내가 기존에 알던 방식과 다른 부분은 3번의 Service는 비지니스 로직을 처리하는 곳이 아니라 트랜잭션, 도메인 간 순서 보장의
 역할만 한다는 부분이다. 

<br/>

#### - 로직 처리는 어디서 할까?(page.101)
로직 처리는 Domain에서 하며 밑에 설명을 참고
- Web Layer
- Service Layer
- Repository Layer
- Dtos
- Domain Model

<br/>

#### - Dto와 Entity 클래스(page.108)
- Entity 클래스와 거의 비슷한 형태인데 따로 Dto 클래스를 두는 이유
1. Entity 클래스는 DB와 연결된 핵심 클래스로서 테이블이 생성되고, 스키마가 변경되는 클래스이다
2. 화면 변경은 굉장히 사소한 기능 변경인데 이를 위해 테이블과 연결된 Entity 클래스를 변경하는 것은 너무 큰 변경임
3. 수많은 서비스 클래스나 비지니스 로직들이 Entity 클래스를 기준으로 동작하기 때문에, 
   Entity 클래스가 변경되면 많은 영향을 끼친다.
4. 반면, Request, Response용으로 사용되는 Dot는 View를 위한 클래스라 수시로 변경되어도 된다.
5. 즉, View Layer와 DB Layer는 꼭 분리를 하자

<br/>

#### - 영속성 컨텍스트(page.113)
- 소스를 보면 repository에 update 날리는 부분이 없는데 update가 가능한이유는 영속성 컨텍스트 때문이다.
1. JPA의 영속성 컨텍스트 이기 때문이다.
2. 영속성 컨텍스트란 엔티티를 영구 저장하는 환경이다.
3. JPA의 핵심은 엔티티가 영속성 컨테이너에 있냐 아니냐 이다.
4. JPA의 엔티티 매니저가 활성화된 상태(Sptring Boot Jpa는 디폴트)로 트랜잭션 안에서 DB에서 데이터를 가져오면
    영속성 컨텍스트가 유지된 상태이다.
5. 이 상태에서 Entity 객체 값을 변경하면 트랜잭션이 끝나는 시점에 자동으로 update를 해서 update 쿼리를 날릴 필요가 없는 
   것 이다.
6. 이러한 개념을 더티체킹 이라고 한다.
```java
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new
                IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }
```

#

### - TestRestTemplate란
- @WebMvcTest의 경우 JPA기능이 작동하지 않기 때문에 사용하며, 스프링3.x부터 지원
```
TestRestTemplate(org.springframework.boot.test.web.client.TestRestTemplate)
```
![1](https://user-images.githubusercontent.com/53487385/72347197-eb06f300-371a-11ea-8952-6909c27fbc1f.PNG)

<br/>

### - HttpEntity란

1. HttpEntity는 Http 프로토콜 통신의 header, body정보를 저장할 수 있다.
2. 이를 상속받은 클래스로 ResponseEntity, RequestEntity가 있다.
3. 즉, 저장하는 것이 request이면 ReqeustEntity, response이면 ResoponseEntity가 관리 하게 된다.
[공문참고](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/http/HttpEntity.html)

#

### - IOC란(Inversion Of Control, 제어의 역전)
- 의존성 주입(Dependency Injection) 해주는 일의 제어권이 Inversion(역적) 된 것
- 의존성 주입을 본인(SampleController)가 하는 것이 아니라 밖에서 하는 것  
1. java만을 이용한 코드의 기본적인 모양을 보면
```java
class SampleController {
    // 1. 생성하지 않고 선언만 하고
    private SampleRepository sampleRepository;
    // 2. 생성자로 OwnerRepository를 주입 받는다.
    public SampleController(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }
    // 3. 주입 받은 SampleRepository를 사용한다.
    public void doSomething() {
        sampleRepository.save();
    }
}

class SampleControllerTest {
    // 4. 여기서 의존성 주입을 해주고 있다.
    @Test
    public void testDoSomthing() {
        SampleRepository sampleRepository = new SampleRepository();
        SampleController sampleController = new SampleController(sampleRepository);
        sampleController.doSomething();
    }
}
```
위와 같이 스프링을 사용하지 않고 예를 들어 보았다.

<br/>

2. 스프링 Ioc(Inversion of Control) 컨테이너(나중에 정리)
- Bean을 만들고 Bean사이의 의존성을 엮어주고, Bean을 제공 해주는 역할
(* Bean : 스프링이 관리하는 객체)
- 스프링 IoC컨테이너 안에 들어 있는 Bean끼리만 의존성 주입이 가능하다.

#

### - DI(Dependency Injection), 의존성 주입
- 필드(@Autowired)
```java
@Autowired
private SampleRepository sampleRepository;
```

<br/>

- 생성자
```java
private SampleRepository sampleRepository;

public SampleController(SampleRepository sampleRepository) {
    this.sampleRepository = sampleRepository;
}
```

<br/>

- setter
```java
private SampleRepository sampleRepository;

public void SetSampleRepository(SampleRepository sampleRepository) {
    this.sampleRepository = sampleRepository;
}
```
스프링에서는 생성자 주입 방식을 권장하는데 이유는  
[IOC란]의 1번을 참고 해보면, 반드시 SampleRepository가 있어야 SampleController를 사용할 수 있는  
상황을 만들 수 있기 때문에 안전하기 때문이다. 

#

### - JPA Auditing으로 생성시간/수정시간 자동화 하기
```java
/*
모든 Entity의 상위 클래스가 되어 Entity들의 createDate, modifiedDate를 자동으로 관리하는 역할
 */
package com.monkey.dugi.springboot.domain.posts;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들(createDate, modifiedDate)도 컬럼으로 인식하도록 함
@EntityListeners(AuditingEntityListener.class) // BaseTiemEntity클래스에 Auditing 기능 포함
public class BaseTimeEntity {

    @CreatedDate // Entity 저장 시 시간 자동 저장
    private LocalDateTime createDate;

    @LastModifiedDate // 조회한  Entity 값을 변경할 때 시간이 자동 저장
    private LocalDateTime modifiedDate;
}
```

<br/>

Apllaication.class에
```java
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
```

#