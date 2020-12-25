package weather.service;

import java.io.IOException;
import java.sql.SQLException;

import weather.model.Weather;

public interface WeatherService {

	Weather recieveWeather(String latutude, String longitude) throws IOException, SQLException, InterruptedException;

}
