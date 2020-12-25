package outfit.facade;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import city.exception.CityIncorrectNameException;
import city.model.City;
import city.service.CityService;
import outfit.dto.OutfitWithWeatherDto;
import outfit.exception.OutfitNotFoundException;
import outfit.model.Outfit;
import outfit.service.OutfitService;
import weather.model.Weather;
import weather.service.WeatherService;

@Component
public class OutfitFacadeImpl implements OutfitFacade {

	private WeatherService weatherService;
	private CityService cityService;
	private OutfitService outfitService;

	@Autowired
	public OutfitFacadeImpl(WeatherService weatherService, CityService cityService, OutfitService outfitService) {
		this.weatherService = weatherService;
		this.cityService = cityService;
		this.outfitService = outfitService;
	}

	@Override
	public OutfitWithWeatherDto buildFacade(String citySearchedName)
			throws CityIncorrectNameException, IOException, SQLException, OutfitNotFoundException,
			InterruptedException {
		City city = cityService.recieveCity(citySearchedName);
		Weather weather = weatherService.recieveWeather(city.getLatitude(), city.getLongitude());
		Outfit outfit = outfitService.recieveOutfitByWeather(weather);

		return new OutfitWithWeatherDto(city.getActualCityName(), weather.getTemperature(), weather.getCondition(),
				weather.getIconUrl(), outfit.getOutfitName(), outfit.getAccessories());
	}

}
