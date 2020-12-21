package outfit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exception.OutfitException;
import outfit.dao.OutfitRepositoryImpl;
import outfit.model.Outfit;
import outfit.service.OutfitServiceImpl;
import weather.client.FileWeatherClient;
import weather.model.Weather;

class OutfitServiceImplTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testOutfitServiceImpl() {

	}

	@Test
	void testRecieveOutfitByWeather() {
		Weather weather = new Weather();
		weather.setTemperature(999999);

		Assertions.assertThrows(OutfitException.class, () -> {
			new OutfitServiceImpl(new OutfitRepositoryImpl()).recieveOutfitByWeather(weather);
		});

	}

	@Test
	void testRecieveOufitById() {

	}

	@Test
	void testRecieveAll() {

	}

	@Test
	void testAddOutfit() {

	}

	@Test
	void testDeleteOutfit() {

	}

	@Test
	void testEditOutfit() {
	}

}
