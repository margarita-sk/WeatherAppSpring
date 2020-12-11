package outfit.dao;

import java.util.Collection;

import outfit.entity.Outfit;

public interface OutfitRepository {

	Outfit recieveOufitById(int id) throws Exception;

	Collection<Outfit> recieveAll() throws Exception;

	void addOutfit(Outfit outfit) throws Exception;

	void deleteOutfit(int id) throws Exception;

	void editOutfit(Outfit outfit) throws Exception;

}
