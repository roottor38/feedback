package feedback.service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feedback.dao.ElasticSearchDAO;

@Service
public class FeedbackDataService {
	
	@Autowired
	ElasticSearchDAO dao;

	public JSONArray getRisk(LocalDate start, LocalDate end ) throws IOException {
		return dao.searchRisk(start, end).getWeek();
	}

	public JSONArray getKeyword(LocalDate start, LocalDate end) throws IOException, ParseException {
		return dao.searchKeyword(start, end).getArray();
	}

	public JSONArray getText(LocalDate start, LocalDate end, String community) throws IOException {
		return dao.searchText(start, end, community);
	}

//	public JSONArray getVital() throws IOException {
//		return dao.searchVital().getArray();
//	}
}