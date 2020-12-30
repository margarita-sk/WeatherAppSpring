package weather.service;

import weather.exception.WeatherNotFoundException;
import weather.model.Weather;

public interface WeatherService {

  Weather recieveWeather(String latutude, String longitude) throws WeatherNotFoundException;

}
