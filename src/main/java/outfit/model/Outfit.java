package outfit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Outfit {

  @NonNull
  private Integer id;
  @NonNull
  private String outfitName;
  @NonNull
  private Integer minTemperatureToWear;
  @NonNull
  private Integer maxTemperatureToWear;

  private Accessories accessories;

  @Getter
  public enum Accessories {
    GOOD_MOOD("a good mood"), SUNGLASSES("sunglasses"), UMBRELLA("an umbrella");

    String name;

    Accessories(String name) {
      this.name = name;
    }
  }

}
