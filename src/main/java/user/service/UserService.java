package user.service;

import java.util.Collection;
import org.springframework.security.core.userdetails.User;

public interface UserService {
  Collection<User> receiveAll();
}
