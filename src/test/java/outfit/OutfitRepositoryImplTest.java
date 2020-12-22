package outfit;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import outfit.dao.OutfitRepository;
import outfit.exception.OutfitDatabaseChangesException;
import outfit.exception.OutfitNotFoundException;
import outfit.model.Outfit;

class OutfitRepositoryImplTest {
	private static OutfitRepository repository;
	private static int outfitId = 22;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		var context = new ClassPathXmlApplicationContext("applicationContext.xml");
		repository = context.getBean(OutfitRepository.class);
		context.close();
	}

	@Test
	void testRecieveOufitById() throws SQLException, OutfitNotFoundException {
		var outfitPresent = repository.recieveOufitById(outfitId);
		assertNotNull(outfitPresent);
		assertThrows(OutfitNotFoundException.class, () -> {
			repository.recieveOufitById(9999999);
		});
	}

	@Test
	void testRecieveAll() throws SQLException, OutfitNotFoundException {
		assertNotNull(repository.recieveAll());
	}

	@Test
	void testAddOutfit() throws SQLException, OutfitDatabaseChangesException {
		var outfit = new Outfit("test", 0, 0);
		assertThrows(OutfitDatabaseChangesException.class, () -> {
			repository.addOutfit(outfit);
		});
	}

	@Test
	void testDeleteOutfit() throws SQLException, OutfitDatabaseChangesException, OutfitNotFoundException {
		var outfit = repository.recieveAll().stream().findAny().orElseThrow(OutfitNotFoundException::new);
		repository.deleteOutfit(outfit.getId());
	}

	@Test
	void testEditOutfit() throws OutfitNotFoundException, SQLException, OutfitDatabaseChangesException {
		var outfit = repository.recieveAll().stream().findAny().orElseThrow(OutfitNotFoundException::new);
		assertDoesNotThrow(() -> repository.editOutfit(outfit));
	}

}
