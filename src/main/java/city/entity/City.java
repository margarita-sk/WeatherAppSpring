package city.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class City {
	private String searchedCityName;
	private String actualCityName;
	private String latitude;
	private String longitude;

}
