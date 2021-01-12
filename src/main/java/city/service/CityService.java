package city.service;

import city.entity.City;

/**
 * The {@code CityService} interface provides the object of the class
 * {@code City} by its name
 * 
 * @author Margarita Skokova
 */
public interface CityService {

	City parseCityByName(String cityName);

}
