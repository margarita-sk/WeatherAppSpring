package weather.entity;

import lombok.Data;

/**
 * The {@code Weather} class describes the weather with the temperature, icon,
 * condition, the type of precipitation and day time fields.
 * 
 * @author Margarita Skokova
 */

@Data
public class Weather {

	private long temperature;
	private String urlIcon;
	private String condition;
	private String precipitationType;
	private String daytime;

}
