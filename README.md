# jpa-subway
지하철 정보 관리 애플리케이션

## 개발에 사용된 스펙
- spring-boot
- spring-data-jpa
- h2 database

## 기능목록
- [ ] 지하철 노선 Admin(CRUD)
  - [ ] 지하철 노선 관리 페이지 연동
  - [ ] 지하철 노선 추가 API
  - [ ] 지하철 노선 목록 조회 API
  - [ ] 지하철 노선 수정 API
  - [ ] 지하철 노선 단건 조회 API
  - [ ] 지하철 노선 제거 API
- [ ] 지하철 역 Admin(CRUD)
  - [ ] 지하철 역 관리 페이지 연동
  - [ ] 지하철 역 추가 API
  - [ ] 지하철 역 목록 조회 API
  - [ ] 지하철 역 수정 API
  - [ ] 지하철 역 단건 조회 API
  - [ ] 지하철 역 제거 API
- [ ] 지하철 노선에 역 추가 Admin(CRUD)
  - [ ] 지하철 구간 관리 페이지 연동
  - [ ] 한 노선의 출발역은 하나만 존재하고 단방향으로 관리함
  - [ ] 이전역이 없는 경우 출발역으로 간주
  - [ ] 출발역이 제거될 경우 출발역 다음으로 오던 역이 출발역으로 됨
  - [ ] 중간역이 제거될 경우 재배치를 함
- [ ] 지하철 노선도 조회
  - [ ] 전체 노선 페이지 연동
  - [ ] 모든 지하철 노선과 각 노선에 포함된 지하철역 조회
- [ ] 지하철 경로 조회
  - [ ] 경로 조회 API
  - [ ] 최단 거리 기준으로 경로와 기타 정보를 응답
  - [ ] 총 소요시간, 총 거리 등
  - [ ] 최단 경로가 하나가 아닐 경우 어느 경로든 하나만 응답
  - [ ] 최단 경로 라이브러리 사용
- [ ] 회원 관리 기능 
  - [ ] 회원 가입
  - [ ] 로그인
  - [ ] 회원 수정
  - [ ] 회원 탈퇴
- [ ] 구간 즐겨찾기 관리 기능
  - [ ] 즐겨찾기 페이지 연동
  - [ ] 즐겨찾기 추가
  - [ ] 즐겨찾기 목록 조회/삭제
  
#### <span style="color:blue">지하철 노선에 역 추가</span>
![](https://images.velog.io/images/ljinsk3/post/e5308ed6-b133-43d8-9183-d044de3084d1/image.png)
   
#### <span style="color:blue">지하철 노선에 역 제거</span>
![](https://images.velog.io/images/ljinsk3/post/adec8ecb-2d0e-4e5d-8daf-170186800385/image.png)
## <span style="color:blue">도메인 분석 및 설계</span>
### 엔티티 분석
![](https://images.velog.io/images/ljinsk3/post/7f242826-87a3-42e4-9464-4da3224f413b/image.png)


### 테이블 분석
![](https://images.velog.io/images/ljinsk3/post/c717feb4-d4ac-4da8-9b51-95f22f0aac8a/image.png)

