package weather.model;

import lombok.Data;

@Data
public class Weather {

	private int temperature;
	private String condition;
	private String iconUrl;
	private String daytime;

}
