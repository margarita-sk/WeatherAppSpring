package outfit.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import city.model.City;
import outfit.dao.OutfitRepository;
import outfit.exception.OutfitDatabaseChangesException;
import outfit.exception.OutfitNotFoundException;
import outfit.model.Outfit;
import weather.model.Weather;

public class OutfitServiceImpl implements OutfitService {

	private OutfitRepository repository;

	public OutfitServiceImpl(OutfitRepository repository) {
		this.repository = repository;
	}

	@Override
	public Outfit recieveOutfitByWeather(Weather weather) throws OutfitNotFoundException, SQLException {
		Stream<Outfit> outfitsStream = repository.recieveAll().stream();
		Optional<Outfit> matchedOutfit = outfitsStream
				.filter(outfit -> (outfit.getMinTemperatureToWear() <= weather.getTemperature()
						&& outfit.getMaxTemperatureToWear() >= weather.getTemperature()))
				.findFirst();

		Outfit outfit = matchedOutfit.orElseThrow(OutfitNotFoundException::new);
		return addAccessoriesToOutfit(outfit, weather);
	}

	private Outfit addAccessoriesToOutfit(Outfit outfit, Weather weather) {
		switch (weather.getCondition()) {
		case "cloudy", "light snow", "overcast", "partly cloudy" -> outfit.setAccessories("a good mood");
		case "clear" -> {
			if (weather.getDaytime().equals("d"))
				outfit.setAccessories("sunglasses");
		}
		default -> outfit.setAccessories("an umbrella");
		}
		;
		return outfit;
	}

	@Override
	public Outfit recieveOufitById(int id) throws SQLException, OutfitNotFoundException {
		return repository.recieveOufitById(id);
	}

	@Override
	public Collection<Outfit> recieveAll() throws SQLException, OutfitNotFoundException {
		return repository.recieveAll();
	}

	@Override
	public void addOutfit(Outfit outfit) throws SQLException, OutfitDatabaseChangesException {
		repository.addOutfit(outfit);
	}

	@Override
	public void deleteOutfit(int id) throws SQLException, OutfitDatabaseChangesException, OutfitNotFoundException {
		repository.deleteOutfit(id);

	}

	@Override
	public void editOutfit(Outfit outfit) throws SQLException, OutfitDatabaseChangesException {
		repository.editOutfit(outfit);

	}

}
