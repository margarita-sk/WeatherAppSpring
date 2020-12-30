package weather.client;

import weather.exception.WeatherNotFoundException;
import weather.model.Weather;

public interface WeatherClient {

  Weather recieveWeather(String latitude, String longitude) throws WeatherNotFoundException;

}
