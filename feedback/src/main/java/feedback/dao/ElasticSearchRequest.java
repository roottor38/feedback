package feedback.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

@Component
public class ElasticSearchRequest {
	
	public SearchRequest searchTextReq(LocalDate start, LocalDate end, String community) {
		SearchSourceBuilder srcBuilder = new SearchSourceBuilder();
		SearchRequest request = new SearchRequest("community_data").source(srcBuilder);
		
		srcBuilder.size(1);
		srcBuilder.query(QueryBuilders.matchQuery(community, "리니지M 인벤"));
		srcBuilder.query(QueryBuilders.rangeQuery("date").gte(start).lte(end));
		srcBuilder.sort("recommand", SortOrder.DESC);
		srcBuilder.sort("commments", SortOrder.DESC);
		srcBuilder.sort("hits", SortOrder.DESC);
		
		return request;
	}
	
	public SearchRequest searchRiskReq(LocalDate start, LocalDate end, String field, String histName) {
		SearchSourceBuilder srcBuilder = new SearchSourceBuilder();
		SearchRequest request = new SearchRequest("community_data");
		
		srcBuilder.query(QueryBuilders.rangeQuery("date").gte(start).lte(end));
		AggregationBuilder aggBuilder =AggregationBuilders.dateHistogram(histName)
						.field("date").dateHistogramInterval(DateHistogramInterval.days(1))
						.subAggregation(AggregationBuilders.sum(field).field(field));
		
		srcBuilder.aggregation(aggBuilder).size(0);
		request.source(srcBuilder);
		
		return request;
	}
	
	public SearchRequest searchKeywordReq(LocalDate start, LocalDate end) {
		SearchSourceBuilder srcBuilder = new SearchSourceBuilder();
		SearchRequest request = new SearchRequest("community_data_analysis");
		ArrayList<LocalDate> dateArray = new ArrayList<>();
		
		for (LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
			dateArray.add(date);
	    }
		
		srcBuilder.query(QueryBuilders.termsQuery("_id", dateArray));
		request.source(srcBuilder);
		
		return request;
	}
}
