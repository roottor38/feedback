package feedback.service;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feedback.dao.ElasticSearchDAO;

@Service
public class FeedbackDataService {
	
	@Autowired
	ElasticSearchDAO dao;

	public JSONArray getRisk() throws IOException {
		return dao.searchRisk().getWeek();
	}

	public JSONArray getKeyword() throws IOException {
		return dao.searchKeyword().getArray();
	}

	public JSONArray getVital() throws IOException {
		return dao.searchVital().getArray();
	}

}