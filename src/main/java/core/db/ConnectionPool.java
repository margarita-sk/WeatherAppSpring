package core.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {

	Connection receiveConnection() throws SQLException, InterruptedException;

}
