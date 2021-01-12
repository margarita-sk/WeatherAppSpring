package weather.service;

import city.entity.City;
import weather.entity.Weather;

/**
 * The {@code WeatherService} interface provides the information about the
 * weather
 * 
 * @author Margarita Skokova
 */
public interface WeatherService {

	Weather getWeather(City city);

}
