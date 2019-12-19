package feedback.model.dto;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import lombok.Data;

@Data
@SuppressWarnings("unchecked")
public class UserResDTO {
	JSONArray dateArray = new JSONArray();
	JSONArray posArray = new JSONArray();
	JSONArray negArray = new JSONArray();
	
	public void addData(String date, double positive, double negative) {
		JSONObject day = new JSONObject();
		JSONObject pos = new JSONObject();
		JSONObject neg = new JSONObject();
		
		day.put("label", date);
		pos.put("value", positive);
		neg.put("value", negative);
		
		dateArray.add(day);
		posArray.add(pos);
		negArray.add(neg);
	}
	
}
