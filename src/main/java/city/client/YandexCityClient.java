package city.client;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import city.entity.City;
import exception.CityException;

public class YandexCityClient implements CityClient {

	private String url;
	private String key;
	private String format;
	private String language;

	public YandexCityClient(String url, String format, String language) {
		this.url = url;
		this.key = System.getenv("CITY_KEY");
		this.format = format;
		this.language = language;
	}

	@Override
	public City recieveCity(String searchedCityName) throws JsonProcessingException, CityException {
		JsonNode rootNode = new ObjectMapper().readTree(recieveJSONFromAPI(searchedCityName));
		JsonNode result = rootNode.get("response").get("GeoObjectCollection").get("featureMember");
		if (!result.has(0))
			throw new CityException();

		JsonNode adress = result.get(0).get("GeoObject").get("metaDataProperty").get("GeocoderMetaData").get("Address")
				.get("Components");
		JsonNode coordinates = result.get(0).get("GeoObject").get("Point").get("pos");

		Stream<JsonNode> stream = StreamSupport.stream(adress.spliterator(), false);
		var city = new City();
		city.setSearchedCityName(searchedCityName);
		city.setActualCityName(stream.reduce((first, second) -> second).get().get("name").asText());
		city.setLongitude(coordinates.asText().split(" ")[0]);
		city.setLatitude(coordinates.asText().split(" ")[1]);
		return city;
	}

	private String recieveJSONFromAPI(String searchedCityName) throws CityException {
		if (searchedCityName.isEmpty())
			throw new CityException();

		var builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("apikey", key)
				.queryParam("geocode", searchedCityName).queryParam("format", format).queryParam("lang", language);

		return new RestTemplate().getForObject(builder.toUriString(), String.class);
	}

}
