package weather.dao;

import weather.model.Weather.Precipitation;

public interface WeatherRepository {
  String receiveCondition(String decryptedCondition);

  Precipitation receivePrecipitation(String decryptedCondition);

}
