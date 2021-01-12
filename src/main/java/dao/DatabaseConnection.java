package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import util.Config;

/**
 * The {@code DatabaseConnection} class provides connections, using DBCP as a
 * library for creating connection pools.
 * 
 * @author Margarita Skokova
 */
public class DatabaseConnection {
	private static Optional<String> JDBC_DRIVER = Config.getConfigDB(Config.Key.JDBC_DRIVER);
	private static Optional<String> URL = Config.getConfigDB(Config.Key.DB_URL);
	private static Optional<String> USER = Config.getConfigDB(Config.Key.DB_USER);
	private static Optional<String> PASSWORD = Config.getConfigDB(Config.Key.DB_PASSWORD);
	private static BasicDataSource basicSource = new BasicDataSource();

	static {
		basicSource.setDriverClassName(JDBC_DRIVER.get());
		basicSource.setUrl(URL.get());

		if (USER.isPresent() && PASSWORD.isPresent()) {
			basicSource.setUsername(USER.get());
			basicSource.setPassword(PASSWORD.get());
		}
		basicSource.setMinIdle(5);
		basicSource.setMaxIdle(10);
		basicSource.setMaxOpenPreparedStatements(100);
	}

	public static Connection getConnection() throws SQLException {
		return basicSource.getConnection();
	}

	private DatabaseConnection() {
	}
}
