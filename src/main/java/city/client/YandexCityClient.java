package city.client;

import java.io.IOException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import city.exception.CityIncorrectNameException;
import city.model.City;

public class YandexCityClient implements CityClient {

	private String url;
	private String key;
	private String format;
	private String language;
	private ObjectMapper mapper;
	private RestTemplate restTemplate;

	public YandexCityClient(String url, String key, String format, String language) {
		this.url = url;
		this.key = key;
		this.format = format;
		this.language = language;
		mapper = new ObjectMapper();
		restTemplate = new RestTemplate();
	}

	@Override
	public City recieveCity(String searchedCityName) throws CityIncorrectNameException, IOException {
		String json = recieveJSONFromAPI(searchedCityName);
		String actualCityName = parseCityName(json);
		String latitude = parseLatitude(json);
		String longitude = parseLongitude(json);
		return new City(actualCityName, latitude, longitude);
	}

	private String recieveJSONFromAPI(String searchedCityName) throws CityIncorrectNameException {
		if (searchedCityName.isEmpty())
			throw new CityIncorrectNameException();

		var builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("apikey", key)
				.queryParam("geocode", searchedCityName).queryParam("format", format).queryParam("lang", language);

		return restTemplate.getForObject(builder.toUriString(), String.class);
	}

	private String parseCityName(String json)
			throws JsonMappingException, JsonProcessingException, CityIncorrectNameException {
		var resultNode = mapper.readTree(json).get("response").get("GeoObjectCollection").get("featureMember");
		if (!resultNode.has(0))
			throw new CityIncorrectNameException();

		var adress = resultNode.get(0).get("GeoObject").get("metaDataProperty").get("GeocoderMetaData").get("Address")
				.get("Components");

		Stream<JsonNode> stream = StreamSupport.stream(adress.spliterator(), false);
		return stream.reduce((first, second) -> second).get().get("name").asText();
	}

	private String parseLatitude(String json)
			throws JsonMappingException, JsonProcessingException, CityIncorrectNameException {
		var resultNode = mapper.readTree(json).get("response").get("GeoObjectCollection").get("featureMember");
		if (!resultNode.has(0))
			throw new CityIncorrectNameException();

		var coordinates = resultNode.get(0).get("GeoObject").get("Point").get("pos");

		return coordinates.asText().split(" ")[1];
	}

	private String parseLongitude(String json)
			throws JsonMappingException, JsonProcessingException, CityIncorrectNameException {
		var resultNode = mapper.readTree(json).get("response").get("GeoObjectCollection").get("featureMember");
		if (!resultNode.has(0))
			throw new CityIncorrectNameException();

		var coordinates = resultNode.get(0).get("GeoObject").get("Point").get("pos");

		return coordinates.asText().split(" ")[0];
	}

}
