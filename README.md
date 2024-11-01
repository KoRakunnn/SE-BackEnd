# SwiftEscaper Backend Web Server
다양한 client(센서, GPS, IPS) 측의 요청을 처리하고 저장하기 위해 Spring Boot기반 Web Server를 구축. 다양한 기능을 갖춘 서버가 아닌 여러 데이터를 수신하고 데이터를 처리하는 것에 중점을 둔 Web Server


# 성과 및 개선 결과
|문제점|해결법|성과|
|:---:|:---:|:---:|
|DB에 저장된 위치 정보를 기반으로 그룹화하고 알람을 전송하는 시간이 너무 오래 소요|위치 테이블의 위도-경도, 터널-절대위치에 Indexing 설정 및 개별적으로 알람을 전송하는 것이 아닌 한번에 Batching|기존에 약 300명의 사용자에게 평균 8초 정도 소요되던 응답시간을 평균 0.7초로 획기적으로 단축

## 역할
* Entire Infra Setting
  * KOREN Cloud Setting
  * Docker Deployment CICD
  * Apache2 Proxy Server Setting
    
* Spring Application
  * 기초 프로젝트 구성
  * 웹소켓
  * 위치별 사용자 Grouping
  * FCM 알람 전송
  * 성능 최적화


## 전체 시스템 아키텍처
![Edge](https://github.com/user-attachments/assets/f3fc1dcc-aa87-4e3e-92ec-34730128a1e6)


## 기술 스택
### Backend
- Java, Spring Boot, MySQL, REST API

### DevOps
- Docker, Github Action, KOREN Cloud, Firebase

### Tools & Others
- IntelliJ, DataGrip, Postman, Firebase Cloud Messaging(FCM)


