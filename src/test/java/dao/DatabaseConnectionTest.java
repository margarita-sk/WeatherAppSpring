package dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DatabaseConnectionTest {

	@Test
	void testGetConnection() {
		assertDoesNotThrow(() -> DatabaseConnection.getConnection());
	}

}
