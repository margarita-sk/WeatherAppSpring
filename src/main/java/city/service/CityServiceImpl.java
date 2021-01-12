package city.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.*;

import org.apache.hc.core5.net.URIBuilder;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import city.entity.City;
import dao.DatabaseConnection;
import util.Config;
import util.Config.Key;

/**
 * The {@code CityServiceImpl} class provides connection to geocode-maps.yandex
 * API, parse the longitude and latitude of current city
 * 
 * @author Margarita Skokova
 */
public class CityServiceImpl implements CityService {
	private static final Logger log = Logger.getLogger(CityServiceImpl.class);

	/**
	 * the url, key for connection and the format and language of received
	 * information
	 */
	private static final String GEOCODER_URL = Config.getConfig(Key.GEOCODER_URL).get();
	private static final String key = Config.getConfig(Key.GEOCODER_KEY).get();
	private static final String value = Config.getConfig(Key.GEOCODER_VALUE).get();
	private static final String format = Config.getConfig(Key.GEOCODER_OUTPUT_FORMAT).get();
	private static final String language = Config.getConfig(Key.GEOCODER_LANG).get();

	@Override
	public City parseCityByName(String cityName) {
		return parseCityFromJSON(getConnection(cityName));
	}

	private HttpURLConnection getConnection(String cityName) {
		try {
			URIBuilder b = new URIBuilder(GEOCODER_URL);
			b.addParameter(key, value);
			b.addParameter("geocode", cityName);
			b.addParameter("format", format);
			b.addParameter("lang", language);
			URL url = b.build().toURL();
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			return connection;
		} catch (IOException | URISyntaxException e) {
			log.error(e);
			return null;
		}
	}

	// this method looks ridiculous, unfortunately, I have no idea about bringing it
	// to proper form
	private City parseCityFromJSON(HttpURLConnection connection) {
		City city = new City();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

			JSONObject response = (JSONObject) jsonObject.get("response");
			JSONObject geoObject = (JSONObject) response.get("GeoObjectCollection");
			JSONObject metaData = (JSONObject) geoObject.get("metaDataProperty");
			JSONObject geoResponse = (JSONObject) metaData.get("GeocoderResponseMetaData");

			if (geoResponse.get("found").equals("0"))
				return null;

			JSONArray feature = (JSONArray) geoObject.get("featureMember");
			JSONObject object = (JSONObject) feature.get(0);
			JSONObject object2 = (JSONObject) object.get("GeoObject");
			JSONObject object3 = (JSONObject) object2.get("Point");

			JSONObject metaDataProperty = (JSONObject) object2.get("metaDataProperty");
			JSONObject geocoderMetaDataProperty = (JSONObject) metaDataProperty.get("GeocoderMetaData");
			JSONObject adress = (JSONObject) geocoderMetaDataProperty.get("Address");
			JSONArray components = (JSONArray) adress.get("Components");
			JSONObject locality = (JSONObject) components.get(components.size() - 1);
			String localityName = locality.get("name").toString();

			city.setName(localityName);

			Pattern pattern = Pattern.compile("([\\-|\\+]?\\d+\\.\\d+)\\s([\\-|\\+]?\\d+\\.\\d+)");
			Matcher matcher = pattern.matcher(object3.toString());
			matcher.find();
			city.setLongitude(matcher.group(1));
			city.setLatitude(matcher.group(2));
		} catch (IOException | ParseException e) {
			log.error(e);
		}
		return city;
	}

}
