package feedback.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FeedbackPageController {
	@RequestMapping("/")
	public String index() {
		return "index.html";
	   }
	
	@RequestMapping("/keywordChart")
	public String keywordChart() {
		return "keywordChart.html";
	}
	
	@RequestMapping("/activeChart")
	public String activeChart() {
		return "activeChart.html";
	}
	
}
