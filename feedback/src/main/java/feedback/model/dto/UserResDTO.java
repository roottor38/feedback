package feedback.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResDTO {

	private String date;
	private double pos;
	private double nev;
	
	public UserResDTO(String date) {
		this.date = date;
		
	}
	

}
