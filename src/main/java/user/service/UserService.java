package user.service;

import java.sql.SQLException;
import java.util.Collection;

import org.springframework.security.core.userdetails.User;

public interface UserService {
	Collection<User> receiveAll() throws SQLException, InterruptedException;
}
