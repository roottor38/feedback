package feedback.dao;

import java.io.IOException;
import java.util.Arrays;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import feedback.model.dto.KeywordDTO;
import feedback.model.dto.RiskDTO;
import feedback.model.dto.VitalDTO;

@Component
// DTO 기준으로 데이터 교환
public class ElasticSearchDAO {
	// 한 번 Client 생성해서 계속 재사용 (굳이 Close X)
	private RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("192.168.1.3", 9200, "http"), new HttpHost("192.168.1.3", 9201, "http")));
	
	// Risk -> Date, Neg, Pos로 표현된 값 (일별로 구분된 데이터)
	public RiskDTO searchRisk() throws IOException {
		String indexName = "community_data"; // 차후 properties로
		String field = "risk";
		// request (ES로 요청 보내기)
		SearchRequest request = new SearchRequest(indexName);
		// aggregation 설정
		String histName = "date_his";
		AggregationBuilder aggBuilder =
			AggregationBuilders.dateHistogram(histName)
					.field("date").dateHistogramInterval(DateHistogramInterval.days(1))
					.subAggregation(AggregationBuilders.sum(field).field(field));
		// date_his라는 이름의 1일씩 나눈 histogram aggregation 설정 (agg만 받기에 size = 0) => risk값 합산 요청
		request.source(new SearchSourceBuilder().size(0).aggregation(aggBuilder));
		// response (ES로부터 응답 받기)
		SearchResponse response = client.search(request, RequestOptions.DEFAULT);
		ParsedDateHistogram dateHist = response.getAggregations().get(histName);
		// DTO로 리턴
		RiskDTO risk = new RiskDTO();
		// 반환값 대상 형변환 필요한 경우 많음 (주의)
		dateHist.getBuckets().stream() // date별로 구분된 buckets들을 받아서 처리
			.forEach(v -> risk.addData(v.getKeyAsString(),  // 우선 String으로 표현된 날짜들을 받고
				((double)v.getDocCount() - ((ParsedSum)v.getAggregations().getAsMap().get(field)).getValue()), // 긍정 값
				((ParsedSum)v.getAggregations().getAsMap().get(field)).getValue())); // 부정 값
		return risk;
	}
	
	// Keyword -> 많이 언급된 명사들 데이터 (일별) => 최근 일주일 데이터 합산해서 제공해야 함
	public KeywordDTO searchKeyword() throws IOException {
		String indexName = "community_data_analysis"; // 차후 properties로
		// request (ES로 요청 보내기)
		SearchRequest request = new SearchRequest(indexName);
		// Search DSL 구성
		SearchSourceBuilder srcBuilder = new SearchSourceBuilder();
		srcBuilder.query(QueryBuilders.matchAllQuery());
		// 여기서는 match all => Overloading으로 일별 조회 구현
		request.source(srcBuilder);
		// response (ES로부터 응답 받기)
		SearchResponse response = client.search(request, RequestOptions.DEFAULT);
		// DTO로 리턴
		KeywordDTO keyword = new KeywordDTO();
		SearchHit[] hitArr = response.getHits().getHits();
		// Risk 구문 참조하여 오늘 이전 최근 7일 데이터 합산할 수 있도록 수정 예정
		int len = hitArr.length;
		for(SearchHit hit : Arrays.copyOfRange(hitArr, len - 8, len - 1)) {
			hit.getSourceAsMap().entrySet().parallelStream()
			.sorted((a, b) -> Integer.parseInt(b.getValue().toString()) - Integer.parseInt(a.getValue().toString()))
			.forEach(v -> keyword.addData(v.getKey().toString(), v.getValue().toString()));
		}
//		hitArr[hitArr.length - 1].getSourceAsMap().entrySet().parallelStream()
//		.sorted((a, b) -> Integer.parseInt(b.getValue().toString()) - Integer.parseInt(a.getValue().toString()))
//		.limit(10).forEach(v -> keyword.addData(v.getKey().toString(), v.getValue().toString()));
		return keyword;
	}
	
	// Vital - 게시글 수 세는 메소드 (이후 삭제 또는 변경 예정으로 별도 리뷰 X)
	public VitalDTO searchVital() throws IOException {
		String index = "community_data";
		String agg = "date_his";
		SearchRequest searchRequest = new SearchRequest(index);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		
		searchSourceBuilder.size(0);
		AggregationBuilder aggregationBuilder = AggregationBuilders.dateHistogram(agg).field("date")
				.dateHistogramInterval(DateHistogramInterval.days(1));
		searchSourceBuilder.aggregation(aggregationBuilder);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		Aggregations aggregations = searchResponse.getAggregations();
		ParsedDateHistogram byDate = aggregations.get("date_his");
		VitalDTO active = new VitalDTO();
		byDate.getBuckets().forEach(v -> active.addData(v.getKeyAsString(), v.getDocCount()));
		return active;
	}

}
