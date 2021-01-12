package weather.dto;

/**
 * The {@code InformationService} interface provides the general information of
 * the weather and outfit tips according to the name of the city.
 * 
 * @author Margarita Skokova
 */
public interface InformationService {

	InformationStorage getGeneralInformation(String cityName);

}
