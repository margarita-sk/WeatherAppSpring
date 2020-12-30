package weather.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Weather {

  private int temperature;
  private String condition;
  private String iconUrl;
  private Daytime daytime;
  private Precipitation precipitation;

  public enum Daytime {
    DAY, NIGHT
  }

  public enum Precipitation {
    CLEAR, PRECIPITATION, NO_PRECIPITATION
  }

}
