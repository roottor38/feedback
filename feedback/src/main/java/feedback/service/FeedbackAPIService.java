package feedback.service;

import org.springframework.stereotype.Service;

@Service
public class FeedbackAPIService {
	
	public String testMethod() {
		System.out.println("test");
		return "testMethod";
	}

}
