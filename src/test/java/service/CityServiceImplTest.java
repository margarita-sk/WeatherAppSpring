package service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import city.entity.City;
import city.service.CityServiceImpl;

class CityServiceImplTest {
	static ArrayList<String> cities;
	static final String filePath = "src/test/resources/cititesTest.json";

	@BeforeAll
	public static void inititializeCities() {
		cities = new ArrayList<String>();
		cities.add("Минск");
		cities.add("Нефтегорск");
		cities.add("Антананариву");
		cities.add("Хиросима");
		cities.add("Мехико");
		cities.add("Буэнос-Айрес");
		cities.add("Кингстон");
	}

	@Test
	void testParseCityByName() {

		for (String cityName : cities) {
			City cityActual = new CityServiceImpl().parseCityByName(cityName);
			City cityExpected = getCityExpected(cityName);
			assertTrue(cityActual.getLatitude().contains(cityExpected.getLatitude()));
			assertTrue(cityActual.getLongitude().contains(cityExpected.getLongitude()));
		}
	}

	City getCityExpected(String cityName) {
		City cityExpected = new City();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(filePath));
			JSONArray cities = (JSONArray) jsonObject.get("Cities");
			Iterator<JSONObject> i = cities.iterator();

			while (i.hasNext()) {
				JSONObject city = i.next();
				if (cityName.equals(city.get("City").toString())) {
					cityExpected.setName(cityName);
					cityExpected.setLatitude(city.get("latitude").toString());
					cityExpected.setLongitude(city.get("longitude").toString());
				}
			}
		} catch (NullPointerException | IOException | ParseException ex) {
			ex.printStackTrace();
		}
		return cityExpected;
	}

}
