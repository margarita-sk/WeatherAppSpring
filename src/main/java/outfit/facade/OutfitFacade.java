package outfit.facade;

import city.exception.CityIncorrectNameException;
import outfit.dto.OutfitWithWeatherDto;
import outfit.exception.OutfitNotFoundException;
import weather.exception.WeatherNotFoundException;

public interface OutfitFacade {

  OutfitWithWeatherDto searchOutfitWithWeatherDto(String searchedCityName)
      throws CityIncorrectNameException, OutfitNotFoundException, WeatherNotFoundException;

}
