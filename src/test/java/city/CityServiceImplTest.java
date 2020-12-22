package city;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import city.exception.CityIncorrectNameException;
import city.model.City;
import city.service.CityService;

class CityServiceImplTest {
	private static CityService service;
	private static String cityName = "Minsk";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		var context = new ClassPathXmlApplicationContext("applicationContext.xml");
		service = context.getBean(CityService.class);
		context.close();
	}

	@Test
	void testRecieveCityWithIncorrectName() {
		assertThrows(CityIncorrectNameException.class, () -> {
			service.recieveCity("");
		});

		assertThrows(CityIncorrectNameException.class, () -> {
			service.recieveCity("sdafafasfdsafsafdsafafdsd");
		});
	}

	@Test
	void testRecieveCity() throws CityIncorrectNameException, IOException {
		City city = service.recieveCity(cityName);
		assertEquals(cityName, city.getActualCityName());
	}

}
