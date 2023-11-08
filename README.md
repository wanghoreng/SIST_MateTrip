# MateTrip 🚀
사용자가 자신과 성향이 맞는 동행을 모집할 수 있는 여행 계획 작성 웹 어플리케이션입니다. <br>
모인 인원이 시각적인 방식으로 의견을 조율할 수 있는 기능을 구현하였습니다.<br>

<br>

### Target
- 주변 사람들과 여행 스타일이 맞지 않아 스타일이 맞는 사람과의 동행을 원하는 사람<br>
- 목적이 같은 여러 동행을 모아서 여행 경비를 줄이고 싶은 사람<br>
- 여러 동행자와 여행 계획을 조율할 때 의견 취합하는 수고를 덜기 원하는 사람<br>

<br>

## MateTrip Web Page
> **쌍용강북교육센터(SIST) 에서 진행한 팀 프로젝트** <br>
**팀 구성** : 5인 1조 <br>
**개발기간** : 2023.07 ~ 2023.09 <br>

<br>

## Stack 📚
<div align=left>
  
### Environment
  
![Eclipse IDE](https://img.shields.io/badge/Eclipse%20IDE-2C2255.svg?&style=for-the-badge&logo=Eclipse%20IDE&logoColor=white)
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<br>

### Development
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<br>
<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
<img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
<img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white">
<br>

<img src="https://img.shields.io/badge/MyBatis-D14836?style=for-the-badge&logo=MyBatis&logoColor=white">
<img src="https://img.shields.io/badge/oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
<br>
  
</div>

---

## 회원 및 비회원 유저 플로우 📬

<img width="8628" alt="MateTrip_회원 및 비회원 유저플로우" src="https://github.com/wanghoreng/SIST_MateTrip/assets/140323690/e3f3f8c9-9fbf-4fbb-a21f-3487bcb519a4.jpg"/>
<br>

## ERD 설계 📱

<img src="https://github.com/wanghoreng/SIST_MateTrip/assets/140323690/18c30df3-b841-4dc6-89cc-bb2428794921.jpg"/>
<br>

## 화면 구성 📺
| 메인 페이지  |  플랜 페이지   |
| :-------------------------------------------: | :------------: |
|  <img width="329" src="https://github.com/wanghoreng/SIST_MateTrip/assets/140323690/3c960021-fa85-44cd-bf61-b645c9d3dee7.png"/> |  <img width="329" src="https://github.com/wanghoreng/SIST_MateTrip/assets/140323690/a2a0e109-d5fc-49c4-9188-43cc2551c064.png"/>|  
| 마이페이지   |  관리자 페이지   |  
| <img width="329" src="https://github.com/wanghoreng/SIST_MateTrip/assets/140323690/a35577d1-248a-4f6b-bfb9-279d62daf029.png"/>   |  <img width="329" src="https://github.com/wanghoreng/SIST_MateTrip/assets/140323690/0f6d7f1a-02b5-4961-a52a-2525f2e6565b.png"/>     |


<br>

## 주요 기능 📦
#### 🌟 13가지의 여행 스타일, 지역, 연령대 등 자세한 분류 검색 기능 
- 지역 공공데이터를 활용하여 모집 지역 검색 기능 제공
- Ajax 를 통해 13가지의 여행 성향을 xml 파일에 데이터를 저장하여 검색 및 저장 기능 제공

#### 🌟 시각적인 일차별 여행 계획 기능
- Kakao Map API 를 활용하여 시각적인 동선 및 장소를 확인할 수 있는 기능 제공
  
#### 🌟 다녀온 여행성향 및 지역횟수 통계 기능 
- Google Chart API 를 활용하여 13가지의 여행 성향에 맞춰 다녀온 여행 횟수 통계 기능 제공

#### 🌟 의견 조율을 위한 투표,피드 및 동의의사를 확인하는 Ready 기능 
- 자연어로 소통할 수 있는 게시판 기능 제공
- Ajax 를 활용한 투표 기능 제공
- 동행자의 계획에 대한 동의의사를 확인할 수 있는 일차별 Ready 및 PASS 버튼 기능 제공

<br>

## 부가 기능 📂
- 사용자 회원가입, 사용자 및 관리자 로그인 기능
- 신고 및 계정정지, 탈퇴한 사용자에 대한 관리자 기능
- 동행자 모집 글 작성 및 수정, 공개여부에 따른 비밀번호 기능 제공 
- 알림, 체크리스트 및 가계부 기능 제공
- 사용자 에티켓점수 기능 및 여행 후 동행자 여행매너점수 기능 제공
- 사용자의 편의성을 위한 계획공유 여부를 나눈 계획전달방과 계획공유방에 따른 서로 다른 기능 제공

<br>

#### 프로젝트 상세보고서 다운
<a href="https://github.com/wanghoreng/SIST_MateTrip/files/12905432/1._._._33.pdf">프로젝트 상세보고서 파일</a>
