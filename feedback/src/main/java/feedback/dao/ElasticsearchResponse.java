package feedback.dao;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Component;

import feedback.model.dto.KeywordDTO;
import feedback.model.dto.RiskDTO;

@Component
public class ElasticsearchResponse {
	
	private RestHighLevelClient client = new RestHighLevelClient(
			RestClient.builder(new HttpHost("192.168.1.3", 9200, "http"), new HttpHost("192.168.1.3", 9201, "http")));
	
	@SuppressWarnings("unchecked")
	public JSONArray searchTextReponse(SearchRequest request) throws IOException {
		SearchResponse response = client.search(request, RequestOptions.DEFAULT);
		SearchHit[] hitArr = response.getHits().getHits();
		
		JSONArray text = new JSONArray();
		text.add(hitArr[0].getSourceAsMap());
		
		return text;
	}
	
	public RiskDTO searchRiskReponse(SearchRequest request, String field, String histName) throws IOException {
		SearchResponse response = client.search(request, RequestOptions.DEFAULT);
		ParsedDateHistogram dateHist = response.getAggregations().get(histName);
		RiskDTO risk = new RiskDTO();
		
		dateHist.getBuckets().stream() // date별로 구분된 buckets들을 받아서 처리
		.forEach(v -> risk.addData(v.getKeyAsString(),  // 우선 String으로 표현된 날짜들을 받고
			((double)v.getDocCount() - ((ParsedSum)v.getAggregations().getAsMap().get(field)).getValue()), // 긍정 값
			((ParsedSum)v.getAggregations().getAsMap().get(field)).getValue())); // 부정 값
		
		return risk;
	}
	
	public KeywordDTO searchKeywordReponse(SearchRequest request) throws IOException {
		SearchResponse response = client.search(request, RequestOptions.DEFAULT);
		KeywordDTO keyword = new KeywordDTO();
		SearchHit[] hitArr = response.getHits().getHits();
		for(SearchHit hit : hitArr) {
			hit.getSourceAsMap().entrySet().parallelStream()
			.sorted((a, b) -> Integer.parseInt(b.getValue().toString()) - Integer.parseInt(a.getValue().toString()))
			.forEach(v -> keyword.addData(v.getKey().toString(), v.getValue().toString()));
		}
		return keyword;
	}

}
