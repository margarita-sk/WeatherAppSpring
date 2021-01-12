package weather.dto;

import lombok.*;

/**
 * The {@code InformationStorage} stores information about the weather and the
 * outfit advice, this class is used for providing the information to servlets.
 * Other words this class is a mediator between business logic and a servlet.
 * 
 * @author Margarita Skokova
 */
@Data
public class InformationStorage {

	private long temperature;
	private String condition;
	private String urlIconWeather;
	private String cityName;

	private String outfit;
	private String accessories;

}
