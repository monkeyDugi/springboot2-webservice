#!/bin/bash

# Jar를 배포하여 실행하는 파일


REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=springboot2-webservice

echo "> Build 파일 복사"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -fl springboot2-webservice | grep jar | awk '{print $1}') # 실행 중인 스프링부트 프로젝트가 있으면 종료하기 위해 애플리케이션의 프로세스 ID를 찾음.
                                                                              # 스프링 부트 애플리케이션 이름(springboot2-webservice)으로 된 다른 프로그램들이 있을 수 있어
                                                                              # springboot2-webservice된 jar 프로세스를 찾고, awk '{print $1}' 이 명령어로 ID를 찾는다.

echo "> 현재 구동 중인 애플리케이션 pid: $CURRENT_PID"
if [ -z "$CURRENT_PID" ]; then
   echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
   echo "> kill -15 $CURRENT_PID"
   kill -15 $CURRENT_PID
   sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME에 실행권한 추가"
chmod +x $JAR_NAME # jar 파일은 실행 권한이 없는 상태이므로 권한 추가
                   # nohup으로 실행할 수 있게 실행권한 부여(nohup : 터미널 꺼도 애플리케이션 계속 실행)

echo "> $JAR_NAME 실행"
nohup java -jar \
        -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
        -Dspring.profiles.active=real \
        $JAR_NAME > $REPOSITORY/nohup.out 2>&1 & # nohup 실행 시 codedeploy는 무한 대기하는 이슈 발생
                                                 # 이 이유슬 해결하기 위해 nohup.out 파일을 표준 입출력용으로 별도로 사용
                                                 # 이렇게 하지 않으면 nouhup.out 파일이 생기지 않고, codedeploy 로그에 표준 입출력이 출력 됨
                                                 # nohup이 끝나기 전까지 codedeploy도 끝나지 않으니 꼭 이렇게 해야만 함.
