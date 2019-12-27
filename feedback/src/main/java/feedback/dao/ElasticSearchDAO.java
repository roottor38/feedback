package feedback.dao;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feedback.model.dto.KeywordDTO;
import feedback.model.dto.RiskDTO;

@Component
public class ElasticSearchDAO {
	
	@Autowired
	ElasticSearchRequest req;
	
	@Autowired
	ElasticsearchResponse rep;
	
	public JSONArray searchText(LocalDate start, LocalDate end, String community) throws IOException {
		return rep.searchTextReponse(req.searchTextReq(start, end, community));
	}
	
	 //Risk -> Date, Neg, Pos로 표현된 값 (일별로 구분된 데이터)
	public RiskDTO searchRisk(LocalDate start, LocalDate end) throws IOException {
		return rep.searchRiskReponse(req.searchRiskReq(start, end, "risk", "date_his"), "risk", "date_his");
	}
	

	// Keyword -> 많이 언급된 명사들 데이터 (일별) => 최근 일주일 데이터 합산해서 제공해야 함
	public KeywordDTO searchKeyword(LocalDate start, LocalDate end) throws IOException, ParseException {
		return rep.searchKeywordReponse(req.searchKeywordReq(start, end));
	}
	
	// Vital - 게시글 수 세는 메소드 (이후 삭제 또는 변경 예정으로 별도 리뷰 X)
//	public VitalDTO searchVital() throws IOException {
//		String index = "community_data";
//		String agg = "date_his";
//		SearchRequest searchRequest = new SearchRequest(index);
//		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		
//		searchSourceBuilder.size(0);
//		AggregationBuilder aggregationBuilder = AggregationBuilders.dateHistogram(agg).field("date")
//				.dateHistogramInterval(DateHistogramInterval.days(1));
//		searchSourceBuilder.aggregation(aggregationBuilder);
//		searchRequest.source(searchSourceBuilder);
//		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//		Aggregations aggregations = searchResponse.getAggregations();
//		ParsedDateHistogram byDate = aggregations.get("date_his");
//		VitalDTO active = new VitalDTO();
//		byDate.getBuckets().forEach(v -> active.addData(v.getKeyAsString(), v.getDocCount()));
//		return active;
//	}

}
