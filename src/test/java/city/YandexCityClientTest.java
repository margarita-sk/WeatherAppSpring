package city;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Executable;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import city.client.YandexCityClient;
import city.entity.City;
import exception.CityException;

class YandexCityClientTest {

//	@BeforeAll
//	static void setUpBeforeClass() throws Exception {
//	}
//
//	@Test
//	void testRecieveCityWithIncorrectName() {
//		assertThrows(CityException.class, () -> {
//			new YandexCityClient().recieveCity("");
//		});
//
//		assertThrows(CityException.class, () -> {
//			new YandexCityClient().recieveCity("sdafafasfdsafsafdsafafdsd");
//		});
//	}
//
//	@Test
//	void testRecieveCity() throws JsonProcessingException, CityException {
//		City city = new YandexCityClient().recieveCity("Minsk");
//		System.out.println(city);
//	}

}
