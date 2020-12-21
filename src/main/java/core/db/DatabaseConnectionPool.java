package core.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.commons.dbcp2.BasicDataSource;

import properties.PropertiesHolder;

/**
 * The {@code DatabaseConnection} class provides connections, using DBCP as a
 * library for creating connection pools.
 * 
 * @author Margarita Skokova
 */
public class DatabaseConnectionPool {
	private static String JDBC_DRIVER = PropertiesHolder.recieveConfigDB(PropertiesHolder.Key.JDBC_DRIVER);
	private static String URL = PropertiesHolder.recieveConfigDB(PropertiesHolder.Key.DB_URL);
	private static String USER = PropertiesHolder.recieveConfigDB(PropertiesHolder.Key.DB_USER);
	private static String PASSWORD = PropertiesHolder.recieveConfigDB(PropertiesHolder.Key.DB_PASSWORD);
	private static BasicDataSource basicSource = new BasicDataSource();

	static {
		basicSource.setDriverClassName(JDBC_DRIVER);
		basicSource.setUrl(URL);

		if (Optional.of(USER).isPresent() && Optional.of(PASSWORD).isPresent()) {
			basicSource.setUsername(USER);
			basicSource.setPassword(PASSWORD);
		}
		basicSource.setMinIdle(5);
		basicSource.setMaxIdle(10);
		basicSource.setMaxOpenPreparedStatements(100);
	}

	public static Connection getConnection() throws SQLException {
		return basicSource.getConnection();
	}

	private DatabaseConnectionPool() {
	}
}
