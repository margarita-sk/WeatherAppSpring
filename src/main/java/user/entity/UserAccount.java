package user.entity;

import lombok.*;

/**
 * The {@code UserAccount} is an utility class for storing user information in
 * sessions
 * 
 * @author Margarita Skokova
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAccount {

	private String userName;
	private CredentialRole role;

	public enum CredentialRole {
		moderator, administrator
	}

	public CredentialRole checkCredentials() {
		return role;
	}
}
