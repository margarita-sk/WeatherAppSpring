package weather.service;

import city.entity.City;
import city.service.CityServiceImpl;
import weather.entity.Weather;

public class Main {

	public static void main(String[] args) {
		City city = new CityServiceImpl().parseCityByName("Minsk");
		Weather weather = new WeatherServiceImpl().getWeather(city);

	}

}
