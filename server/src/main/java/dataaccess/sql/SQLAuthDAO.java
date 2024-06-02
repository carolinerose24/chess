package dataaccess.sql;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.AuthData;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAO {

  @Override
  public String createAndInsertAuthToken(String username) throws DataAccessException {



    return null;
  }

  @Override
  public void deleteAuthToken(String authToken) throws DataAccessException {

  }

  @Override
  public AuthData getAuthToken(String authToken) throws DataAccessException {
    return null;
  }

  @Override
  public void clear() throws DataAccessException {
    try (Connection conn = DatabaseManager.getConnection();
         var preparedStatement = conn.prepareStatement("DELETE FROM AuthData")) {
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error: Couldn't clear user data");
    }
  }
}
