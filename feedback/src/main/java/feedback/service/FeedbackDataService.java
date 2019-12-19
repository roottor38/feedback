package feedback.service;

import java.io.IOException;
import java.util.ArrayList;

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
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feedback.dao.ElDao;
import feedback.model.dto.ActiveDTO;
import feedback.model.dto.KeywordDTO;
import feedback.model.dto.UserResDTO;

@Service
public class FeedbackDataService {
	
	@Autowired
	ElDao dao;

	public JSONArray getRes() throws IOException {
		ElDao highLevelClient = new ElDao();
		JSONArray result = highLevelClient.searchRes();
		highLevelClient.close();
		return result;
	}

	public JSONArray getKeyword() throws IOException {
		ElDao highLevelClient = new ElDao();
		JSONArray result = highLevelClient.searchKwd().getArray();
		highLevelClient.close();
		return result;
	}

	public JSONArray getActive() throws IOException {
		ElDao highLevelClient = new ElDao();
		JSONArray result = highLevelClient.searchActive().getArray();
		highLevelClient.close();
		return result;
	}

}