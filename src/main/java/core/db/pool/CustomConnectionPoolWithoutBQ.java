package core.db.pool;

import java.sql.DriverManager;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import core.db.util.AdaptedConnection;
import core.db.util.CustomDataSource;
import util.ExceptionHandler;

public class CustomConnectionPoolWithoutBQ implements ConnectionPool {

  private static CustomConnectionPoolWithoutBQ instance;

  private final String driver;
  private final String url;
  private final String user;
  private final char[] password;
  private final int maxPoolSize;
  private final int maxWaitTime;

  private LinkedList<AdaptedConnection> availableCons;

  @Autowired
  private CustomConnectionPoolWithoutBQ(CustomDataSource source) {
    this.driver = source.getDriverClassName();
    this.url = source.getUrl();
    this.user = source.getUsername();
    this.password = source.getPassword();
    this.maxPoolSize = source.getMaxIdle();
    this.maxWaitTime = source.getMaxWaitTime();
    availableCons = initializeConnectionPool();
  }

  public static CustomConnectionPoolWithoutBQ getInstance(CustomDataSource source) {
    if (instance == null) {
      instance = new CustomConnectionPoolWithoutBQ(source);
    }
    return instance;
  }

  private LinkedList<AdaptedConnection> initializeConnectionPool() {
    var connections = new LinkedList<AdaptedConnection>();
    return ExceptionHandler.handleIntoRuntime(() -> {
      Class.forName(driver);
      for (int i = 0; i < maxPoolSize; i++) {
        connections.add(new AdaptedConnection(
            DriverManager.getConnection(url, user, password.toString()), this));
      }
      return connections;
    });

  }

  @Override
  public synchronized AdaptedConnection receiveConnection() {
    return ExceptionHandler.handleIntoRuntime(() -> {
      while (availableCons.isEmpty()) {
        this.wait(maxWaitTime);
      }
      return availableCons.getLast();
    });
  }

  @Override
  public synchronized boolean releaseConnection(AdaptedConnection conn) {
    if (availableCons.add(conn)) {
      notify();
    }
    return true;
  }

}
