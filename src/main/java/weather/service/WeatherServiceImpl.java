package weather.service;

import weather.client.WeatherClient;
import weather.dao.WeatherRepository;
import weather.entity.Weather;

public class WeatherServiceImpl implements WeatherService {

	private WeatherClient client;
	private WeatherRepository repository;

	public WeatherServiceImpl(WeatherClient client, WeatherRepository repository) {
		this.client = client;
		this.repository = repository;
	}

	@Override
	public Weather recieveWeather(String latitude, String longitude) throws Exception {
		var weather = client.recieveWeather(latitude, longitude);
		return repository.decryptCondition(weather);
	}

}
