package core.db.util;

import java.sql.Connection;
import core.db.pool.ConnectionPool;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdaptedConnection implements AutoCloseable {

  private Connection connection;
  private ConnectionPool pool;

  @Override
  public void close() {
    pool.releaseConnection(this);
  }

}
