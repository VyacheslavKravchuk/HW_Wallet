FROM openjdk:17-slim
WORKDIR /app
COPY . .
ENTRYPOINT ["java","-jar","/test_me.jar"]