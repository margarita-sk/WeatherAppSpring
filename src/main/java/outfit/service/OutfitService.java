package outfit.service;

import java.util.Collection;
import outfit.exception.OutfitDatabaseChangesException;
import outfit.exception.OutfitNotFoundException;
import outfit.model.Outfit;
import weather.model.Weather;

public interface OutfitService {

  Outfit recieveOutfitByWeather(Weather weather) throws OutfitNotFoundException;

  Outfit recieveOufitById(int id) throws OutfitNotFoundException;

  Collection<Outfit> recieveAll() throws OutfitNotFoundException;

  void addOutfit(Outfit outfit) throws OutfitDatabaseChangesException;

  void deleteOutfit(int id) throws OutfitDatabaseChangesException, OutfitNotFoundException;

  void editOutfit(Outfit outfit) throws OutfitDatabaseChangesException;

}
