package outfit.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OutfitNotFoundException extends Exception {

	private String message = "Outfit was not found";

}
