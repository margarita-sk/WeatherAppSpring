package user.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import core.db.ConnectionPool;

public class UserRepositoryImpl implements UserRepository {
	private ConnectionPool connectionPool;
	private static final String RECEIVE_USER = "SELECT * FROM users";

	public UserRepositoryImpl(@Qualifier("myConnectionPool") ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	@Override
	public Collection<User> receiveAll() throws SQLException, InterruptedException {
		Collection<User> users = new ArrayList<>();
		try (var connection = connectionPool.receiveConnection();
				var prStatement = connection.prepareStatement(RECEIVE_USER)) {
			var result = prStatement.executeQuery();
			while (result.next()) {
				Collection<GrantedAuthority> authorities = new ArrayList<>();
				authorities.add(new SimpleGrantedAuthority(result.getString(4)));
				users.add(new User(result.getString(2), result.getString(3), authorities));
			}
		}
		return users;
	}

}
