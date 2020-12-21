package weather.service;

import java.io.IOException;
import java.sql.SQLException;

import weather.client.WeatherClient;
import weather.dao.WeatherRepository;
import weather.model.Weather;

public class WeatherServiceImpl implements WeatherService {

	private WeatherClient client;
	private WeatherRepository repository;

	public WeatherServiceImpl(WeatherClient client, WeatherRepository repository) {
		this.client = client;
		this.repository = repository;
	}

	@Override
	public Weather recieveWeather(String latitude, String longitude) throws IOException, SQLException {
		var weather = client.recieveWeather(latitude, longitude);
		return repository.decryptCondition(weather);
	}

}
