package weather.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import core.db.ConnectionPool;
import weather.model.Weather;

@Repository
public class WeatherRepositoryImpl implements WeatherRepository {
	private ConnectionPool connectionPool;
	private static final String DECRYPT_CONDITION = "SELECT "
			+ "en_decryption FROM weather_conditions WHERE symbol = ?";

	@Autowired
	public WeatherRepositoryImpl(@Qualifier("myConnectionPool") ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	@Override
	public Weather decryptCondition(Weather weather) throws SQLException, InterruptedException {
		try (Connection connection = connectionPool.receiveConnection();
				PreparedStatement prStatement = connection.prepareStatement(DECRYPT_CONDITION)) {
			prStatement.setString(1, weather.getCondition());
			ResultSet result = prStatement.executeQuery();
			weather.setCondition(result.getString(1));
		}
		return weather;
	}

}
