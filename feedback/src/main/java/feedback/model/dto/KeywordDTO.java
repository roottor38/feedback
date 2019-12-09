package feedback.model.dto;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import lombok.Data;

@Data
@SuppressWarnings("unchecked")
public class KeywordDTO {
	JSONArray array = new JSONArray();
		
	public void addData(String label, String value) {
		JSONObject object = new JSONObject();
		object.put("label", label);
		object.put("value", value);
		array.add(object);
	}
}
