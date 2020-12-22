package outfit;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import outfit.exception.OutfitDatabaseChangesException;
import outfit.exception.OutfitNotFoundException;
import outfit.model.Outfit;
import outfit.service.OutfitService;

class OutfitServiceImplTest {
	private static OutfitService service;
	private static int outfitId = 22;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		var context = new ClassPathXmlApplicationContext("applicationContext.xml");
		service = context.getBean(OutfitService.class);
		context.close();
	}

	@Test
	void testRecieveOufitById() throws SQLException, OutfitNotFoundException {
		var outfitPresent = service.recieveOufitById(outfitId);
		assertNotNull(outfitPresent);
		assertThrows(OutfitNotFoundException.class, () -> {
			service.recieveOufitById(9999999);
		});
	}

	@Test
	void testRecieveAll() throws SQLException, OutfitNotFoundException {
		assertNotNull(service.recieveAll());
	}

	@Test
	void testAddOutfit() throws SQLException, OutfitDatabaseChangesException {
		var outfit = new Outfit("test", 0, 0);
		assertThrows(OutfitDatabaseChangesException.class, () -> {
			service.addOutfit(outfit);
		});
	}

	@Test
	void testDeleteOutfit() throws SQLException, OutfitDatabaseChangesException, OutfitNotFoundException {
		var outfit = service.recieveAll().stream().findAny().orElseThrow(OutfitNotFoundException::new);
		service.deleteOutfit(outfit.getId());
	}

	@Test
	void testEditOutfit() throws OutfitNotFoundException, SQLException, OutfitDatabaseChangesException {
		var outfit = service.recieveAll().stream().findAny().orElseThrow(OutfitNotFoundException::new);
		assertDoesNotThrow(() -> service.editOutfit(outfit));
	}

}
