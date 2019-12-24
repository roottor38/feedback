package feedback.model.dto;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import lombok.Data;

@Data
@SuppressWarnings("unchecked")
public class KeywordDTO {
	private HashMap<String, Integer> data = new HashMap<>();
	
	public void addData(String label, String value) {
		// map 형태로 data를 받아준다
		// key가 있다면 -> count된 값들을 더 해 줌
		data.computeIfPresent(label, (k, v) -> Integer.parseInt(v.toString()) + Integer.parseInt(value));
		// key가 없다면 -> 새로 생성
		data.putIfAbsent(label, Integer.parseInt(value));
	}
	
	public JSONArray getArray() {
		JSONArray arr = new JSONArray(); // 리턴할 result
		JSONObject obj = new JSONObject(); // 임시 JSON object
		for(Entry<String, Integer> v : data.entrySet().stream() // 
				.sorted((v1, v2) -> v2.getValue().compareTo(v1.getValue())) // 카운트 된 값들 기준으로 역정렬
				.limit(5).collect(Collectors.toList())) { // 10개로 끊고 list화 시켜준 다음 for문 돌림
			obj.put("label", v.getKey()); // label 값에 key(키워드)
			obj.put("value", v.getValue()); // value 값에 카운트 된 값
			arr.add(obj.clone()); // 임시 object를 클론해서 array에 넣고
			obj.clear(); // 임시 object는 클리어
		}
		return arr;
	}

}
