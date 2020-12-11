package weather.dao;

import weather.entity.Weather;

public interface WeatherRepository {
	Weather decryptCondition(Weather weather) throws Exception;

}
