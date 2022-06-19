# 확장자 서비스 API(Extension)

- 확장자 최대 길이는 20자이며, 일반적으로 사용되는 확장자를 위해서 특수문자는 제외하고 영문자와 숫자만을 허용합니다.
- 커스텀 확장자는 최대 200개까지 저장할 수 있습니다.
- 등록 시에는 확장자 중복 체크 및 커스텀 확장자일 경우 200개 제한을 초과하는지 체크 후 등록합니다.
- 수정 기능은 used(체크 여부) 만을 수정할 수 있습니다.
- 목록 조회 시에는 타입 별로 해당되는 타입의 목록을 가져옵니다.

### Requirements

- Java >= 1.8

### 기본 구성

- Java 1.8
- Spring Boot 2.7.1-SNAPSHOT
- H2 Database
- lombok
- MapStruct
- CheckStyle
- Gradle
- Swagger
- Docker

### 빌드

```
gradle build
```

### 서버 실행

```
java -jar .\build\libs\extension-0.0.1-SNAPSHOT.jar
```

### 엔드포인트 목록
- http://localhost:8080/api/v1/extensions (HTTP:POST)
- http://localhost:8080/api/v1/extensions (HTTP:GET)
- http://localhost:8080/api/v1/extensions/{id} (HTTP:PATCH)
- http://localhost:8080/api/v1/extensions/{id} (HTTP:DELETE)

### 목록 조회
- 파라미터: type(CUSTOM, FIXED)
- 예시) http://localhost:8080/api/v1/extensions?type=CUSTOM

### Swagger

- http://localhost:8080/swagger-ui/index.html
