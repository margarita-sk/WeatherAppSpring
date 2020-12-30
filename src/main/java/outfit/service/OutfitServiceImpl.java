package outfit.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import outfit.dao.OutfitRepository;
import outfit.exception.OutfitDatabaseChangesException;
import outfit.exception.OutfitNotFoundException;
import outfit.model.Outfit;
import outfit.model.Outfit.Accessories;
import weather.model.Weather;
import weather.model.Weather.Daytime;

@Service
public class OutfitServiceImpl implements OutfitService {

  private OutfitRepository repository;

  @Autowired
  public OutfitServiceImpl(OutfitRepository repository) {
    this.repository = repository;
  }

  @Override
  public Outfit recieveOutfitByWeather(Weather weather) throws OutfitNotFoundException {
    var outfitsStream = repository.recieveAll().stream();
    var matchedOutfit =
        outfitsStream.filter(outfit -> (outfit.getMinTemperatureToWear() <= weather.getTemperature()
            && outfit.getMaxTemperatureToWear() >= weather.getTemperature())).findFirst();

    var outfit = matchedOutfit.orElseThrow(OutfitNotFoundException::new);
    return addAccessoriesToOutfit(outfit, weather);
  }

  private Outfit addAccessoriesToOutfit(Outfit outfit, Weather weather) {
    switch (weather.getPrecipitation()) {
      case PRECIPITATION -> outfit.setAccessories(Accessories.UMBRELLA);
      case CLEAR -> {
        if (Daytime.DAY.equals(weather.getDaytime()))
          outfit.setAccessories(Accessories.SUNGLASSES);
      }
      default -> outfit.setAccessories(Accessories.GOOD_MOOD);
    };
    return outfit;
  }

  @Override
  public Outfit recieveOufitById(int id) throws OutfitNotFoundException {
    return repository.recieveOufitById(id);
  }

  @Override
  public Collection<Outfit> recieveAll() throws OutfitNotFoundException {
    return repository.recieveAll();
  }

  @Override
  public void addOutfit(Outfit outfit) throws OutfitDatabaseChangesException {
    repository.addOutfit(outfit);
  }

  @Override
  public void deleteOutfit(int id) throws OutfitDatabaseChangesException, OutfitNotFoundException {
    repository.deleteOutfit(id);

  }

  @Override
  public void editOutfit(Outfit outfit) throws OutfitDatabaseChangesException {
    repository.editOutfit(outfit);
  }

}
