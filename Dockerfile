FROM eclipse-temurin:17-jdk-alpine

#시스템 타임존을 Asia/Seoul로 설정
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    echo "Asia/Seoul" > /etc/timezone

#JVM 시간대를 KST로 설정
ENV TZ=Asia/Seoul

COPY build/libs/bugzero-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar", "/app.jar"]
