import numpy as np
import os, json
from konlpy.tag import Okt
from keras.models import load_model

model = load_model(os.getcwd() + "/py/" + "model.h5")
def predict_risk(doc):
    okt = Okt()
    with open(os.getcwd() + "/py/" + "word_index.json", encoding="utf-8-sig") as f:
        word_index = json.load(f)
    
    def tokenize(doc):
        return ['/'.join(t) for t in okt.pos(doc, norm=True, stem=True)]
        # norm은 정규화(오타 및 띄어쓰기 교정), stem은 근어화
    token = tokenize(doc)
    data = np.expand_dims(np.asarray([token.count(word) for word in word_index]).astype('float32'), axis=0)
    score = float(model.predict(data))
    print("[{}]의 위험 지수는 {:.2f}% 입니다.\n".format(doc, score * 100))

predict_risk(input("문장 입력 : "))