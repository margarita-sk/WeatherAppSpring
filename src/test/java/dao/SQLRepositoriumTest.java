package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import outfit.entity.Outfit;
import user.entity.UserAccount;
import user.entity.UserAccount.CredentialRole;

class SQLRepositoriumTest {
	static UserAccount userExpected;
	static Outfit outfit;
	static ArrayList<Outfit> outfits;

	@BeforeAll
	static void initializeUser() {
		userExpected = new UserAccount();
		userExpected.setUserName("admin");
		userExpected.setRole(CredentialRole.administrator);
	}

	@BeforeAll
	static void initializeOutfit() {
		outfit = new Outfit(-15, -40, "polar outfit");
		outfits = SQLRepositorium.getClothing();
	}

	@Test
	void testGetClothing() {
		ArrayList<Outfit> outfitActual = SQLRepositorium.getClothing();
		assertTrue(outfitActual.size() > 0);
	}

	@Test
	void testAddClothing() {
		SQLRepositorium.addClothing(outfit);
		outfits = SQLRepositorium.getClothing();
		assertTrue(outfits.contains(outfit));
	}

	void testDeleteOutfit() {
		int id = outfits.get(0).getId();
		SQLRepositorium.deleteOutfit(id);
		outfits = SQLRepositorium.getClothing();
		for (Outfit outfit : outfits) {
			assertTrue(outfit.getId() != id);
		}
	}

	@Test
	void testAuthenticateUser() {
		UserAccount userActual = SQLRepositorium.authenticateUser("admin", "12345".toCharArray());
		assertEquals(userExpected, userActual);
	}

	@Test
	void testGetDecryptionCondition() {
		String expected = "wet snow";
		String actual = SQLRepositorium.getDecryptionCondition("wet-snow");
		assertEquals(expected, actual);
	}

}
