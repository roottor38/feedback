package feedback.service;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import feedback.model.dto.ActiveDTO;
import feedback.model.dto.KeywordDTO;

@Service
public class FeedbackDataService {

	public JSONArray getKeyword() throws IOException {
		HighLevelClient highLevelClient = new HighLevelClient();
		JSONArray result = highLevelClient.searchKwd().getArray();
		highLevelClient.close();
		return result;
	}

	public JSONArray getActive() throws IOException {
		HighLevelClient highLevelClient = new HighLevelClient();
		JSONArray result = highLevelClient.searchActive().getArray();
		highLevelClient.close();
		return result;
	}

}

class HighLevelClient {
	private RestHighLevelClient client = new RestHighLevelClient(
			RestClient.builder(new HttpHost("192.168.1.3", 9200, "http"), new HttpHost("192.168.1.3", 9201, "http")));

	public void close() throws IOException {
		client.close();
	}

	public KeywordDTO searchKwd() throws IOException {
		SearchRequest searchRequest = new SearchRequest("community_data_analysis");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		KeywordDTO keyword = new KeywordDTO();
		searchResponse.getHits().getHits()[1].getSourceAsMap().entrySet().parallelStream()
				.sorted((a, b) -> Integer.parseInt(b.getValue().toString()) - Integer.parseInt(a.getValue().toString()))
				.limit(10).forEach(v -> keyword.addData(v.getKey().toString(), v.getValue().toString()));
		return keyword;
	}

	public ActiveDTO searchActive() throws IOException {
		String index = "community_data";
		String agg = "date_his";
		SearchRequest searchRequest = new SearchRequest(index);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.size(0);
		AggregationBuilder aggregationBuilder = AggregationBuilders.dateHistogram(agg).field("date").dateHistogramInterval(DateHistogramInterval.days(1));				
		searchSourceBuilder.aggregation(aggregationBuilder);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		Aggregations aggregations = searchResponse.getAggregations();
		ParsedDateHistogram byDate = aggregations.get("date_his");
		ActiveDTO active = new ActiveDTO();
		byDate.getBuckets().forEach(v -> active.addData(v.getKeyAsString(), v.getDocCount()));
		return active;
	}

}