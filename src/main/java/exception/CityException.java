package exception;

import lombok.Getter;

@Getter
public class CityException extends Exception {

	private String message = "Incorrect city name";

}
