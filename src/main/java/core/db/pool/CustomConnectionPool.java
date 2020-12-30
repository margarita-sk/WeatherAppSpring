package core.db.pool;

import java.sql.DriverManager;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import core.db.util.AdaptedConnection;
import core.db.util.CustomDataSource;
import util.ExceptionHandler;

/**
 * The {@code DatabaseConnection} class provides connections, using DBCP as a library for creating
 * connection pools.
 * 
 * @author Margarita Skokova
 */

public class CustomConnectionPool implements ConnectionPool {
  private final String driver;
  private final String url;
  private final String user;
  private final char[] password;
  private final int maxPoolSize;
  private final int maxWaitTime;

  private BlockingQueue<AdaptedConnection> pool;
  private Semaphore semaphore;
  private static CustomConnectionPool instance;

  private CustomConnectionPool(CustomDataSource source) {
    this.driver = source.getDriverClassName();
    this.url = source.getUrl();
    this.user = source.getUsername();
    this.password = source.getPassword();
    this.maxPoolSize = source.getMaxIdle();
    this.maxWaitTime = source.getMaxWaitTime();
    semaphore = new Semaphore(maxPoolSize);
    this.pool = initPoolConnections();
  }

  @Autowired
  public static CustomConnectionPool getInstance(CustomDataSource source) {
    if (instance == null) {
      instance = new CustomConnectionPool(source);
    }
    return instance;
  }

  private LinkedBlockingQueue<AdaptedConnection> initPoolConnections() {
    var pool = new LinkedBlockingQueue<AdaptedConnection>(maxPoolSize);
    return ExceptionHandler.handleIntoRuntime(() -> {
      Class.forName(driver);
      for (int i = 0; i < maxPoolSize; i++) {
        var conn = new AdaptedConnection(
            DriverManager.getConnection(url, user, password.toString()), this);
        pool.offer(conn);
      }
      return pool;
    });
  }

  @Override
  public AdaptedConnection receiveConnection() {
    return ExceptionHandler.handleIntoRuntime(() -> {
      var conn = pool.take();
      semaphore.tryAcquire(maxWaitTime, TimeUnit.MILLISECONDS);
      return conn;
    });
  }

  @Override
  public boolean releaseConnection(AdaptedConnection conn) {
    return ExceptionHandler.handleIntoRuntime(() -> {
      semaphore.release();
      pool.offer(conn);
      return true;
    });
  }

}
