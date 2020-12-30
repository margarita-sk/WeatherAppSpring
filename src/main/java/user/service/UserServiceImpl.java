package user.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import user.dao.UserRepository;

@Component
public class UserServiceImpl implements UserService {

  private UserRepository repository;

  @Autowired
  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public Collection<User> receiveAll() {
    return repository.receiveAll();
  }

}
