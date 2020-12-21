package outfit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import exception.OutfitException;
import outfit.dao.OutfitRepositoryImpl;
import outfit.model.Outfit;
import outfit.service.OutfitServiceImpl;

class OutfitRepositoryImplTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testRecieveOufitById() {
//		Throwable thrown = assertThrows(OutfitException.class, () -> {
//			new OutfitRepositoryImpl().recieveOufitById(1);
//		});
////		assertNotNull(thrown.getMessage());
//		var outfit = new OutfitRepositoryImpl().recieveOufitById(24);
//
//		System.out.println(outfit.getOutfitName());
	}

	@Test
	void testRecieveAll() {
	}

	@Test
	void testAddOutfit() {
	}

	@Test
	void testDeleteOutfit() {
	}

	@Test
	void testEditOutfit() {
	}

}
