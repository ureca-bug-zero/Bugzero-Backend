# 🐞 Bug Zero! Backend

---

## 📌 프로젝트 소개
**Bug Zero!** 는 IT 개발자에게 최적화된 Todo List를 제공하는 서비스입니다.
<br/> 이 프로젝트는 백엔드 애플리케이션으로, 투두리스트 데이터 및 미션을 관리하고 사용자 인터렉션(투두리스트, 캘린더, 친구, 랭킹 등)을 처리합니다.

---

## 🌟 서비스 주요 기능
- 카카오 소셜 로그인을 통한 회원 관리 기능
- 친구 기능
- 달성률 계산을 활용한 캘린더 잔디 심기 기능
- 랭킹 조회 기능
- 개발자 능력 향상을 위한 알고리즘 문제풀이 미션 제공 기능
  

---

## 📂 프로젝트 디렉토리 구조

```plaintext
src
├── main
│   ├── java
│   │   └── com.bugzerobackend
│   │       ├── 🛠️ config               # 설정 파일 (Swagger Security 등)
│   │       ├── 📂 controller           # 컨트롤러 레이어
            ├── 🗂️ converter            # Json Converter
│   │       ├── 🗃️ domain               # 엔티티 클래스
│   │       ├── 📑 DTO                  # 데이터 전송 객체 (DTO)
│   │       ├── 📦 mapper               # MyBatis 매퍼 인터페이스 (@Mapper 사용)
│   │       └── 🧩 service              # 서비스 레이어
│   └── resources
│       ├── ⚙️ application.properties   # Spring Boot 설정 파일
│       ├── 📄 mapper                   # MyBatis 매퍼 XML 파일 (SQL 정의)
│       └── 📜 schema.sql               # 데이터베이스 스키마 SQL
```

---

## 🛠️ 기술 스택
- **언어**: Java 17
- **프레임워크**: Spring Boot 3.4.3
- **데이터베이스**: MySQL
- **보안**: JWT
- **API 통신**: RESTful API, Kakao OAuth 2.0
- **기타**: Hibernate, Gradle, Log4j2

---

## 👩‍💻 팀원 소개
<table >
    <tr>
      <td align="center"><img src="https://avatars.githubusercontent.com/u/88073842?s=400&u=bc39f4c6820808f5c034dc5e210f7ea279bff43c&v=4" width="130"></td>
      <td align="center"><img src="https://avatars.githubusercontent.com/u/164132741?v=4" width="130"></td>
      <td align="center" ><img src="https://avatars.githubusercontent.com/u/195983909?v=4" width="130" borderRadius="100%"></td>
      <td align="center"><img src="https://avatars.githubusercontent.com/u/125029488?v=4" width="130"></td>
      <td align="center" ><img src="https://avatars.githubusercontent.com/u/171488704?v=4" width="130" borderRadius="100%"></td>
    </tr>
    <tr>
      <td align="center"><a href="https://github.com/sujinRo" target="_blank" width="160">sujinRo</a></td>
      <td align="center"><a href="https://github.com/seohyunn2" target="_blank">seohyunn2</a></td>
      <td align="center"><a href="https://github.com/minji-38" target="_blank">minji-38</a></td>
      <td align="center"><a href="https://github.com/Jetty-Lee" target="_blank">Jetty-Lee</a></td>
      <td align="center"><a href="https://github.com/eunchrri" target="_blank">eunchrri</a></td>
    </tr>
  </table>
