package outfit.entity;

import lombok.Data;

/**
 * The {@code Outfit} class describes the outfit.
 * 
 * @author Margarita Skokova
 */
@Data
public class Outfit {

	private int id;

	/** The temperature at which it is not too hot for this outfit */
	private int maxTemperature;

	/** The temperature at which it is already warm enough to wear this outfit */
	private int minTemperature;

	private String clothing;

	public Outfit(int maxTemperature, int minTemperature, String clothing) {
		this.maxTemperature = maxTemperature;
		this.minTemperature = minTemperature;
		this.clothing = clothing;
	}

	public Outfit() {
	}

	// equals and hashcode methods were overrided without id field, because id is
	// automatically generated by the database and is not an indicator of the
	// logical identity of objects
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Outfit other = (Outfit) obj;
		if (clothing == null) {
			if (other.clothing != null)
				return false;
		} else if (!clothing.equals(other.clothing))
			return false;
		if (maxTemperature != other.maxTemperature)
			return false;
		if (minTemperature != other.minTemperature)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clothing == null) ? 0 : clothing.hashCode());
		result = prime * result + maxTemperature;
		result = prime * result + minTemperature;
		return result;
	}

}
