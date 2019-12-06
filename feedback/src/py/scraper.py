from elasticsearch import Elasticsearch
from time import sleep
from bs4 import BeautifulSoup
import re, requests
class DataCollector:
    def __init__(self, name, host="localhost:9200"):
        self.es = Elasticsearch(hosts=host)
        self.name = name
        self.urls = None
    def check_index(self):
        if self.es.indices.exists(self.name): self.es.indices.delete(self.name)
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
                            "time" : {"type": "date", "format": "yyyy-MM-dd"}
                        }}
        }
        self.es.indices.create(index=self.name, body=body)
    def scrape_urls(self, url):
        print("# URL 수집 시작")
        result = []
        for i in range(1, 61):
            print(i)
            sleep(0.3)
            result.extend(BeautifulSoup(requests.get(url, params={"p" : i}).text, "lxml").select(".tr a"))
        print("# URL 수집 완료")
        print(len(result))
        self.urls = map(lambda el : el['href'], result)
    def scrape_bodies(self):
        if self.urls is None :
            print("# 우선 URL 수집을 진행해주세요!")
            return
        bulk_body = []
        community = "인벤"    
        for i, el in enumerate(self.urls):    
            print(el)
            sleep(0.3)
            bulk_body.append({"create" : {"_index" : self.name, "_id" : i+1}})
            soup = BeautifulSoup(requests.get(el).text, "lxml")
            bulk_body.append({"url" : el,
                        "community" : community,
                        "title" : soup.select_one(".articleTitle").text.strip(),
                        "time" : re.search(r'\d{4}-\d{2}-\d{2}', soup.select_one(".articleDate").text.strip()).group(),
                        "body" : re.sub(r'\xa0', "", soup.select_one("#powerbbsContent").text.strip()),
                        "hits" : int(re.search(r'[0-9,]+', soup.select_one(".articleHit").text).group().replace(",", "")),
                        "commments" : int(re.search(r'[0-9,]+', soup.select_one(".articleBottomMenu").text).group().replace(",", "")),
                        "recommand" : int(re.search(r'[0-9,]+', soup.select_one(".reqnum").text).group().replace(",", ""))
                        })
        self.es.bulk(index=self.name, body=bulk_body)
        print("# 벌크 저장 완료")
        self.es.indices.refresh(index=self.name)
        print("# 엘라스틱 서치 반영 완료")
  
collector = DataCollector(name="community_data")
collector.scrape_bodies()
collector.scrape_urls("http://www.inven.co.kr/board/lineagem/5019")
collector.scrape_bodies()