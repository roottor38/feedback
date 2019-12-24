from elasticsearch import Elasticsearch
from functools import reduce
from konlpy.tag import Okt
from collections import Counter
import json
class WordCount:
    def __init__(self, name, host="192.168.1.3:9200"):
        self.es = Elasticsearch(hosts=host)
        self.name = name
        
    def merge_text(self, date):#날짜와 text를 뽑는다
        self.date = date
        self.text = reduce(lambda x, y: x + " " + y['_source']['title'] + " " + y['_source']['body'],
              self.es.search(index=self.name,
                        body={"size": 1000, "query" : {"match" : {"date" : self.date}}})["hits"]["hits"], "")
    def analyze_text(self):#
        self.result = dict(filter(lambda x: x[1] >= 5, Counter(list(filter(lambda x: len(x) > 1, Okt().nouns(self.text)))).most_common()))
        print(self.result)
    def check_index(self, opt=True):
        if not opt and self.es.indices.exists(self.name + "_analysis"):
            print("# 해당 인덱스 초기화를 위한 삭제")
            self.es.indices.delete(self.name + "_analysis")
        if not self.es.indices.exists(self.name + "_analysis"):
            print("# 해당 인덱스 미존재")
            body = {"mappings" : {"properties" : {"date" : {"type": "date", "format": "yyyy-MM-dd"}}}}
            self.es.indices.create(index=self.name + "_analysis", body=body)
            print("# 해당 인덱스 생성 완료")
    def check_buckets(self):
        body = {"size":0,"aggs":{"date_his":{"date_histogram":{"field":"date","interval":"day"}}}}
        self.buckets = map(lambda x : x['key_as_string'], self.es.search(index=self.name, body=body)["aggregations"]["date_his"]['buckets'])
    def save_analysis(self):    
        self.es.create(index=self.name + "_analysis", id=self.date, body=self.result)
        print("# 엘라스틱 서치 저장 완료")
        self.es.indices.refresh(index=self.name)
        print("# 엘라스틱 서치 반영 완료")
    def roll_buckets(self):
        for date in self.buckets:
            self.merge_text(date)
            self.analyze_text()
            self.check_index()
            self.save_analysis()

counter = WordCount("community_data")
counter.check_index(opt=False)
counter.check_buckets()
counter.roll_buckets()