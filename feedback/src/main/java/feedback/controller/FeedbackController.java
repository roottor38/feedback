package feedback.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedbackController {
	
	@PostMapping("/test")
	public String test() {
		System.out.println("// accountList");
		System.out.println("testController");
		
		return "test";
	}

}
