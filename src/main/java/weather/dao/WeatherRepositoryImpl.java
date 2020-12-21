package weather.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import core.db.DatabaseConnectionPool;
import properties.PropertiesHolder;
import properties.PropertiesHolder.Key;
import weather.model.Weather;

public class WeatherRepositoryImpl implements WeatherRepository {
	public static final String lang = PropertiesHolder.recieveConfigDB(Key.DB_LANG);
	private static final String DECRYPT_CONDITION = "SELECT " + lang
			+ "_decryption FROM weather_conditions WHERE symbol = ?";

	@Override
	public Weather decryptCondition(Weather weather) throws SQLException {
		try (Connection connection = DatabaseConnectionPool.getConnection();
				PreparedStatement prStatement = connection.prepareStatement(DECRYPT_CONDITION)) {
			prStatement.setString(1, weather.getCondition());
			ResultSet result = prStatement.executeQuery();
			weather.setCondition(result.getString(1));
		}
		return weather;
	}

}
