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
         if not opt and self.es.indices.exists(self.name):
            print("# 해당 인덱스 초기화를 위한 삭제")



collector = DataCollector(name="community_data")# 객체 생성
collector.check_index(False)
        
