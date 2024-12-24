# 🎥 영화 정보 및 리뷰 플랫폼 개발 프로젝트

## 📌 프로젝트 목표  
최신 영화 정보를 조회하고, 평점과 리뷰를 공유할 수 있는 커뮤니티 기능을 구현합니다.

---

## 📋 요구사항 분석 및 기능 정의  

### 요구사항 분석  
1. **영화 정보 및 리뷰 조회**: 모든 사용자에게 제공  
2. **커뮤니티 기능**: 로그인한 사용자에게 제공  
3. **영화 상영 정보 편집**: 관리자만 허용  
4. **리뷰 및 평점 수정, 삭제**: 작성자 및 관리자만 허용  

### 구현 기능  
- **최신 영화 인기 순위 조회** (일자별 업데이트)  
- **영화 정보 제공 페이지** (개봉일, 주연배우, 시놉시스 등)  
- **회원가입 및 로그인 시스템**  
- **별점 평가 및 '한줄 리뷰' 작성 기능** (공백 포함 30자 이내)  

---

## 🛠 팀원 구성 및 역할 분담  

- **이** (프론트엔드): 웹 화면 설계, CSS  
- **허선행** (백엔드): REST API 설계, DB 설계  

---

## ⚙️ 프로젝트 수행 시 발생한 문제점 및 해결 방법  

### 문제점: 회원 탈퇴 시 게시물 외래 키 참조 문제  
- **초기 접근**: 회원이 작성한 게시물을 삭제한 후 탈퇴 처리  
- **문제 발견**: 데이터 보존 및 데이터 관계성 유지 문제 발생  

### 해결 방법: 'Soft Delete' 방식 도입  
- **구현 전략**:  
  - 회원 탈퇴 시 계정 정보는 논리적으로 삭제 표시  
  - 실제 데이터는 유지하여 데이터 관계성을 보존  
- **배운 점**:  
  - 데이터 구조 설계의 중요성을 재인식  
  - 설계 단계에서의 철저한 검토와 준비 필요성  

---

## 📝 사용 기술  

- **프론트엔드**: HTML, CSS, JavaScript  
- **백엔드**: Node.js, Express  
- **데이터베이스**: MySQL  
- **버전 관리**: Git, GitHub  

---

## 📅 일정 관리  

| 날짜       | 작업 내용                           | 담당자      |
|------------|-------------------------------------|-------------|
| 12/01~12/05 | 요구사항 분석 및 기능 정의         |          |
| 12/06~12/15 | 프론트엔드 개발                     | 이**         |
| 12/06~12/15 | 백엔드 개발 및 DB 설계              | 허선행       |
| 12/16~12/20 | 기능 통합 및 테스트                 |          |
| 12/21~12/24 | 디버깅 및 최종 배포 준비            |          |

---

## 📚 참고 자료  

- [Node.js 공식 문서](https://nodejs.org/)  
- [Express 공식 문서](https://expressjs.com/)  
- [MySQL 공식 문서](https://dev.mysql.com/doc/)  

---

## 📧 문의  

- **이메일**: example@email.com  
- **깃허브**: [프로젝트 깃허브 링크](https://github.com/example/project)  
