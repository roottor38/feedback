package feedback.controller;

<<<<<<< HEAD
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
=======
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
>>>>>>> 1527872ed396109e43022de696a1cf557ec392eb
public class FeedbackController {
	@Autowired
	FeedbackService service;
	
<<<<<<< HEAD
	@GetMapping("/test")
	public ArrayList<JSONObject> test() throws FileNotFoundException, IOException, ParseException {
		return service.topWordCount();
=======
	@PostMapping("/test")
//	@GetMapping("/test")
	public String test() {
		System.out.println("// accountList");
		System.out.println("testController");
		
		return "test";
>>>>>>> 1527872ed396109e43022de696a1cf557ec392eb
	}
	
	@GetMapping("/key")
	public String index() {
	    String page = "keywordChart.html";
		return page;
	}
	
}
