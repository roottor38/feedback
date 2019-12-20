package feedback.model.dto;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import lombok.Data;

@Data
@SuppressWarnings("unchecked")
public class RiskDTO {
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
	
	public JSONArray getAll() {
		JSONArray result = new JSONArray();
		result.add(dateArray);
		result.add(posArray);
		result.add(negArray);
		return result;	
	}
	
	public JSONArray getWeek() {
		JSONArray result = new JSONArray();
		int len = dateArray.size();
		if(len >= 8) {
			result.add(dateArray.subList(len - 8, len - 1));
			result.add(posArray.subList(len - 8, len - 1));
			result.add(negArray.subList(len - 8, len - 1));			
		} else {
			result.add(dateArray.subList(0, len - 1));
			result.add(posArray.subList(0, len - 1));
			result.add(negArray.subList(0, len - 1));
		}
		return result;
	}
}
