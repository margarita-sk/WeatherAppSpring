package weather;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import city.exception.CityIncorrectNameException;
import city.model.City;
import city.service.CityService;
import weather.model.Weather;
import weather.service.WeatherService;

class WeatherServiceImplTest {

	private static WeatherService service;
	private static City city;
	private static String cityName = "Minsk";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		var context = new ClassPathXmlApplicationContext("applicationContext.xml");
		service = context.getBean(WeatherService.class);
		city = context.getBean(CityService.class).recieveCity(cityName);
		context.close();
	}

	@Test
	void testRecieveCity() throws CityIncorrectNameException, IOException, SQLException {
		Weather weather = service.recieveWeather(city.getLatitude(), city.getLongitude());
		assertNotNull(weather);
		assertNotNull(weather.getCondition());
	}

}
