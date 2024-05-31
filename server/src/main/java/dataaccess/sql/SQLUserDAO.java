package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;

public class SQLUserDAO implements UserDAO {
  @Override
  public void createUser(UserData user) throws DataAccessException {

    // like the createPet method in PetShop

  }

  @Override
  public UserData getUser(String username) {
    return null;
  }

  @Override
  public void clear() throws DataAccessException {

  }
}
