package feedback.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import feedback.model.dto.RiskDTO;
import feedback.service.FeedbackDataService;

// Rest API의 URI 규약에 맞춰서 자원 명사화
@CrossOrigin(origins = { "http://127.0.0.1:8000", "http://localhost:8000" })
@RestController
public class FeedbackDataController {
	@Autowired
	FeedbackDataService dataService;

	@GetMapping("/keyword")
	public JSONArray getKeyword() throws IOException {
		return dataService.getKeyword();
	}
	
	@GetMapping("/vital")
	public JSONArray getVital() throws IOException {
		return dataService.getVital();
	}
	
	@GetMapping("/risk")
	public JSONArray getRisk() throws IOException {
		return dataService.getRisk();
	}

}
