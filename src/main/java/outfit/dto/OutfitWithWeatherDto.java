package outfit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OutfitWithWeatherDto {

  private String cityName;

  private int temperature;
  private String condition;
  private String iconUrl;

  private String outfitName;
  private String accessories;

}
