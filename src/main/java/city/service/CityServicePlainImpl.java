package city.service;

import city.client.CityClient;
import city.entity.City;

public class CityServicePlainImpl implements CityService {

	private CityClient client;

	public City recieveCity(String searchedCityName) throws Exception {
		return client.recieveCity(searchedCityName);
	}

}
