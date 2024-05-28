package dataaccess.Memory;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {

  // private HashMap to hold a table of usernames, passwords, and emails
  private HashMap<String, UserData> usersTable = new HashMap<>();

  @Override
  public void clear() throws DataAccessException {
    usersTable.clear();
  }


  @Override
  public void createUser(UserData user) throws DataAccessException {
    usersTable.put(user.username(), user);
  }

  @Override
  public UserData getUser(String username) {
    if(usersTable.containsKey(username)) {
      return usersTable.get(username);
    } else {
      return null;
    }
  }



  @Override
  public boolean isUsernameFree(String username){
    if(usersTable.containsKey(username)) return false;
    return true; // it doesn't contain it
  }

}
