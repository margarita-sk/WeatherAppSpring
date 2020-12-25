package core.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TomcatConnectionPool implements ConnectionPool {
	private DataSource dataSource;

	@Autowired
	public TomcatConnectionPool(DataSource source) {
		this.dataSource = source;
	}

	@Override
	public Connection receiveConnection() throws SQLException, InterruptedException {
		return dataSource.getConnection();
	}

}
