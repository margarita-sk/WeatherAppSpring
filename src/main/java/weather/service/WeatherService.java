package weather.service;

import weather.entity.Weather;

public interface WeatherService {

	Weather recieveWeather(String latutude, String longitude) throws Exception;

}
