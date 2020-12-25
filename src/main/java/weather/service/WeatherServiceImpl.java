package weather.service;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weather.client.WeatherClient;
import weather.dao.WeatherRepository;
import weather.model.Weather;

@Service
public class WeatherServiceImpl implements WeatherService {

	private WeatherClient client;
	private WeatherRepository repository;

	@Autowired
	public WeatherServiceImpl(WeatherClient client, WeatherRepository repository) {
		this.client = client;
		this.repository = repository;
	}

	@Override
	public Weather recieveWeather(String latitude, String longitude)
			throws IOException, SQLException, InterruptedException {
		var weather = client.recieveWeather(latitude, longitude);
		return repository.decryptCondition(weather);
	}

}
