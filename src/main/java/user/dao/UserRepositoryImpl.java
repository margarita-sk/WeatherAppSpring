package user.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;
import core.db.pool.ConnectionPool;
import lombok.extern.log4j.Log4j;

@Log4j
@Repository
public class UserRepositoryImpl implements UserRepository {
  private ConnectionPool connectionPool;
  private static final String RECEIVE_USER = "SELECT * FROM users";

  private static final int GET_LOGIN_INDEX = 2;
  private static final int GET_PASSWORD_INDEX = 3;
  private static final int GET_ROLE_INDEX = 4;

  @Autowired
  public UserRepositoryImpl(ConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
  }

  @Override
  public Collection<User> receiveAll() {
    var users = new ArrayList<User>();
    try (var connection = connectionPool.receiveConnection();
        var prStatement = connection.getConnection().prepareStatement(RECEIVE_USER)) {
      var result = prStatement.executeQuery();
      while (result.next()) {
        var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(result.getString(GET_ROLE_INDEX)));
        users.add(new User(result.getString(GET_LOGIN_INDEX), result.getString(GET_PASSWORD_INDEX),
            authorities));
      }
    } catch (SQLException e) {
      log.error(e);
      throw new RuntimeException(e);
    }
    return users;
  }

}
