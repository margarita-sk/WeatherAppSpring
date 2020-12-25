package outfit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import core.db.ConnectionPool;
import lombok.extern.log4j.Log4j;
import outfit.exception.OutfitDatabaseChangesException;
import outfit.exception.OutfitNotFoundException;
import outfit.model.Outfit;

@Repository
@Log4j
public class OutfitRepositoryImpl implements OutfitRepository {

	private ConnectionPool connectionPool;

	public OutfitRepositoryImpl(@Qualifier("myConnectionPool") ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	private static final String GET_OUTFITS = "SELECT * FROM outfits";
	private static final String GET_OUTFIT_BY_ID = "SELECT * FROM outfits WHERE id = ?";
	private static final String ADD_OUTFIT = "INSERT INTO outfits (clothing, temp_max, temp_min) VALUES (?,?,?)";
	private static final String DELETE_OUTFIT = "DELETE FROM outfits WHERE id = ?";
	private static final String UPDATE_OUTFIT = "UPDATE outfits SET clothing = ?, temp_max = ?, temp_min = ? WHERE id = ?";

	private static final int OUTFIT_ID_INDEX = 1;
	private static final int OUTFIT_NAME_INDEX = 2;
	private static final int OUTFIT_TEMP_MAX_INDEX = 3;
	private static final int OUTFIT_TEMP_MIN_INDEX = 4;

	@Override
	public Outfit recieveOufitById(int id) throws SQLException, OutfitNotFoundException, InterruptedException {
		var outfit = new Outfit();
		try (var connection = connectionPool.receiveConnection();
				var prStatement = connection.prepareStatement(GET_OUTFIT_BY_ID)) {
			prStatement.setInt(OUTFIT_ID_INDEX, id);
			outfit = convertResultToOutfit(prStatement.executeQuery());
		}
		return outfit;
	}

	@Override
	public Collection<Outfit> recieveAll() throws SQLException, OutfitNotFoundException, InterruptedException {
		ArrayList<Outfit> outfits = new ArrayList<>();
		try (var connection = connectionPool.receiveConnection(); var statement = connection.createStatement()) {
			var resultSet = statement.executeQuery(GET_OUTFITS);
			while (resultSet.next()) {
				outfits.add(convertResultToOutfit(resultSet));
			}
		}
		return outfits;
	}

	@Override
	public void addOutfit(Outfit outfit) throws SQLException, OutfitDatabaseChangesException, InterruptedException {
		try (var connection = connectionPool.receiveConnection();
				var prStatement = connection.prepareStatement(ADD_OUTFIT)) {
			prStatement.setString(1, outfit.getOutfitName());
			prStatement.setInt(2, outfit.getMaxTemperatureToWear());
			prStatement.setInt(3, outfit.getMinTemperatureToWear());
			if (!prStatement.execute())
				throw new OutfitDatabaseChangesException("The outfit was not added");
		}
	}

	@Override
	public void deleteOutfit(int id)
			throws SQLException, OutfitDatabaseChangesException, OutfitNotFoundException, InterruptedException {
		try (var connection = connectionPool.receiveConnection();
				var prStatement = connection.prepareStatement(DELETE_OUTFIT)) {
			prStatement.setInt(1, id);
			Outfit outfit = recieveOufitById(id);
			if (prStatement.executeUpdate() == 1) {
				log.info("deleted outfit: " + outfit);
			} else
				throw new OutfitDatabaseChangesException("The outfit was not deleted");
		}
	}

	@Override
	public void editOutfit(Outfit outfit) throws SQLException, OutfitDatabaseChangesException, InterruptedException {
		try (var connection = connectionPool.receiveConnection();
				var prStatement = connection.prepareStatement(UPDATE_OUTFIT)) {

			prStatement.setString(1, outfit.getOutfitName());
			prStatement.setInt(2, outfit.getMaxTemperatureToWear());
			prStatement.setInt(3, outfit.getMinTemperatureToWear());
			prStatement.setInt(4, outfit.getId());

			if (prStatement.executeUpdate() == 1) {
				log.info("the outfit was changed: " + outfit);
			} else
				throw new OutfitDatabaseChangesException("The outfit was failed to edit");
		}
	}

	private Outfit convertResultToOutfit(ResultSet result) throws SQLException, OutfitNotFoundException {
		if (result.isClosed())
			throw new OutfitNotFoundException();

		return new Outfit(result.getInt(OUTFIT_ID_INDEX), result.getString(OUTFIT_NAME_INDEX),
				result.getInt(OUTFIT_TEMP_MIN_INDEX), result.getInt(OUTFIT_TEMP_MAX_INDEX));
	}

}
