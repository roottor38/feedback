from elasticsearch import Elasticsearch

import numpy as np
import os, json
from time import sleep

from konlpy.tag import Okt
from keras.models import load_model

class RiskScoring:
    def __init__(self, name, host="192.168.1.3:9200"):
        self.es = Elasticsearch(hosts=host)
        self.name = name
        self.path = os.getcwd() + "/py/"
    def read_data(self):
        # es index 내의 doc 갯수를 불러온다
        self.doc_count = self.es.count(index=self.name)
        print(self.doc_count['count'])
        self.doc_text = [' '.join([source['title'], source['body']])\
            for source in [self.es.get(index=self.name, id=i)['_source']\
                for i in range(1, self.doc_count['count'] + 1)]]
    def risk_score(self):
        model = load_model(self.path + "model.h5")
        okt = Okt()
        with open(self.path + "word_index.json", encoding="utf-8-sig") as f:
            word_index = json.load(f)
        def tokenize(doc):
            return ['/'.join(t) for t in okt.pos(doc, norm=True, stem=True)]
        def scoring(doc):
            token = tokenize(doc)
            data = np.expand_dims(np.asarray([token.count(word) for word in word_index]).astype('float32'), axis=0)
            score = float(model.predict(data))
            print(score)
            return score
        self.scored = [scoring(t) for t in self.doc_text]
    def update_es(self):
        bulk_body = []
        for i, el in enumerate(self.scored):    
            bulk_body.append({ "update" : {"_id" : i+1} })
            bulk_body.append({ "doc" : {"risk" : 1 if el >= 0.65 else 0} })
            if (i + 1) % 100 == 0:
                self.es.bulk(index=self.name, body=bulk_body)
                self.es.indices.refresh(index=self.name)
                print("# 중간 저장")
                bulk_body.clear()
        if len(bulk_body) != 0:
            self.es.bulk(index=self.name, body=bulk_body)
        self.es.indices.refresh(index=self.name)
# 각 index마다 title과 body를 대상으로 risk 점수 분석 (model 활용)
# post -> update를 통해 risk 0 or 1 (상위 35%가 risk 문헌)로 넣어준다

scoring = RiskScoring("community_data")
print("// 데이터 읽기")
scoring.read_data()
print("// 위험 점수 매기기")
scoring.risk_score()
print("// 엘라스틱 서치 업데이트")
scoring.update_es()
print("// 인덱스 완료")