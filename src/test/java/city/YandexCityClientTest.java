package city;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import city.client.CityClient;
import city.exception.CityIncorrectNameException;
import city.model.City;

class YandexCityClientTest {
	private static CityClient client;
	private static String cityName = "Minsk";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		var context = new ClassPathXmlApplicationContext("applicationContext.xml");
		client = context.getBean(CityClient.class);
		context.close();
	}

	@Test
	void testRecieveCityWithIncorrectName() {
		assertThrows(CityIncorrectNameException.class, () -> {
			client.recieveCity("");
		});

		assertThrows(CityIncorrectNameException.class, () -> {
			client.recieveCity("sdafafasfdsafsafdsafafdsd");
		});
	}

	@Test
	void testRecieveCity() throws CityIncorrectNameException, IOException {
		City city = client.recieveCity(cityName);
		assertEquals(cityName, city.getActualCityName());
	}

}
