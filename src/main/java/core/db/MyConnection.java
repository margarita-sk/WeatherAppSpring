package core.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.Getter;

@Getter
public class MyConnection implements AutoCloseable {

	private Connection connection;
	private MyConnectionPool pool;

	public MyConnection(String url, String user, String password) throws SQLException {
		this.connection = DriverManager.getConnection(url, user, password);
	}

	@Override
	public void close() throws SQLException {
		pool.releaseConnection(this);
	}

}
