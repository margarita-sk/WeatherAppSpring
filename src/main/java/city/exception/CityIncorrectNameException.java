package city.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CityIncorrectNameException extends Exception {

	private String message = "Incorrect city name";

}
