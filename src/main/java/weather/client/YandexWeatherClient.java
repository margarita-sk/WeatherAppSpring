package weather.client;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import weather.model.Weather;

public class YandexWeatherClient implements WeatherClient {

	private String url;
	private String key;
	private String language;
	private ObjectMapper mapper;
	private RestTemplate restTemplate;

	public YandexWeatherClient(String url, String key, String language) {
		this.url = url;
		this.key = key;
		this.language = language;
		mapper = new ObjectMapper();
		restTemplate = new RestTemplate();
	}

	@Override
	public Weather recieveWeather(String latitude, String longitude) throws IOException {
		var weather = new Weather();
		var infoFromApi = recieveJSONFromAPI(latitude, longitude);
		var rootNode = mapper.readTree(infoFromApi).get("fact");

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

		HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
				new HttpEntity<>(headers), String.class);

		return response.getBody();
	}

}
