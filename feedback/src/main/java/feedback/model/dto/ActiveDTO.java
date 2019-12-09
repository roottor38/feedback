package feedback.model.dto;

import org.json.simple.JSONArray;

import lombok.Data;

@Data
@SuppressWarnings("unchecked")
public class ActiveDTO {
	JSONArray array = new JSONArray();
	
	public void addData(String date, long value) {
		JSONArray row = new JSONArray();
		row.add(date);
		row.add(value);
		array.add(row);
	}
}
