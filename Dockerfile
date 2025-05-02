# 베이스 이미지로 JDK 17 사용
FROM eclipse-temurin:17-jdk-alpine

# JAR 파일 경로를 외부에서 받을 수 있도록 ARG 설정
ARG JAR_FILE=build/libs/*.jar

# JAR 파일을 컨테이너 내부로 복사
COPY ${JAR_FILE} app.jar

# 애플리케이션이 사용하는 포트 노출 (Spring Boot 기본 포트 8080)
EXPOSE 8080

# 컨테이너 시작 시 실행할 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]