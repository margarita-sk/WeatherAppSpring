package outfit.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import city.exception.CityIncorrectNameException;
import city.service.CityService;
import outfit.dto.OutfitWithWeatherDto;
import outfit.exception.OutfitNotFoundException;
import outfit.service.OutfitService;
import weather.exception.WeatherNotFoundException;
import weather.service.WeatherService;

@Component
public class OutfitFacadeImpl implements OutfitFacade {

  private WeatherService weatherService;
  private CityService cityService;
  private OutfitService outfitService;

  @Autowired
  public OutfitFacadeImpl(WeatherService weatherService, CityService cityService,
      OutfitService outfitService) {
    this.weatherService = weatherService;
    this.cityService = cityService;
    this.outfitService = outfitService;
  }

  @Override
  public OutfitWithWeatherDto searchOutfitWithWeatherDto(String searchedCityName)
      throws CityIncorrectNameException, OutfitNotFoundException, WeatherNotFoundException {
    var city = cityService.recieveCity(searchedCityName);
    var weather = weatherService.recieveWeather(city.getLatitude(), city.getLongitude());
    var outfit = outfitService.recieveOutfitByWeather(weather);

    return new OutfitWithWeatherDto(city.getCityName(), weather.getTemperature(),
        weather.getCondition(), weather.getIconUrl(), outfit.getOutfitName(),
        outfit.getAccessories().getName());
  }

}
