package feedback.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FeedbackController {
	
	@PostMapping("/test")
	public String test() {
		System.out.println("// accountList");
		System.out.println("testController");
		
		return "test";
	}

}
