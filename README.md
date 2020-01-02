# feedback
=============
encore project

## 프로젝트 소개
커뮤니티의 유저들의 의견을 Deep Learning 분석하여 유저의 글을 긍부정으로 나타내어 게임사에 리포트를 제출

## 요구사항
사용자가 분석 기간를 선택 할 수 있어야 함
유저 의견의 긍부정을 그래프 형태로 나타내야 함
유저들의 관심이 가장 많은 글을 나타내어야함
유저들의 의견을 실기간으로 나타 내어야함

## 정책
### crawling 정책
 - 4개의 커뮤니티을 crawling 함
 - 실시간 crawling
 - crawling한 데이터를 flask를 사용하여 logstash로 보냄
 - logstash에서 data를 Elasticsearch로 보냄

### 분석 기간 정책
 - 사용자가 분석 기간을 정할 수 있어야 함
 - 모든 분석 데이터가 사용자가 정한 기간내로 나타나야함

### 사용한 기술
      - Spring boot
      - JS
      - Css
      - Axios
      - ELK stack(Elasticsearch, Logstash, Kibana)
      - Python selenium
      - Beautifulsoup
      - Pandas
      - Deep Learning
      - konlpy
      - nltk
      - Docker compose
      - DNS
      - CentOs 7
      - Jupyter Notebook
      - maven
