package feedback.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
public class FeedbackController {
	
	@PostMapping("/test")
//	@GetMapping("/test")
	public String test() {
		System.out.println("// accountList");
		System.out.println("testController");
		
		return "test";
	}
	
	@GetMapping("/key")
	public String index() {
	    String page = "keywordChart.html";
		return page;
	}
	
}
