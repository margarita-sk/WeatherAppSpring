package weather.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import weather.dao.WeatherRepository;
import weather.dto.WeatherYandexDto;
import weather.exception.WeatherNotFoundException;
import weather.model.Weather;
import weather.model.Weather.Daytime;

public class YandexWeatherClient implements WeatherClient {

  private final String url;
  private final String key;
  private final String language;
  private ObjectMapper mapper;
  private RestTemplate restTemplate;
  private WeatherRepository repository;

  public YandexWeatherClient(WeatherRepository repository, String url, String key,
      String language) {
    this.url = url;
    this.key = key;
    this.language = language;
    this.repository = repository;
    mapper = new ObjectMapper();
    restTemplate = new RestTemplate();
  }

  @Override
  public Weather recieveWeather(String latitude, String longitude) throws WeatherNotFoundException {
    var infoFromApi = recieveJSONFromAPI(latitude, longitude);
    var weatherDto = receiveWeatherDto(infoFromApi);
    var condition = repository.receiveCondition(weatherDto.getCondition());
    var precipitation = repository.receivePrecipitation(weatherDto.getCondition());
    var daytime = switch (weatherDto.getDaytime()) {
      case "d" -> Daytime.DAY;
      default -> Daytime.NIGHT;
    };
    return new Weather(weatherDto.getTemperature(), condition, weatherDto.getIconUrl(), daytime,
        precipitation);
  }

  private WeatherYandexDto receiveWeatherDto(String info) throws WeatherNotFoundException {
    try {
      var rootNode = mapper.readTree(info).get("fact");
      return new WeatherYandexDto(rootNode.get("condition").asText(), rootNode.get("temp").asInt(),
          rootNode.get("icon").asText(), rootNode.get("daytime").asText());
    } catch (JsonProcessingException e) {
      throw new WeatherNotFoundException();
    }
  }

  private String recieveJSONFromAPI(String latitude, String longitude)
      throws WeatherNotFoundException {
    try {
      var builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("lat", latitude)
          .queryParam("lon", longitude).queryParam("lang", language).queryParam("extra", "true");
      var headers = new HttpHeaders();
      headers.set("X-Yandex-API-Key", key);
      var response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
          new HttpEntity<>(headers), String.class);
      return response.getBody();
    } catch (HttpClientErrorException e) {
      throw new WeatherNotFoundException();
    }
  }

}
