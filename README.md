# Spring Boot Bucket4j Pricing Plan

## 1. 설명
Spring Boot Bucket4j 예제이다. 포트는 8080을 사용한다.

버킷의 크기는 3개이며 10초에 3개의 토큰을 충전한다.

API 호출시 토큰 1개를 소비한다.

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
