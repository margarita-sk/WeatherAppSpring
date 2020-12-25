package user.dao;

import java.sql.SQLException;
import java.util.Collection;

import org.springframework.security.core.userdetails.User;

public interface UserRepository {

	Collection<User> receiveAll() throws SQLException, InterruptedException;

}
