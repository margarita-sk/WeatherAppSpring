package outfit.facade;

import java.io.IOException;
import java.sql.SQLException;

import city.exception.CityIncorrectNameException;
import outfit.dto.OutfitWithWeatherDto;
import outfit.exception.OutfitNotFoundException;

public interface OutfitFacade {

	OutfitWithWeatherDto buildFacade(String searchedCityName)
			throws CityIncorrectNameException, IOException, SQLException, OutfitNotFoundException;

}
