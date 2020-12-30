package core.db.pool;

import core.db.util.AdaptedConnection;

public interface ConnectionPool {

  AdaptedConnection receiveConnection();

  boolean releaseConnection(AdaptedConnection conn);

}
