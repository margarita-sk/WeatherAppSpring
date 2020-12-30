package weather.exception;

import lombok.Getter;

@Getter
public class WeatherNotFoundException extends Exception {
	private String message = "Weather wasn't found";
}
