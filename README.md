# PAYHERE - CAFE
<hr>


### 🏗 프로젝트 아키텍쳐   
![Payhere 아키텍쳐 이미지](https://user-images.githubusercontent.com/81751267/232327900-44563bdc-937c-4083-b252-b75a37f77159.png)
- JWT Refresh Token 관리를 위해 Redis를 활용하였습니다.
- JWT 를 활용한 로그인, 로그아웃 구현을 위해 Spring Security를 적용하였습니다.
- 서버 : Java 17 / Spring Boot 3.2.0
- Data : MySQL 5.7 / Redis
- JPA (Hibernate), Spring Security, JWT 등으로 개발하였습니다.
<hr>

### ▶ 실행 방법
- 프로젝트 Root 디렉토리 명령 프롬프트(터미널)에서 다음 커맨드를 입력합니다. 
```java
docker-compose up --build -d
```

<br>
<hr>

### ⚙ ERD
![image](https://github.com/hojinahn4234/Cafe/assets/72196035/7da6f916-b62b-4263-9201-0daed4eef810)
#### MySQL Reverse engineer 를 통한 ERD diagram
<br>

<hr>

### 🗝 API 및 테스트 케이스   
#### Postman을 통해 테스트 진행하였습니다.
<br>


<hr>

### 보완이 필요한 점 - 추가 개발 요망

- **Request Format Exception 처리**   
시간이 부족하여 Exception 처리에 있어서 부족한 부분이 많아 서비스에 부족함이 있습니다.
더 많은 테스트를 진행하여 Exception case 처리에 있어서 보완이 필요합니다.
<br>


<hr>

### 프로젝트 디렉토리   
```java
├─gradle
│  └─wrapper
└─src
    └─main
       ├─java
       │  └─com
       │      └─payhere
       │          └─cafe
       │              ├─config
       │              ├─controller
       │              ├─dto
       │              ├─entity
       │              ├─exceptions
       │              ├─jwt
       │              ├─repository
       │              ├─service
       │              └─util
       └─resources
```
<br>
