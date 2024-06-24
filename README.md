# LinkUp

## Table of Contents
- Introduction
- Features
- Tech Stack
- Architecture & ERD
- API Documentation
- License
- Contact

## Introduction
#### 링크업(Linkup)은 **사람과 관계를 통해 성장하는 곳**이라는 의미를 가지고 있습니다.

단순히 예약만 하는 플랫폼이 아니라, 소모임을 추가로 제공하여 사용자가 공유오피스를 선택할 때 업무 스타일에 맞는 공간과 휴식 공간을 찾을 수 있도록 도와줍니다. 
이를 통해 공유오피스를 결정할 때 더 깊이 있고 다양하게 선택할 수 있는 서비스를 제공합니다.

#### LINKUP 제작 배경
- COVID-19 팬데믹으로 인해 회사들의 업무 방식이 크게 변화하면서 재택근무와 유연근무제의 빈도가 증가했습니다. 이러한 변화에 맞춰 공유오피스에 대한 수요도 증가하였습니다.
- 수요가 증가함에 따라 기존 공유오피스가 제공하는 다양한 서비스 외에도 사용자 맞춤형 서비스를 제공할 필요성이 대두되었습니다.
- 유연한 근무제를 지원하는 공유오피스를 편리하게 활용할 수 있도록 예약 서비스와 커뮤니티 플랫폼을 제공하는 플랫폼을 제작하게 되었습니다.

#### LINKUP 둘러보기 (or 리소스)
Linkup : [배포 주소](https://linkup-3mw.vercel.app/) 

GitHub : [백엔드 GitHub 레포지토리](https://github.com/Linkup-3mw/backend)

## Features
- 인증/인가
  - JWT 기반 회원가입, 로그인
- 예약
  - 개인 멤버십 및 기업 멤버십
  - 좌석 및 공간 예약
- 소모임 커뮤니티
  - 소모임 생성 및 모집 관리
  - 소모임 내 게시글, 댓글 생성 및 관리

## Tech Stack
- 언어: Java
- 프레임워크: Spring Boot 3.1.1
- DB: MySQL
- 그 외: Redis, Docker, Docker-Compose, Nginx

## Architecture & ERD
### Architecture
![linkup-architecture](https://velog.velcdn.com/images/c0smosaur/post/a32324ff-982e-4799-9653-f5689948d0b4/image.png)

### ERD
![linkup-erd](https://velog.velcdn.com/images/c0smosaur/post/085db9dd-b864-49f7-a199-65cc46799d48/image.png)

## API Documentation
Postman: [포스트맨 문서](https://documenter.getpostman.com/view/25612527/2sA3XWeK6w#ad62c6d1-a9aa-4231-93e9-a397042b45eb)

## License
- PlusX
- 언그래머

## Contact
| 이름 | 이메일                 |
|----|---------------------|
| 강서진 | sjkang539@gmail.com |
| 김애림 | cloim09@gmail.com   |
