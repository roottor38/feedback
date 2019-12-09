package feedback.controller;

import java.io.IOException;
import java.util.Date;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import feedback.service.FeedbackDataService;

@CrossOrigin(origins = { "http://127.0.0.1:8000", "http://localhost:8000" })
@RestController
public class FeedbackDataController {
	@Autowired
	FeedbackDataService dataService;
	
	@GetMapping("/getKeyword")
	public JSONArray getKeyword() throws IOException {
		return dataService.getKeyword();
	}
	
	@GetMapping("/getActive")
	public JSONArray getActive() throws IOException {
		return dataService.getActive();
	}
}
