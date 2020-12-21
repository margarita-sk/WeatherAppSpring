package weather.client;

import java.io.IOException;

import weather.model.Weather;

public interface WeatherClient {

	Weather recieveWeather(String latitude, String longitude) throws IOException;

}
