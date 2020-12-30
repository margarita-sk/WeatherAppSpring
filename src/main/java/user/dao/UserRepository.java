package user.dao;

import java.util.Collection;
import org.springframework.security.core.userdetails.User;

public interface UserRepository {

  Collection<User> receiveAll();

}
