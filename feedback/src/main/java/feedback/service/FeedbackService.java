package feedback.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feedback.model.util.ApiTool;

@Service
public class FeedbackService {

	@Autowired
	ApiTool apiTool;

	public ArrayList<JSONObject> topWordCount() throws FileNotFoundException, IOException, ParseException {
		ArrayList<JSONObject> wordCount = new ArrayList<>();
		for(int i = 0; i < 5; i++) {
			wordCount.add((JSONObject) apiTool.getWordCount().get(i));
		}
		return wordCount;

	}
}
