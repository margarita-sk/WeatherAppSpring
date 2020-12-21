package weather.dao;

import java.sql.SQLException;

import weather.model.Weather;

public interface WeatherRepository {
	Weather decryptCondition(Weather weather) throws SQLException ;

}
