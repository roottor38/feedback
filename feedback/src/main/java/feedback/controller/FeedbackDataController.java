package feedback.controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import feedback.service.FeedbackDataService;

// Rest API의 URI 규약에 맞춰서 자원 명사화
@CrossOrigin(origins = { "http://127.0.0.1:8000", "http://localhost:8000" })
@RestController
public class FeedbackDataController {
	@Autowired
	FeedbackDataService dataService;

	@PostMapping("/keyword")
	public JSONArray getKeyword(@RequestBody JSONObject body) throws IOException, ParseException {
		return dataService.getKeyword(LocalDate.parse(body.get("start").toString()), LocalDate.parse(body.get("end").toString()));
	}
	
	@GetMapping("/vital")
	public JSONArray getVital() throws IOException {
		return dataService.getVital();
	}
	
	@GetMapping("/risk")
	public JSONArray getRisk() throws IOException {
		System.out.println("risk run **********************************************");
		return dataService.getRisk();
	}

}
