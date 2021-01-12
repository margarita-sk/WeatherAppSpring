package weather.service;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.hc.core5.net.URIBuilder;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import city.entity.City;
import dao.DatabaseConnection;
import util.Config;
import weather.entity.Weather;

/**
 * The {@code WeatherServiceImpl} class provides connection to the weather API
 * and parse the weather information of current city.
 * 
 * @author Margarita Skokova
 */
public class WeatherServiceImpl implements WeatherService {
	private static final Logger log = Logger.getLogger(WeatherServiceImpl.class);

	/**
	 * the url and the key for the weather API
	 */
	private final static String WEATHER_URL = Config.getConfig(Config.Key.WEATHER_URL).get();
	private final static String key = Config.getConfig(Config.Key.WEATHER_KEY).get();
	private final static String value = Config.getConfig(Config.Key.WEATHER_VALUE).get();

	@Override
	public Weather getWeather(City city) {
		if (city == null)
			return null;
		return parseWeatherFromJSON(getConnection(city.getLatitude(), city.getLongitude()));
	}

	private HttpURLConnection getConnection(String latitude, String longitude) {
		try {
			URIBuilder b = new URIBuilder(WEATHER_URL);
			b.addParameter("lat", latitude);
			b.addParameter("lon", longitude);
			b.addParameter("extra", "true");
			URL url = b.build().toURL();

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty(key, value);
			return connection;
		} catch (IOException | URISyntaxException e) {
			log.error(e);
			return null;
		}
	}

	private Weather parseWeatherFromJSON(HttpURLConnection connection) {
		Weather weather = new Weather();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

			JSONObject fact = (JSONObject) jsonObject.get("fact");

			System.out.println(fact);

			weather.setTemperature((long) fact.get("temp"));
			weather.setUrlIcon((String) fact.get("icon"));
			weather.setDaytime((String) fact.get("daytime"));

			weather.setCondition((String) fact.get("condition"));
			weather.setPrecipitationType(fact.get("prec_type").toString());
		} catch (IOException | ParseException e) {
			log.error(e);
			e.printStackTrace();
			return null;
		}
		return weather;
	}

}
