# ---- build ----
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# gradle wrapper 및 빌드 스크립트 복사
COPY gradlew gradlew.bat ./
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./

# gradlew 실행 권한 설정
RUN chmod +x gradlew

# 의존성 캐시 최적화 - 소스 코드 변경 시에도 의존성은 재다운로드하지 않음
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사
COPY src/ src/

# JAR 빌드
RUN ./gradlew bootJar -x test --no-daemon

# ---- run ----
FROM eclipse-temurin:21-jre
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxRAMPercentage=75"
WORKDIR /app
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar
EXPOSE 8080
HEALTHCHECK --interval=10s --timeout=3s --retries=10 CMD wget -qO- http://localhost:8080/actuator/health | grep '"status":"UP"' || exit 1
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]