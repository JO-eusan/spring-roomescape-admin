# 🚪방탈출 예약 관리 애플리케이션

## 요구사항 분석

### 1. 홈 화면

- [x] `/admin`으로 요청시 메인 페이지 응답

### 2. 예약 조회

- [x] `/admin/reservation` 요청 시 예약 관리 페이지 응답
- [x] 예약 관리 페이지 로드 시 호출되는 예약 목록 조회
- [ ] 예약 조회 시, H2 데이터베이스 활용

### 3. 예약 추가 / 취소

- [x] 예약 관리 페이지 내에서 예약 추가
- [x] 예약 관리 페이지 내에서 예약 삭제
- [ ] 예약 추가/취소 시, H2 데이터베이스 활용

## CRUD API 명세

### 예약 목록 조회

* Request
    ```
    GET /reservations HTTP/1.1
    ```
* Response
    ```
    HTTP/1.1 200 
    Content-Type: application/json

    [
      {
        "id": 1,
        "name": "브라운",
        "date": "2023-01-01",
        "time": "10:00"
      },
      {
         "id": 2,
         "name": "브라운",
         "date": "2023-01-02",
         "time": "11:00"
      }
    ]
    ```

### 예약 추가

* Request
    ```
    POST /reservations HTTP/1.1
    content-type: application/json

    {
      "date": "2023-08-05",
      "name": "브라운",
      "time": "15:40"
    }
    ```
* Response
    ```
    HTTP/1.1 200 
    Content-Type: application/json

    {
      "id": 1,
      "name": "브라운",
      "date": "2023-08-05",
      "time": "15:40"
    }
    ```

### 예약 취소

* Request
    ```
    DELETE /reservations/1 HTTP/1.1
    ```
* Response
    ```
    HTTP/1.1 200
    ```
