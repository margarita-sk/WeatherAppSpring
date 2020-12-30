package city.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import city.client.CityClient;
import city.exception.CityIncorrectNameException;
import city.model.City;

@Service
public class CityServiceImpl implements CityService {

  private CityClient client;

  @Autowired
  public CityServiceImpl(CityClient client) {
    this.client = client;
  }

  public City recieveCity(String searchedCityName) throws CityIncorrectNameException {
    var city = client.recieveCity(searchedCityName);
    if (!city.getCityName().equals(searchedCityName))
      throw new CityIncorrectNameException(
          "The name of the city found does not match the search term");
    return city;
  }

}
