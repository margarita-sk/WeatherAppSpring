package weather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WeatherDto {

  private int temperature;
  private String condition;
}
