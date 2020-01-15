from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from time import sleep
from bs4 import BeautifulSoup
import re
import pandas as pd
import numpy as np
from elasticsearch import Elasticsearch

es = Elasticsearch(hosts="localhost:9200")
publicUrl = "#ncCommunityList > ul > li > div.title > a"
currentPage = "#ncCommunityList > div.pagination-container > ul > li.current"
title = "#ncCommunityView > section > div.view-header > h2"
body = "#ncCommunityView > section > div.view-body > div"
date = "span.date"
hits = "span.hit > em"
comments = "span.comment > em"
recommend = "#ncCommunityView > section > div.view-utils > button.co-btn.co-btn-like > em"

driver = webdriver.Chrome('chromedriver.exe')
driver.implicitly_wait(3)


def parse(selector):
    return BeautifulSoup(driver.page_source, 'lxml', from_encoding='utf-8').select(selector)

def checkUrl(url):
    try:
        if es.get(index="community_data", id=url)['_id'] == url:
            print(url)
            print(es.get(index="community_data", id=url)['_id'])
            return True
    except:
        return False
# 해당 Page 게시물 URL 전체 수집
def getUrl(pageNum):
    sleep(1)
    e = []
    for i in range(1,pageNum+1):
        driver.get("https://lineagem.plaync.com/board/all/list?page="+str(i))
        for el in parse(publicUrl):
            if checkUrl(el['href']) == False:
                e.append(el['href'])
    print(len(e))
    return e

# 다음 Page로 이동

def getTitle():
    sleep(1)
    return parse(title)[0].text

def getBody():
    textBody = ""
    for s in parse(body):
        textBody += s.text.replace("\xa0", "")
    return textBody

def getDate():
    textDate = ""
    textDate = parse(date)[0].text
    return textDate.replace(" ","").replace(".","-")[:10]

def getHits():
    return parse(hits)[0].text.replace(",", "")

def getComments():
    if len(parse(comments[0])) == 0:
        return 0
    else:
        return parse(comments)[0].text.replace(",", "")

def getRecommend():
    #sleep(1)
    return parse(recommend)[0].text.replace(",", "")

def check_index(opt=True):
        if not opt and es.indices.exists("community_data"):
            print("# 해당 인덱스 초기화를 위한 삭제")
            es.indices.delete("community_data")
        if not es.indices.exists("community_data"):
            print("# 해당 인덱스 미존재")
            settings = {
                "index": {
                "analysis": {
                    "tokenizer": {
                    "nori_tokenizer_mixed": {
                        "type": "nori_tokenizer",
                        "decompound_mode": "mixed"
                    }
                    },
                    "analyzer": {
                    "korean": {
                        "type": "custom",
                        "tokenizer": "nori_tokenizer_mixed",
                        "filter": [
                        "nori_readingform",
                        "lowercase",
                        "nori_part_of_speech_basic"
                        ]
                    }
                    },
                    "filter": {
                    "nori_part_of_speech_basic": {
                        "type": "nori_part_of_speech",
                        "stoptags": ["E", "IC", "J",
                                    "MAG", "MAJ", "MM",
                                    "SP", "SSC", "SSO", "SC", "SE",
                                    "XPN", "XSA", "XSN", "XSV",
                                    "UNA", "NA", "VSV"]
                    }
                    }
                }
                }
            }
            body = {
            "settings": settings,
            "mappings" : {"properties" :
                            {"title" : {"type" : "text", "analyzer" : "korean"},
                                "body" : {"type" : "text", "analyzer" : "korean"},
                                "date" : {"type": "date", "format": "yyyy-MM-dd"}
                            }}
            }
            es.indices.create(index="community_data", body=body)#인덱스 생성
            print("# 해당 인덱스 생성 완료")

def crawling(pageNum):
    es = Elasticsearch(hosts="localhost:9200")
    test = []
    test2 = []
    for i, url in enumerate(getUrl(pageNum)):
        driver.get(url)
        test.append({"create" : {"_index" : "community_data", "_id" : url}})
        test.append({
                    "community" : "리니지M 공식홈페이지",
                    "title" : getTitle(),
                    "date" : getDate(),
                    "body" : getBody(),
                    "hits" : int(getHits()),
                    "commments" : int(getComments()),
                    "recommand" : int(getRecommend())
                    })
        print(i+1, " " , url)
        if (i+1) % 100 == 0:
            es.bulk(index="community_data", body=test)
            es.indices.refresh(index="community_data")
            print(len(test))
            test.clear()
            print(len(test))
    es.bulk(index="community_data", body=test)
    es.indices.refresh(index="community_data")
    print(len(test))
    print(test)
    test.clear()
    print(len(test))