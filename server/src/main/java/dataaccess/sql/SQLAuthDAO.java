package dataaccess.sql;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.AuthData;

import java.sql.*;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO {



  private final String createAuthTableStatement =
          """
            CREATE TABLE IF NOT EXISTS AuthData (
            `authToken` VARCHAR(256) NOT NULL,
            `username` VARCHAR(256) DEFAULT NULL,
            PRIMARY KEY (`authToken`),
            FOREIGN KEY (`username`) REFERENCES UserData(`username`)
                ON DELETE CASCADE
                ON UPDATE CASCADE
            )
            """
  ;

  public SQLAuthDAO() throws DataAccessException{
    makeTables();
  }

  private void makeTables() throws DataAccessException{
    try(var conn = DatabaseManager.getConnection();
        var preparedStatement = conn.prepareStatement(createAuthTableStatement)){
      preparedStatement.executeUpdate();
    } catch(SQLException e){
      throw new DataAccessException("Error: Couldn't create the Auth Table");
    }
  }




  @Override
  public String createAndInsertAuthToken(String username) throws DataAccessException {
    String authToken = UUID.randomUUID().toString();
    try (Connection conn = DatabaseManager.getConnection();
         var preparedStatement = conn.prepareStatement("INSERT INTO AuthData (authToken, username) VALUES (?, ?)")) {

      preparedStatement.setString(1, authToken);
      preparedStatement.setString(2, username);
      preparedStatement.executeUpdate();
      return authToken;
    } catch (SQLException e) {
      throw new DataAccessException("Error: Couldn't add new auth data");
    }
  }

  @Override
  public void deleteAuthToken(String authToken) throws DataAccessException {
    try (Connection conn = DatabaseManager.getConnection();
         var preparedStatement = conn.prepareStatement("DELETE FROM AuthData WHERE authToken = ?")) {
      preparedStatement.setString(1, authToken);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error: Couldn't Delete Auth Token");
    }
  }

  @Override
  public AuthData getAuthToken(String authToken) throws DataAccessException {

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement preparedStatement = conn.prepareStatement("SELECT username FROM AuthData WHERE authToken = ?")) {

      preparedStatement.setString(1, authToken);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return new AuthData(authToken, resultSet.getString("username"));
        } else {
          return null; // No row found with the specified authToken
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException("Error: Couldn't retrieve username by authToken");
    }
  }

  @Override
  public void clear() throws DataAccessException {
    try (Connection conn = DatabaseManager.getConnection();
         var preparedStatement = conn.prepareStatement("TRUNCATE TABLE AuthData")) {
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error: Couldn't clear auth data");
    }
  }
}
