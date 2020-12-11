package weather.client;

import weather.entity.Weather;

public interface WeatherClient {

	Weather recieveWeather(String latitude, String longitude) throws Exception;

}
