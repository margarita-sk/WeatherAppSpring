package outfit.dao;

import java.sql.SQLException;
import java.util.Collection;

import outfit.exception.OutfitDatabaseChangesException;
import outfit.exception.OutfitNotFoundException;
import outfit.model.Outfit;

public interface OutfitRepository {

	Outfit recieveOufitById(int id) throws SQLException, OutfitNotFoundException, InterruptedException;

	Collection<Outfit> recieveAll() throws SQLException, OutfitNotFoundException, InterruptedException;

	void addOutfit(Outfit outfit) throws SQLException, OutfitDatabaseChangesException, InterruptedException;

	void deleteOutfit(int id)
			throws SQLException, OutfitDatabaseChangesException, OutfitNotFoundException, InterruptedException;

	void editOutfit(Outfit outfit) throws SQLException, OutfitDatabaseChangesException, InterruptedException;

}
