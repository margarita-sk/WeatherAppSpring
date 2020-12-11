package weather.client;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import weather.entity.Weather;

public class FileWeatherClient implements WeatherClient {

	private static String PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();

	@Override
	public Weather recieveWeather(String latitude, String longitude)
			throws JsonParseException, JsonMappingException, IOException {
		var mapper = new ObjectMapper();
		var weather = mapper.readValue(new File(PATH + "weather.json"), Weather.class);
		return weather;
	}

}
