package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import outfit.entity.Outfit;

public class OutfitValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Outfit.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Outfit outfit = (Outfit) target;
		Matcher matcherName = Pattern.compile("[a-zA-Z,\\s]+").matcher(outfit.getOutfitName());
		if (!matcherName.matches()) {
			errors.rejectValue("outfitName", "incorrectInputType", "Incorrect name of the outfit");
		}

		if (outfit.getMaxTemperatureToWear() <= outfit.getMinTemperatureToWear()) {
			errors.rejectValue("minTemperatureToWear", "incorrectInputType", "Incorrect temperature regime");
			errors.rejectValue("maxTemperatureToWear", "incorrectInputType", "Incorrect temperature regime");
		}

	}

}
