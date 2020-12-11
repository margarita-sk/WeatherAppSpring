package outfit.service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import city.entity.City;
import exception.OutfitException;
import outfit.dao.OutfitRepository;
import outfit.dto.OutfitWithWeatherFacade;
import outfit.entity.Outfit;
import weather.entity.Weather;

public class OutfitServiceImpl implements OutfitService {

	private OutfitRepository repository;

	public OutfitServiceImpl(OutfitRepository repository) {
		this.repository = repository;
	}

	@Override
	public Outfit recieveOutfitByWeather(Weather weather) throws Exception {
		Stream<Outfit> outfitsStream = repository.recieveAll().stream();
		Optional<Outfit> matchedOutfit = outfitsStream
				.filter(outfit -> (outfit.getMinTemperatureToWear() <= weather.getTemperature()
						&& outfit.getMaxTemperatureToWear() >= weather.getTemperature()))
				.findFirst();

		Outfit outfit = matchedOutfit.orElseThrow(OutfitException::new);
		return addAccessoriesToOutfit(outfit, weather);
	}

	private Outfit addAccessoriesToOutfit(Outfit outfit, Weather weather) {
		switch (weather.getCondition()) {
		case "clear":
			if ("d".equals(weather.getDaytime()))
				outfit.setAccessories("sunglasses");
			break;
		case "thunderstorm with hail":
		case "cloudy":
		case "light snow":
		case "overcast":
		case "partly cloudy":
			outfit.setAccessories("good mood");
			break;
		default:
			outfit.setAccessories("umbrella");
			break;
		}
		return outfit;
	}

	@Override
	public Outfit recieveOufitById(int id) throws Exception {
		return repository.recieveOufitById(id);
	}

	@Override
	public Collection<Outfit> recieveAll() throws Exception {
		return repository.recieveAll();
	}

	@Override
	public void addOutfit(Outfit outfit) throws Exception {
		repository.addOutfit(outfit);
	}

	@Override
	public void deleteOutfit(int id) throws Exception {
		repository.deleteOutfit(id);

	}

	@Override
	public void editOutfit(Outfit outfit) throws Exception {
		repository.editOutfit(outfit);

	}

	@Override
	public OutfitWithWeatherFacade buildFacade(City city, Weather weather, Outfit outfit) throws Exception {
		var facade = new OutfitWithWeatherFacade(city.getActualCityName(), weather.getTemperature(),
				weather.getCondition(), weather.getIconUrl(), outfit.getOutfitName(), outfit.getAccessories());
		return facade;
	}

}
