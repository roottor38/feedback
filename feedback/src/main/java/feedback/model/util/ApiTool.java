package feedback.model.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiTool {
//	@Value("${api.ip}")
//	private String IP;
//	@Value("${api.port}")
//	private String PORT;
//	@Value("${api.name}")
//	private String NAME;

//	public JSONObject callApi(String cat, String name, String method, Map<String, Object> dataMap) throws RestClientException, ParseException {
//		System.out.println("** 분류 : " + cat + " / API 기능명 : " + name);
//		String uri = makeURI(cat, name, dataMap);
//		System.out.println("** URI : " + uri);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<JSONObject> request = new HttpEntity<>(new JSONObject(dataMap), headers);
//		ResponseEntity<JSONObject> response = (new RestTemplate()).exchange(
//				URI.create(uri), method.equals("post") ? HttpMethod.POST : HttpMethod.GET, request, JSONObject.class);
//		return response.getBody();
//	}
//	
//	public String makeURI(String cat, String name, Map<String, Object> dataMap) throws ParseException {
//		System.out.println("** 요청 데이터 : "+ new JSONObject(dataMap));
//		return new StringBuilder()
//				.append(IP).append(":")
//				.append(((JSONObject) (new JSONParser().parse(PORT))).get(cat).toString()).append("/")
//				.append(((JSONObject) (new JSONParser().parse(NAME))).get(name).toString())
//				.toString();
//	}
	
	private String path = System.getProperty("user.dir");
	
	public JSONArray getWordCount() throws FileNotFoundException, IOException, ParseException {
		return (JSONArray) new JSONParser().parse(new FileReader(path + "\\test.json"));

	}
}
