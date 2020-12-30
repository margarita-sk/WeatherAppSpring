package weather.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weather.client.WeatherClient;
import weather.exception.WeatherNotFoundException;
import weather.model.Weather;

@Service
public class WeatherServiceImpl implements WeatherService {

  private WeatherClient client;

  @Autowired
  public WeatherServiceImpl(WeatherClient client) {
    this.client = client;
  }

  @Override
  public Weather recieveWeather(String latitude, String longitude) throws WeatherNotFoundException {
    return client.recieveWeather(latitude, longitude);
  }

}
