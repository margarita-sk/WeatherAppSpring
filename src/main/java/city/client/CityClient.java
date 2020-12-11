package city.client;

import city.entity.City;

public interface CityClient {

	City recieveCity(String searchedCityName) throws Exception;

}
