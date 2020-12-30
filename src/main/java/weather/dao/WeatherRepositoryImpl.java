package weather.dao;

import java.sql.SQLException;
import core.db.pool.ConnectionPool;
import lombok.extern.log4j.Log4j;
import weather.model.Weather.Precipitation;

@Log4j
public class WeatherRepositoryImpl implements WeatherRepository {
  private ConnectionPool connectionPool;
  private String GET_CONDITION = "SELECT en_decryption FROM weather_conditions WHERE symbol = ?";
  private static final String GET_PRECIPITATION =
      "SELECT precipitation FROM weather_conditions WHERE symbol = ?";

  private static final int GET_CONDITION_INDEX = 1;
  private static final int GET_PRECIPITATION_INDEX = 1;

  public WeatherRepositoryImpl(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

  @Override
  public String receiveCondition(String decryptedCondition) {
    try (var connection = connectionPool.receiveConnection();
        var prStatement = connection.getConnection().prepareStatement(GET_CONDITION)) {
      prStatement.setString(1, decryptedCondition);
      var result = prStatement.executeQuery();
      return result.getString(GET_CONDITION_INDEX);
    } catch (SQLException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public Precipitation receivePrecipitation(String decryptedCondition) {
    try (var connection = connectionPool.receiveConnection();
        var prStatement = connection.getConnection().prepareStatement(GET_PRECIPITATION)) {
      prStatement.setString(1, decryptedCondition);
      var result = prStatement.executeQuery();
      return Precipitation.valueOf(result.getString(GET_PRECIPITATION_INDEX));
    } catch (SQLException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
  }

}
