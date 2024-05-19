FROM gradle:7-jdk19 as cache

RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY . /home/gradle/src/
WORKDIR /home/gradle/src
RUN gradle clean build --no-daemon

FROM gradle:7-jdk19 AS build

COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:19-slim

EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/ktor/build/libs/*.jar /app/ptbox.jar

ENTRYPOINT ["java", "-jar", "./app/ptbox.jar"]
