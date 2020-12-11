package city.service;

import city.client.CityClient;
import city.entity.City;
import exception.CityException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CityServiceImpl implements CityService {

	private CityClient client;

	public City recieveCity(String searchedCityName) throws Exception {
		City city = client.recieveCity(searchedCityName);
		if (!city.getActualCityName().equals(city.getSearchedCityName()))
			throw new CityException();
		return city;
	}

}
