# react-dockerizing/Dockerfile

# base image 설정(as build 로 완료된 파일을 밑에서 사용할 수 있다.)
FROM node:20.11.1-alpine3.18 as build

# 컨테이너 내부 작업 디렉토리 설정
WORKDIR /app

# 환경변수 설정
ENV VITE_BASE_URL=http://k10d203.p.ssafy.io/api

# app dependencies
# 컨테이너 내부로 package.json 파일들을 복사
COPY package*.json ./


# package.json 및 package-lock.json 파일에 명시된 의존성 패키지들을 설치
RUN npm install

COPY . .

# npm build
RUN npm run build


CMD ["ls", "-al"]

# prod environment
FROM nginx:stable-alpine

# 이전 빌드 단계에서 빌드한 결과물을 /usr/share/nginx/html 으로 복사한다.
COPY --from=build /app/dist /usr/share/nginx/html

# 컨테이너의 3000번 포트를 열어준다.
EXPOSE 3000

ENV TZ=Asia/Seoul
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone

# nginx 서버를 실행하고 백그라운드로 동작하도록 한다.
CMD ["nginx", "-g", "daemon off;"]
