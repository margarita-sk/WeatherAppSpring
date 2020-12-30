package city.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class City {
  private String cityName;
  private String latitude;
  private String longitude;

}
