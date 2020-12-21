package city.client;

import java.io.IOException;

import city.exception.CityIncorrectNameException;
import city.model.City;

public interface CityClient {

	City recieveCity(String searchedCityName) throws CityIncorrectNameException, IOException;

}
