FROM alpine/java:21-jdk

WORKDIR /app

# # 필수 패키지
# RUN apt-get update && apt-get install -y findutils

COPY . .
# RUN chmod +x gradlew
RUN ./gradlew clean build -x test 

CMD ["java", "-jar", "./build/libs/eggtec-0.0.1-SNAPSHOT.jar"]