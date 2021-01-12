package weather.dto;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import city.entity.City;
import city.service.CityServiceImpl;
import dao.DatabaseConnection;
import dao.SQLRepositorium;
import outfit.entity.Outfit;
import weather.entity.Weather;
import weather.service.WeatherServiceImpl;

/**
 * The {@code InformationServiceImpl} is a class mediator between business logic
 * and a servlet. This method provides the information about the weather and the
 * outfit advice, according to the weather.
 * 
 * @author Margarita Skokova
 */
public class InformationServiceImpl implements InformationService {
	private static final Logger log = Logger.getLogger(InformationServiceImpl.class);

	@Override
	public InformationStorage getGeneralInformation(String cityName) {
		City city = new CityServiceImpl().parseCityByName(cityName);
		Weather weather = new WeatherServiceImpl().getWeather(city);

		if (weather == null || city == null) {
			log.warn("weather==null:" + weather == null);
			log.warn("city==null:" + city == null);
			log.warn("getGeneralInformation(String cityName) returned null");
			return null;
		}
		InformationStorage storage = new InformationStorage();
		ArrayList<Outfit> outfits = SQLRepositorium.getClothing();
		for (Outfit outfit : outfits) {
			if (weather.getTemperature() >= outfit.getMinTemperature()
					&& weather.getTemperature() <= outfit.getMaxTemperature()) {
				storage.setOutfit(outfit.getClothing());
			}
		}
		storage.setCondition(SQLRepositorium.getDecryptionCondition(weather.getCondition()));
		storage.setCityName(city.getName());
		storage.setUrlIconWeather(weather.getUrlIcon());
		storage.setTemperature(weather.getTemperature());

		if (weather.getCondition().equals("clear") && weather.getDaytime().equals("d")) {
			storage.setAccessories("sunglasses");
		} else if (Integer.parseInt(weather.getPrecipitationType()) > 0) {
			storage.setAccessories("umbrella");
		}
		return storage;
	}
}
