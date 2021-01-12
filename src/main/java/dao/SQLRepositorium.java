package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import outfit.entity.Outfit;
import user.entity.UserAccount;
import user.entity.UserAccount.CredentialRole;
import util.Config;
import util.Config.Key;

/**
 * The {@code SQLRepositorium} class executes SQL commands to edit or to receive
 * the information from the database.
 * 
 * @author Margarita Skokova
 */
public class SQLRepositorium {
	private static final Logger log = Logger.getLogger(SQLRepositorium.class);

	// choice of database language (works partially - for subsequent implementation)
	public static final String lang = Config.getConfig(Key.DB_LANG).get();

	private static final String getOutfitsQuery = "SELECT * FROM outfits";
	private static final String getOutfitQueryById = "SELECT clothing, temp_max, temp_min FROM outfits WHERE id = ?";
	private static final String getPasswordQuery = "SELECT password, credential FROM users WHERE login = ?";
	private static final String getConditionQuery = "SELECT " + lang
			+ "_decryption FROM weather_conditions WHERE symbol = ?";
	private static final String addClothingQuery = "INSERT INTO outfits (clothing, temp_max, temp_min) VALUES (?,?,?)";
	private static final String deleteOutfit = "DELETE FROM outfits WHERE id = ?";

	public static ArrayList<Outfit> getClothing() {
		ArrayList<Outfit> outfits = new ArrayList<Outfit>();
		try (Connection connection = DatabaseConnection.getConnection();
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery(getOutfitsQuery);
			while (result.next()) {
				Outfit outfit = new Outfit();
				outfit.setId(result.getInt(1));
				outfit.setClothing(result.getString(2));
				outfit.setMaxTemperature(result.getInt(3));
				outfit.setMinTemperature(result.getInt(4));
				outfits.add(outfit);
			}
		} catch (SQLException e) {
			log.error(e);
		}
		return outfits;
	}

	public static Outfit getClothingById(int id) {
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement prStatement = connection.prepareStatement(getOutfitQueryById)) {
			prStatement.setInt(1, id);
			ResultSet result = prStatement.executeQuery();
			while (result.next()) {
				String clothing = result.getString(1);
				int max = result.getInt(2);
				int min = result.getInt(3);
				Outfit outfit = new Outfit(max, min, clothing);
				return outfit;
			}
		} catch (SQLException e) {
			log.error(e);
		}
		return null;
	}

	public static void addClothing(Outfit outfit) {
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement prStatement = connection.prepareStatement(addClothingQuery)) {
			prStatement.setString(1, outfit.getClothing());
			prStatement.setInt(2, outfit.getMaxTemperature());
			prStatement.setInt(3, outfit.getMinTemperature());
			prStatement.execute();
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public static void deleteOutfit(int id) {
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement prStatement = connection.prepareStatement(deleteOutfit)) {
			prStatement.setInt(1, id);
			String outfit = getClothingById(id).toString();
			if (prStatement.executeUpdate() == 1) {
				log.info("deleted outfit: " + outfit);
			}
		} catch (SQLException e) {
			log.error(e);
		}
	}

	/**
	 * This method receives the string with the login and the array of chars with
	 * the password and returns the authenticated user or null, if the login of
	 * password was wrong
	 * 
	 * @param {@code String} login
	 * @param {@code char[]} passExpected
	 * @exception SQLException
	 * @return {@code UserAccount} user
	 * 
	 */
	public static UserAccount authenticateUser(String login, char[] passExpected) {
		UserAccount user = null;
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement prStatement = connection.prepareStatement(getPasswordQuery)) {
			prStatement.setString(1, login);
			ResultSet result = prStatement.executeQuery();
			while (result.next()) {
				char[] passActual = result.getString(1).toCharArray();
				String role = result.getString(2);
				if (Arrays.equals(passExpected, passActual)) {
					user = new UserAccount(login, CredentialRole.valueOf(role));
				}
			}
		} catch (SQLException e) {
			log.error(e);
		}
		return user;
	}

	/**
	 * This method receives the string with the login and the expected credential
	 * role and checks if actual credential role of the login matches with the
	 * expected credential role.
	 * 
	 * @param {@code String} login
	 * @param {@code UserAccount.CredentialRole} roleExpected
	 * @exception SQLException
	 * @return true if roleExpected matches with roleActual
	 * 
	 */

	public static String getDecryptionCondition(String symbol) {
		String decryption = null;
		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement prStatement = connection.prepareStatement(getConditionQuery)) {
			prStatement.setString(1, symbol);
			ResultSet result = prStatement.executeQuery();
			while (result.next()) {
				decryption = result.getString(1);
			}
		} catch (SQLException e) {
			log.error(e);
		}
		return decryption;
	}

}
