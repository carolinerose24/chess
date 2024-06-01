package dataaccess.sql;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;

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

  }
}
