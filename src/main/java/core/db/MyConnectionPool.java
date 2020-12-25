package core.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The {@code DatabaseConnection} class provides connections, using DBCP as a
 * library for creating connection pools.
 * 
 * @author Margarita Skokova
 */

@Component
public class MyConnectionPool implements ConnectionPool {
	private String driver;
	private String url;
	private String user;
	private String password;

	private BlockingQueue<Connection> pool;

	private int maxPoolSize;
	private int currentPoolSize;

	@Autowired
	public MyConnectionPool(DataSource source) throws ClassNotFoundException, SQLException {
		this.driver = source.getDriverClassName();
		this.url = source.getUrl();
		this.user = source.getUsername();
		this.password = System.getenv("DATABASE_PASS");
		this.maxPoolSize = source.getMaxIdle();
		if (currentPoolSize >= maxPoolSize) {
			throw new IllegalArgumentException("Invalid pool size parameters");
		}
		this.pool = new LinkedBlockingQueue<Connection>(maxPoolSize);
		initPoolConnections();
	}

	private void initPoolConnections() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		for (int i = 0; i < maxPoolSize; i++) {
			var myConn = new MyConnection(url, user, password);
			pool.offer(myConn.getConnection());
			currentPoolSize++;
		}
	}

	@Override
	public Connection receiveConnection() throws SQLException, InterruptedException {
		currentPoolSize--;
		return pool.take();
	}

	public boolean releaseConnection(MyConnection myCon) {
		currentPoolSize++;
		return pool.offer(myCon.getConnection());
	}

}
