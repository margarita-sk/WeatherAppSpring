package city.client;

import java.util.stream.StreamSupport;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import city.exception.CityIncorrectNameException;
import city.model.City;
import util.ExceptionHandler;

public class YandexCityClient implements CityClient {

  private final String url;
  private final String key;
  private final String format;
  private final String language;
  private final ObjectMapper mapper;
  private final RestTemplate restTemplate;

  public YandexCityClient(String url, String key, String format, String language) {
    this.url = url;
    this.key = key;
    this.format = format;
    this.language = language;
    mapper = new ObjectMapper();
    restTemplate = new RestTemplate();
  }

  @Override
  public City recieveCity(String searchedCityName) throws CityIncorrectNameException {
    var json = recieveJSONFromAPI(searchedCityName);
    var actualCityName = parseCityName(json);
    var latitude = parseLatitude(json);
    var longitude = parseLongitude(json);
    return new City(actualCityName, latitude, longitude);
  }

  private String recieveJSONFromAPI(String searchedCityName) throws CityIncorrectNameException {
    if (searchedCityName.isEmpty())
      throw new CityIncorrectNameException();

    return ExceptionHandler.handleIntoRuntime(() -> {
      var builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("apikey", key)
          .queryParam("geocode", searchedCityName).queryParam("format", format)
          .queryParam("lang", language);
      return restTemplate.getForObject(builder.toUriString(), String.class);
    });
  }

  private String parseCityName(String json) throws CityIncorrectNameException {
    var resultNode = ExceptionHandler.handleIntoRuntime(() -> {
      return mapper.readTree(json).get("response").get("GeoObjectCollection").get("featureMember");
    });
    if (!resultNode.has(0))
      throw new CityIncorrectNameException();

    var adress = resultNode.get(0).get("GeoObject").get("metaDataProperty").get("GeocoderMetaData")
        .get("Address").get("Components");
    var stream = StreamSupport.stream(adress.spliterator(), false);
    return stream.reduce((first, second) -> second).get().get("name").asText();
  }

  private String parseLatitude(String json) throws CityIncorrectNameException {
    var resultNode = ExceptionHandler.handleIntoRuntime(() -> {
      return mapper.readTree(json).get("response").get("GeoObjectCollection").get("featureMember");
    });
    if (!resultNode.has(0))
      throw new CityIncorrectNameException();

    var coordinates = resultNode.get(0).get("GeoObject").get("Point").get("pos");
    return coordinates.asText().split(" ")[1];
  }

  private String parseLongitude(String json) throws CityIncorrectNameException {
    var resultNode = ExceptionHandler.handleIntoRuntime(() -> {
      return mapper.readTree(json).get("response").get("GeoObjectCollection").get("featureMember");
    });
    if (!resultNode.has(0))
      throw new CityIncorrectNameException();

    var coordinates = resultNode.get(0).get("GeoObject").get("Point").get("pos");
    return coordinates.asText().split(" ")[0];
  }

}
