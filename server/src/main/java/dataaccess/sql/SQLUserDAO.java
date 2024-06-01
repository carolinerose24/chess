package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.UserDAO;
import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


public class SQLUserDAO implements UserDAO {


//  private final String[] createUserTableStatement = {
//          """
//          CREATE TABLE IF NOT EXISTS UserData (
//            `username` VARCHAR(256) NOT NULL,
//            `password` VARCHAR(256) DEFAULT NULL,
//            `email` VARCHAR(256) DEFAULT NULL,
//            PRIMARY KEY (`username`),
//            INDEX (`email`)
//          );
//          """
//  };



//  public SQLUserDAO() throws DataAccessException{
//    configureDatabase();
//  }
//
//  private void configureDatabase() throws DataAccessException {
//    DatabaseManager.createDatabase();
//    try (var conn = DatabaseManager.getConnection()) {
//      for (var statement : createUserTableStatement) {
//        try (var preparedStatement = conn.prepareStatement(statement)) {
//          preparedStatement.executeUpdate();
//        }
//      }
//    } catch (SQLException e) {
//      throw new DataAccessException("Error: SQL exception");
//    }
//  }


//
//  private Connection conn;
//
//  public SQLUserDAO(Connection conn){
//    DatabaseManager.getConnection();
//  }
//




  @Override
  public void createUser(UserData user) throws DataAccessException {

    var statement = "INSERT INTO UserData (username, password, email) VALUES(?, ?, ?)";
    // like the createPet method in PetShop
    try (var preparedStatement = DatabaseManager.getConnection().prepareStatement(statement)) {
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
    try (var preparedStatement = DatabaseManager.getConnection().prepareStatement("SELECT username, password, email FROM UserData WHERE username=?")) {
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

  }
}
