package weather;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import city.client.CityClient;
import city.exception.CityIncorrectNameException;
import city.model.City;
import weather.client.WeatherClient;
import weather.model.Weather;

class YandexWeatherClientTest {
	private static WeatherClient client;
	private static City city;
	private static String cityName = "Minsk";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		var context = new ClassPathXmlApplicationContext("applicationContext.xml");
		client = context.getBean(WeatherClient.class);
		city = context.getBean(CityClient.class).recieveCity(cityName);
		context.close();
	}

	@Test
	void testRecieveCity() throws CityIncorrectNameException, IOException {
		Weather weather = client.recieveWeather(city.getLatitude(), city.getLongitude());
		assertNotNull(weather);
		assertNotNull(weather.getCondition());
	}

}
