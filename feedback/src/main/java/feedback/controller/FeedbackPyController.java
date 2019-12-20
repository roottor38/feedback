package feedback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import feedback.service.FeedbackPyService;

// 이후 Linux 환경에서의 Flask로 대체 예정
@CrossOrigin(origins = { "http://127.0.0.1:8000", "http://localhost:8000", "http://127.0.0.1:5500"})
@RestController
public class FeedbackPyController {
	@Autowired
	FeedbackPyService pyService;
	
	@GetMapping("/scraping")
	public String scraping() {
		System.out.println("스크레이핑 호출 받았음돠");
//		pyService.runtime("scraping"); <= Flask로 대체
		return "스크레이핑 응답합니돠";
	}
	
	@GetMapping("/wordcount")
	public String wordcount() {
		System.out.println("워드카운트 호출 받았음돠");
//		pyService.runtime("wordcount"); <= Flask로 대체
		return "워드카운트 응답합니돠";
	}
}
