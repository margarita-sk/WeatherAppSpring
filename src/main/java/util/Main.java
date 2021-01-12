package util;

import java.util.Optional;

public class Main {

	public static void main(String[] args) {
		Optional<String> o = Optional.ofNullable(null);
		System.out.println(o.toString());

	}

}
