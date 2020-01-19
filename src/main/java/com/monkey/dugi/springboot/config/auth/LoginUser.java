package com.monkey.dugi.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 1. 이 어노테이션이 생성(적용)될 수 있는 위치를 지정
                               // 2. PARAMETER로 지정했으니 메소드의 파라미터로 선언된 객체에서만 사용할 수 있음
                               // 3. 이 외에도 클래스 선언문에 쓸 수 있는 TYPE 등이 있음
@Retention(RetentionPolicy.RUNTIME)// 어노테이션의 범위로써 어떤 시점까지 어노테이션이 영향을 미치는지 결정
public @interface LoginUser { // 1. 이 파일을 어노테이션 클래스로 지정
                              // 2. LoginUser라는 이름을 가진 어노테이션이 생성 되었다고 보면 됨
}
