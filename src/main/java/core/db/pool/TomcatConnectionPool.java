package core.db.pool;

import org.apache.tomcat.jdbc.pool.DataSource;
import core.db.util.AdaptedConnection;
import core.db.util.CustomDataSource;
import util.ExceptionHandler;;

public class TomcatConnectionPool implements ConnectionPool {
  private DataSource dataSource;

  public TomcatConnectionPool(CustomDataSource source) {
    dataSource = new DataSource();
    dataSource.setDriverClassName(source.getDriverClassName());
    dataSource.setUrl(source.getUrl());
    dataSource.setUsername(source.getUsername());
    dataSource.setPassword(source.getPassword().toString());
    dataSource.setMaxIdle(source.getMaxIdle());
    dataSource.setMaxWait(source.getMaxWaitTime());
  }

  @Override
  public AdaptedConnection receiveConnection() {
    return ExceptionHandler.handleIntoRuntime(() -> {
      return new AdaptedConnection(dataSource.getConnection(), this);
    });
  }

  @Override
  public boolean releaseConnection(AdaptedConnection connectionAdapter) {
    return ExceptionHandler.handleIntoRuntime(() -> {
      connectionAdapter.getConnection().close();
      return true;
    });
  }

}
