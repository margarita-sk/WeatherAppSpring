package weather.dto;

import lombok.Getter;

@Getter
public class WeatherYandexDto extends WeatherDto {

  private String iconUrl;
  private String daytime;

  public WeatherYandexDto(String condition, int temperature, String iconUrl, String daytime) {
    super(temperature, condition);
    this.iconUrl = iconUrl;
    this.daytime = daytime;
  }

}
