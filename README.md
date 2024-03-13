# Spring Boot Bucket4j Pricing Plan

## 1. 설명
Spring Boot Bucket4j Pricing Plan 예제이다. 포트는 8080을 사용한다.

API 클라이언트 및 가격 계획이다.

* Free: 20 requests per hour per API client

* Basic: 40 requests per hour per API client

* Professional: 100 requests per hour per API client

## 2. 개발환경

* OpenJDK 17

* spring-boot 3.2.3

* bucket4j-core 8.9.0

* Gradle 8.5

## 3. 참고 자료

* [https://www.baeldung.com/spring-bucket4j](https://www.baeldung.com/spring-bucket4j)

## 3. API 실행
버킷을 모두 사용하면 토큰이 충전되기 전까지 조회 실패를 리턴한다.

```shell
## successful request
$ curl -v -X POST http://localhost:8080/api/v1/area/rectangle \
    -H "Content-Type: application/json" -H "X-api-key:FX001-99999" \
    -d '{ "length": 10, "width": 12 }'

< HTTP/1.1 200
< X-Rate-Limit-Remaining: 11
{"shape":"rectangle","area":120.0}

## rejected request
$ curl -v -X POST http://localhost:8080/api/v1/area/rectangle \
    -H "Content-Type: application/json" -H "X-api-key:FX001-99999" \
    -d '{ "length": 10, "width": 12 }'

< HTTP/1.1 429
< X-Rate-Limit-Retry-After-Seconds: 583
```
