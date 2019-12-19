package feedback.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class FeedbackPageController {
	@RequestMapping("/")
	public RedirectView dashboard() {
		return new RedirectView("architectui-html-free/index.html");
	}
	
//	@RequestMapping("/test")
//	public String textIndex() {
//		return "index.html";
//	   }

	@RequestMapping("/keywordChart")
	public String keywordChart() {
		return "keywordChart.html";
	}

	@RequestMapping("/activeChart")
	public String activeChart() {
		return "activeChart.html";
	}

}
