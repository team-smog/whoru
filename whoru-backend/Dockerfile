FROM openjdk:21

# 인자 설정 - JAR_File
ARG JAR_FILE=build/libs/*SNAPSHOT.jar
ARG JASYPT_KEY

ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY

#ARG JASYPT_KEY
ENV JASYPT_KEY=$JASYPT_KEY
ENV AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
ENV AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY

# jar 파일 복제
COPY ${JAR_FILE} app.jar

# 인자 설정 부분과 jar 파일 복제 부분 합쳐서 진행해도 무방
#COPY build/libs/*.jar app.jar
EXPOSE 8080
# 실행 명령어
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-Dencryptor.key=${JASYPT_KEY}", "-DAWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}","-DAWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}","-jar", "app.jar"]