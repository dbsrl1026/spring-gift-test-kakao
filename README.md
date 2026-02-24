# spring-gift-test

Spring Boot 기반 선물하기 서비스 (카카오 연동)

## 기술 스택

- Java 21
- Spring Boot 3.5.8
- Spring Data JPA
- H2 Database (단위 테스트)
- PostgreSQL (Cucumber 테스트)
- Docker Compose
- Cucumber BDD
- RestAssured

## 사전 요구사항

- JDK 21
- Docker & Docker Compose (Cucumber 테스트용)

## 실행 방법

### 테스트 실행

```bash
# Cucumber BDD 테스트 (PostgreSQL + Docker)
./gradlew cucumberTest

# 기존 JUnit 테스트 (H2)
./gradlew test --tests "gift.*AcceptanceTest"

# 전체 테스트
./gradlew test
```

### Docker Compose 수동 관리

```bash
# PostgreSQL 시작
docker compose up -d

# PostgreSQL 중지
docker compose down
```

### 테스트 리포트

테스트 실행 후 리포트 확인:
- JUnit 리포트: `build/reports/tests/test/index.html`
- Cucumber 리포트: `build/reports/cucumber.html`

## 프로젝트 구조

```
├── docker-compose.yml          # PostgreSQL 컨테이너 정의
├── src/
│   ├── main/
│   │   ├── java/gift/
│   │   │   ├── ui/             # REST Controller
│   │   │   ├── application/    # Service, Request DTO
│   │   │   ├── model/          # Entity, Repository
│   │   │   └── infrastructure/
│   │   └── resources/
│   │       ├── application.properties           # H2 (기본)
│   │       └── application-cucumber.properties  # PostgreSQL
│   └── test/
│       ├── java/gift/
│       │   ├── cucumber/
│       │   │   ├── steps/      # Step Definitions
│       │   │   ├── CucumberTest.java
│       │   │   ├── CucumberSpringConfiguration.java
│       │   │   └── TestContext.java
│       │   └── *AcceptanceTest.java  # JUnit 테스트
│       └── resources/features/       # Gherkin Feature 파일
```

## 테스트 환경 분리

| 테스트 유형 | DB | 실행 명령 |
|------------|-----|----------|
| JUnit (기존) | H2 | `./gradlew test --tests "gift.*AcceptanceTest"` |
| Cucumber BDD | PostgreSQL | `./gradlew cucumberTest` |

### Production Parity
PostgreSQL을 사용하여 운영 환경과 동일한 DB로 테스트함으로써, H2와 PostgreSQL 간 SQL 방언 차이로 인한 문제를 사전에 발견할 수 있습니다.
