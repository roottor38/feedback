from elasticsearch import Elasticsearch
from time import sleep
from bs4 import BeautifulSoup
import re, requests
class DataCollector:
    def __init__(self, name, host="192.168.1.3:9200"):
        self.es = Elasticsearch(hosts=host)
        self.name = name
        self.urls = []
        self.lasturl = ""
    def check_index(self, opt=True):
        ##앞에 왜 not을 넣었는지?
        if not opt and self.es.indices.exists(self.name):
            print("# 해당 인덱스 초기화를 위한 삭제")
            self.es.indices.delete(self.name)
        ##앞에 왜 not을 넣었는지?
        if not self.es.indices.exists(self.name):
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
            self.es.indices.create(index=self.name, body=body)#인덱스 생성
            print("# 해당 인덱스 생성 완료")
    ##
    def scrape_urls(self, url, pages=10):#page는 디폴트로 10
        print("# URL 수집 시작")
        ##self.lastnum??맴버 변수로 선언을 여기서 처음함? 원래 __init에서 하는것이 아니인가? self
        self.lastnum = self.es.count(index=self.name, body={"query" : {"match_all" : {}}})['count']
        ##왜 not?
        if not self.lastnum == 0: 
            self.lasturl = self.es.get(index=self.name, id=self.lastnum)['_source']['url']
            print(self.lasturl)
        for i in range(1, pages+1):
            print("Page", i)
            sleep(0.1)
            for soup in BeautifulSoup(requests.get(url, params={"p" : i}).text, "lxml").select(".tr a"):
                url = re.search(r'\S+/\d+/\d+', soup['href']).group()
                if url == self.lasturl:
                    print("# 이미 수집된 URL => 종료")
                    #리턴을 왜 빈값으로?
                    return
                self.urls.append(url)
        print("# URL 수집 완료")
    
    def scrape_bodies(self):
        #왜 0이지? None이 아니고? 값이 없을때는 null이 아닌 0값으로 집어 넣었나?
        if len(self.urls) == 0 :
            print("# 우선 URL 수집을 진행해주세요!")
            #리턴을 왜 빈값으로?
            return
        fileName = "test0.csv"
        
        for i, el in enumerate(reversed(self.urls)):    
            print(i, el)
            sleep(0.1)
            soup = BeautifulSoup(requests.get(el).text, "lxml")
            #csv_body.append(
            #    soup.select_one(".articleTitle").text.strip() + ", " + re.sub(r'\xa0', "", soup.select_one("#powerbbsContent").text.strip()) + "\n" 
            #)
            #if (i + 1) % 100 == 0:
            #if (i + 1) % 50 == 0:
            #f = open("DataCollector"+str(i)+".csv","w", encoding='utf-8')
            if (i) % 1000 == 0:
                fileName = "test"+ str((i+1)/1000) +".csv"
                
            f = open(fileName, "a", encoding='utf-8')
            data = re.sub(r'\xa0', "", soup.select_one("#powerbbsContent").text.strip()).replace(",", "")
            hearder = soup.select_one(".articleTitle").text.strip().replace(",", "")
            f.write(str(hearder + ", " + data + ", \n" ))
            f.close()
            #csv_body.clear()
      

        ##뭐하는 함수지?
        print("# 최종 저장") 


collector = DataCollector(name="community_data")
collector.check_index(opt=False)
collector.scrape_urls("http://www.inven.co.kr/board/lineagem/5019", pages=100)
collector.scrape_bodies()
