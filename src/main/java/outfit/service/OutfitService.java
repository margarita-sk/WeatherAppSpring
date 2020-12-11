package outfit.service;

import java.util.Collection;

import city.entity.City;
import outfit.dto.OutfitWithWeatherFacade;
import outfit.entity.Outfit;
import weather.entity.Weather;

public interface OutfitService {

	Outfit recieveOutfitByWeather(Weather weather) throws Exception;

	Outfit recieveOufitById(int id) throws Exception;

	Collection<Outfit> recieveAll() throws Exception;

	void addOutfit(Outfit outfit) throws Exception;

	void deleteOutfit(int id) throws Exception;

	void editOutfit(Outfit outfit) throws Exception;

	OutfitWithWeatherFacade buildFacade(City city, Weather weather, Outfit outfit) throws Exception;

}
