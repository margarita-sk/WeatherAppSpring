package outfit.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import exception.OutfitException;
import outfit.entity.Outfit;
import poolConnection.DatabaseConnectionPool;

public class OutfitRepositoryImpl implements OutfitRepository {
	private static final Logger log = Logger.getLogger(OutfitRepositoryImpl.class);

	private static final String GET_OUTFITS = "SELECT * FROM outfits";
	private static final String GET_OUTFIT_BY_ID = "SELECT clothing, temp_min, temp_max FROM outfits WHERE id = ?";
	private static final String ADD_OUTFIT = "INSERT INTO outfits (clothing, temp_max, temp_min) VALUES (?,?,?)";
	private static final String DELETE_OUTFIT = "DELETE FROM outfits WHERE id = ?";
	private static final String UPDATE_OUTFIT = "UPDATE outfits SET clothing = ?, temp_max = ?, temp_min = ? WHERE id = ?";

	@Override
	public Outfit recieveOufitById(int id) throws SQLException, OutfitException {
		var outfit = new Outfit();
		try (var connection = DatabaseConnectionPool.getConnection();
				var prStatement = connection.prepareStatement(GET_OUTFIT_BY_ID)) {
			prStatement.setInt(1, id);
			var resultSet = prStatement.executeQuery();
			if (resultSet.isClosed())
				throw new OutfitException();
			outfit = new Outfit(id, resultSet.getString(1), resultSet.getInt(2), resultSet.getInt(3));
		}
		return outfit;
	}

	@Override
	public Collection<Outfit> recieveAll() throws SQLException {
		ArrayList<Outfit> outfits = new ArrayList<>();
		try (var connection = DatabaseConnectionPool.getConnection(); var statement = connection.createStatement()) {
			var resultSet = statement.executeQuery(GET_OUTFITS);
			while (resultSet.next()) {
				Outfit outfit = new Outfit(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(4),
						resultSet.getInt(3));
				outfits.add(outfit);
			}
		}
		return outfits;
	}

	@Override
	public void addOutfit(Outfit outfit) throws SQLException, OutfitException {
		try (var connection = DatabaseConnectionPool.getConnection();
				var prStatement = connection.prepareStatement(ADD_OUTFIT)) {
			prStatement.setString(1, outfit.getOutfitName());
			prStatement.setInt(2, outfit.getMaxTemperatureToWear());
			prStatement.setInt(3, outfit.getMinTemperatureToWear());
			if (!prStatement.execute())
				throw new OutfitException();
		}
	}

	@Override
	public void deleteOutfit(int id) throws SQLException, OutfitException {
		try (var connection = DatabaseConnectionPool.getConnection();
				var prStatement = connection.prepareStatement(DELETE_OUTFIT)) {
			prStatement.setInt(1, id);
			Outfit outfit = recieveOufitById(id);
			if (prStatement.executeUpdate() == 1) {
				log.info("deleted outfit: " + outfit);
			} else
				throw new OutfitException();
		}
	}

	@Override
	public void editOutfit(Outfit outfit) throws SQLException, OutfitException {
		try (var connection = DatabaseConnectionPool.getConnection();
				var prStatement = connection.prepareStatement(UPDATE_OUTFIT)) {

			prStatement.setString(1, outfit.getOutfitName());
			prStatement.setInt(2, outfit.getMaxTemperatureToWear());
			prStatement.setInt(3, outfit.getMinTemperatureToWear());
			prStatement.setInt(4, outfit.getId());

			if (prStatement.executeUpdate() == 1) {
				log.info("the outfit was changed: " + outfit);
			} else
				throw new OutfitException();
		}
	}

}
