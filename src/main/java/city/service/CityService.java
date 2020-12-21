package city.service;

import java.io.IOException;

import city.exception.CityIncorrectNameException;
import city.model.City;

public interface CityService {

	City recieveCity(String searchedCityName) throws CityIncorrectNameException, IOException;

}
