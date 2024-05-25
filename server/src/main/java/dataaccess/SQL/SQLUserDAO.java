package dataaccess.SQL;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;

public class SQLUserDAO implements UserDAO {
  @Override
  public void createUser(UserData user) throws DataAccessException {

  }

  @Override
  public UserData getUser(String username) {
    return null;
  }

  @Override
  public boolean isUsernameFree(String username) throws DataAccessException {
    return false;
  }

  @Override
  public void clear() throws DataAccessException {

  }
}
