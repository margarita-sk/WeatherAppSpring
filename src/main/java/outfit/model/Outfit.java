package outfit.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Outfit {

	private String outfitName;

	private int minTemperatureToWear;
	private int maxTemperatureToWear;
	private int id;

	private String accessories;

	public Outfit(int id, String outfitName, int minTemperature, int maxTemperature) {
		this.id = id;
		this.outfitName = outfitName;
		this.minTemperatureToWear = minTemperature;
		this.maxTemperatureToWear = maxTemperature;

	}

	public Outfit(String outfitName, int minTemperatureToWear, int maxTemperatureToWear) {
		this.outfitName = outfitName;
		this.minTemperatureToWear = minTemperatureToWear;
		this.maxTemperatureToWear = maxTemperatureToWear;
	}

}
