package dataaccess.Memory;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;

public class MemoryAuthDAO implements AuthDAO {
  @Override
  public void createAuthToken() throws DataAccessException {

  }

  @Override
  public void deleteAuthToken() throws DataAccessException {

  }

  @Override
  public AuthData getAuthToken(String authToken) throws DataAccessException {
    return null;
  }

  @Override
  public void clear() throws DataAccessException {

  }
}
