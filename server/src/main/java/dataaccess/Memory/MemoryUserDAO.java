package dataaccess.Memory;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;

public class MemoryUserDAO implements UserDAO {
  @Override
  public void createUser(UserData user) throws DataAccessException {

  }

  @Override
  public UserData getUser(String username) throws DataAccessException {
    return null;
  }

  @Override
  public void clear() throws DataAccessException {

  }
}
