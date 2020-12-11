package weather.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import weather.entity.Weather;

public class YandexWeatherClient implements WeatherClient {

	private String url;
	private String key;
	private String language;

	public YandexWeatherClient(String url, String language) {
		this.url = url;
		this.key = System.getenv("WEATHER_KEY");
		this.language = language;
	}

	@Override
	public Weather recieveWeather(String latitude, String longitude)
			throws JsonMappingException, JsonProcessingException {
		var weather = new Weather();
		String infoFromApi = recieveJSONFromAPI(latitude, longitude);
		JsonNode rootNode = new ObjectMapper().readTree(infoFromApi).get("fact");

		weather.setCondition(rootNode.get("condition").asText());
		weather.setTemperature(rootNode.get("temp").asInt());
		weather.setIconUrl(rootNode.get("icon").asText());
		weather.setDaytime(rootNode.get("daytime").asText());
		return weather;
	}

	public String recieveJSONFromAPI(String latitude, String longitude) throws HttpClientErrorException {
		var builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("lat", latitude).queryParam("lon", longitude)
				.queryParam("lang", language).queryParam("extra", "true");
		var headers = new HttpHeaders();
		headers.set("X-Yandex-API-Key", key);

		HttpEntity<String> response = new RestTemplate().exchange(builder.toUriString(), HttpMethod.GET,
				new HttpEntity<>(headers), String.class);

		return response.getBody();
	}

}
