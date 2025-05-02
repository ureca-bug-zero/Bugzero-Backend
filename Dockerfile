# 베이스 이미지로 JDK 17 사용
FROM eclipse-temurin:17-jdk-alpine

# JAR 파일을 app.jar로 복사 (정확한 파일명 지정 필요)
COPY build/libs/*.jar app.jar

# 애플리케이션이 사용하는 포트 노출
EXPOSE 8080

# 컨테이너 시작 시 실행할 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]
