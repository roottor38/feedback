package feedback.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import feedback.service.FeedbackService;

@RestController
public class FeedbackController {
	@Autowired
	FeedbackService service;
	
	@GetMapping("/test")
	public ArrayList<JSONObject> test() throws FileNotFoundException, IOException, ParseException {
		return service.topWordCount();
	}

}
