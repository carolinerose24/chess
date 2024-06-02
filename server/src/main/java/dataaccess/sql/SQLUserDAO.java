package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.UserDAO;
import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;



public class SQLUserDAO implements UserDAO {

  @Override
  public void createUser(UserData user) throws DataAccessException {

    var statement = "INSERT INTO UserData (username, password, email) VALUES(?, ?, ?)";
    // like the createPet method in PetShop
    try (Connection conn = DatabaseManager.getConnection();
         var preparedStatement = conn.prepareStatement(statement)) {
      preparedStatement.setString(1, user.username());
      preparedStatement.setString(2, user.password());
      preparedStatement.setString(2, user.email());
      preparedStatement.executeUpdate();
    } catch(SQLException e){
      throw new DataAccessException("Error: Couldn't Add User");
    }

  }

  @Override
  public UserData getUser(String username) throws DataAccessException{
    try (Connection conn = DatabaseManager.getConnection();
         var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM UserData WHERE username=?")) {
      preparedStatement.setString(1, username);
      try (var rs = preparedStatement.executeQuery()) {
        if (rs.next()) {
          var password = rs.getString("password");
          var email = rs.getString("email");

          // Check if password or email is null
          if (password == null || email == null) {
            return null;
          }
          return new UserData(username, password, email);
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException("Error: Couldn't access user");
    }
    // Return null if no user is found
    return null;
  }

  @Override
  public void clear() throws DataAccessException {
    try (Connection conn = DatabaseManager.getConnection();
         var preparedStatement = conn.prepareStatement("DELETE FROM UserData")) {
         preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error: Couldn't clear user data");
    }
  }
}
