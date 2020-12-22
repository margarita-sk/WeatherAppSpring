package weather;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import weather.dao.WeatherRepository;
import weather.model.Weather;

class WeatherRepositoryImplTest {
	static WeatherRepository repository;
	static Weather weatherActual = new Weather();
	static Weather weatherExpected = new Weather();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		var context = new ClassPathXmlApplicationContext("applicationContext.xml");
		repository = context.getBean(WeatherRepository.class);
		context.close();
		weatherActual.setCondition("thunderstorm-with-hail");
		weatherExpected.setCondition("thunderstorm with hail");
	}

	@Test
	void testDecryptCondition() throws SQLException {
		weatherActual = repository.decryptCondition(weatherActual);
		assertEquals(weatherExpected, weatherActual);
	}

}
