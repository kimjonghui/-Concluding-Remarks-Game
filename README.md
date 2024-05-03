# 프로젝트 개요

본 프로젝트는 사용자가 즐길 수 있는 끝말잇기 게임을 개발하는 것을 목적으로 합니다. 게임은 싱글 모드와 멀티 모드로 진행되며 외부 API를 통해 단어 데이터를 불러오고, 이를 데이터베이스에 저장하여 사용합니다.


### 참여 인원

3명

## 사용된 언어 및 기술

![JAVA](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MYSQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)

## 코드 통합 및 관리

![GIT](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)

## 제작 동기 및 목적

• 핵심 가치: 게임을 통한 한글 학습 촉진 및 언어 능력 향상

• 비즈니스적 영향: 교육적 가치를 제공하며, 사용자의 언어 스킬 향상에 기여

• 사용자 및 사용 환경: 한글을 학습하는 학생 및 어른, 혼자서도 즐길 수 있는 게임 환경 제공

## 주요 기능 및 개발 내용

• 초기 GUI 및 데이터베이스 설계: 초기 단계에서 팀원들과 그림판을 사용하여 게임의 메인 화면과 내부에 들어갈 UI 요소들을 설계하고 이후 데이터베이스에 저장될 필요한 값들을 논의

• 외부 API 활용: 웹에서 단어 데이터를 가져오는 API를 선정하고, JSON 파싱을 통해 단어를 추출

### (!클릭하여 확대)

![KakaoTalk_20240502_181012432](https://github.com/kimjonghui/MovieReservation-Webproject-/assets/154950232/984ee3bc-66df-4735-8282-2f2af121ae99)

• 데이터베이스 설계 및 활용: 첫 호출 시 받아온 단어를 데이터베이스에 저장하여, 외부 API 의존성을 줄이고 독립성을 강화

• 게임 로직 개발: 싱글모드에서 타이머를 추가하여 일정 시간 내 답을 하지 못하면 패배하도록 설정함, 한글자나 숫자 등 룰에 어긋나는 문자 입력 시 경고 문구를 표시하도록 처리

### 멀티게임 타임아웃 및 승패

![KakaoTalk_20240502_181314507](https://github.com/kimjonghui/MovieReservation-Webproject-/assets/154950232/ad4bb048-4eea-4ebf-962d-cb77b4c06fae)

### 멀티게임 타이머

![KakaoTalk_20240502_181337870](https://github.com/kimjonghui/MovieReservation-Webproject-/assets/154950232/442d75af-f2c1-477d-9ed3-a1ad68eef869)

### 싱글게임 타임아웃 및 한글자 불가

![KakaoTalk_20240502_181228336](https://github.com/kimjonghui/MovieReservation-Webproject-/assets/154950232/597659c1-2f44-46e6-87c9-e8148bbc786c)

### 싱글게임 숫자 불가

![KakaoTalk_20240502_181412510](https://github.com/kimjonghui/MovieReservation-Webproject-/assets/154950232/42b9d86a-988a-4435-a1ef-7b84f1d94181)

## 기술적 도전과 해결책

• 외부 API 호출 및 JSON 파싱: 외부 API를 호출하고 JSON 데이터를 파싱하여 게임에 필요한 데이터를 추출하기 위해 올바른 호출과 데이터 처리가 필요하여 Json 라이브러리를 추가하고 올바른 값이 전달되는지에 대한 테스트와 수정을 반복함

• 기술 습득: 소켓서버와 쓰레드, 외부 API 호출 등 이전에 배우지 않은 기술들에 대해 구글링과 자료 조사를 통해 학습

## 아쉬웠던 점

• 두음법칙 미적용: 게임 로직에서 두음법칙이 적용되지 않아 일부 게임 플레이가 제한적임, 이 문제를 해결하기 위한 추가적인 로직 구현에 더 많은 고민과 개발에 대한 실력이 필요
