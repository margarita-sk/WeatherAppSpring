package outfit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.stereotype.Repository;
import core.db.pool.ConnectionPool;
import lombok.extern.log4j.Log4j;
import outfit.exception.OutfitDatabaseChangesException;
import outfit.exception.OutfitNotFoundException;
import outfit.model.Outfit;

@Repository
@Log4j
public class OutfitRepositoryImpl implements OutfitRepository {

  private ConnectionPool connectionPool;

  public OutfitRepositoryImpl(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

  private static final String GET_OUTFITS = "SELECT * FROM outfits";
  private static final String GET_OUTFIT_BY_ID = "SELECT * FROM outfits WHERE id = ?";
  private static final String ADD_OUTFIT =
      "INSERT INTO outfits (clothing, temp_max, temp_min) VALUES (?,?,?)";
  private static final String DELETE_OUTFIT = "DELETE FROM outfits WHERE id = ?";
  private static final String UPDATE_OUTFIT =
      "UPDATE outfits SET clothing = ?, temp_max = ?, temp_min = ? WHERE id = ?";

  private static final int GET_OUTFIT_ID_INDEX = 1;
  private static final int GET_OUTFIT_NAME_INDEX = 2;
  private static final int GET_OUTFIT_TEMP_MAX_INDEX = 3;
  private static final int GET_OUTFIT_TEMP_MIN_INDEX = 4;

  private static final int UPDATE_OUTFIT_NAME_INDEX = 1;
  private static final int UPDATE_OUTFIT_TEMP_MAX_INDEX = 2;
  private static final int UPDATE_OUTFIT_TEMP_MIN_INDEX = 3;
  private static final int UPDATE_OUTFIT_ID_INDEX = 4;

  @Override
  public Outfit recieveOufitById(int id) throws OutfitNotFoundException {
    var outfit = new Outfit();
    try (var connection = connectionPool.receiveConnection();
        var prStatement = connection.getConnection().prepareStatement(GET_OUTFIT_BY_ID)) {
      prStatement.setInt(GET_OUTFIT_ID_INDEX, id);
      outfit = convertResultToOutfit(prStatement.executeQuery());
    } catch (SQLException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
    return outfit;
  }

  @Override
  public Collection<Outfit> recieveAll() throws OutfitNotFoundException {
    var outfits = new ArrayList<Outfit>();
    try (var connection = connectionPool.receiveConnection();
        var statement = connection.getConnection().createStatement()) {
      var resultSet = statement.executeQuery(GET_OUTFITS);
      while (resultSet.next()) {
        outfits.add(convertResultToOutfit(resultSet));
      }
    } catch (SQLException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
    return outfits;
  }

  @Override
  public void addOutfit(Outfit outfit) throws OutfitDatabaseChangesException {
    try (var connection = connectionPool.receiveConnection();
        var prStatement = connection.getConnection().prepareStatement(ADD_OUTFIT)) {
      prStatement.setString(UPDATE_OUTFIT_NAME_INDEX, outfit.getOutfitName());
      prStatement.setInt(UPDATE_OUTFIT_TEMP_MAX_INDEX, outfit.getMaxTemperatureToWear());
      prStatement.setInt(UPDATE_OUTFIT_TEMP_MIN_INDEX, outfit.getMinTemperatureToWear());
      if (!prStatement.execute())
        throw new OutfitDatabaseChangesException("The outfit was not added");
    } catch (SQLException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteOutfit(int id) throws OutfitDatabaseChangesException, OutfitNotFoundException {
    try (var connection = connectionPool.receiveConnection();
        var prStatement = connection.getConnection().prepareStatement(DELETE_OUTFIT)) {
      prStatement.setInt(1, id);
      var outfit = recieveOufitById(id);
      if (prStatement.executeUpdate() == 1) {
        log.info("deleted outfit: " + outfit);
      } else
        throw new OutfitDatabaseChangesException("The outfit was not deleted");
    } catch (SQLException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void editOutfit(Outfit outfit) throws OutfitDatabaseChangesException {
    try (var connection = connectionPool.receiveConnection();
        var prStatement = connection.getConnection().prepareStatement(UPDATE_OUTFIT)) {

      prStatement.setString(UPDATE_OUTFIT_NAME_INDEX, outfit.getOutfitName());
      prStatement.setInt(UPDATE_OUTFIT_TEMP_MAX_INDEX, outfit.getMaxTemperatureToWear());
      prStatement.setInt(UPDATE_OUTFIT_TEMP_MIN_INDEX, outfit.getMinTemperatureToWear());
      prStatement.setInt(UPDATE_OUTFIT_ID_INDEX, outfit.getId());

      if (prStatement.executeUpdate() == 1) {
        log.info("the outfit was changed: " + outfit);
      } else
        throw new OutfitDatabaseChangesException("The outfit was failed to edit");
    } catch (SQLException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
  }

  private Outfit convertResultToOutfit(ResultSet result)
      throws OutfitNotFoundException, SQLException {
    if (result.isClosed())
      throw new OutfitNotFoundException();

    return new Outfit(result.getInt(GET_OUTFIT_ID_INDEX), result.getString(GET_OUTFIT_NAME_INDEX),
        result.getInt(GET_OUTFIT_TEMP_MIN_INDEX), result.getInt(GET_OUTFIT_TEMP_MAX_INDEX));
  }

}
