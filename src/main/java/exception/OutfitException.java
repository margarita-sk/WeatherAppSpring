package exception;

import lombok.Getter;

@Getter
public class OutfitException extends Exception {

	private String message = "Outfit was not found";
}
