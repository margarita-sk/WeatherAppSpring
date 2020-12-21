package city.service;

import java.io.IOException;

import city.client.CityClient;
import city.exception.CityIncorrectNameException;
import city.model.City;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CityServiceImpl implements CityService {

	private CityClient client;

	public City recieveCity(String searchedCityName) throws CityIncorrectNameException, IOException {
		City city = client.recieveCity(searchedCityName);
		if (!city.getActualCityName().equals(searchedCityName))
			throw new CityIncorrectNameException("The name of the city found does not match the search term");
		return city;
	}

}
