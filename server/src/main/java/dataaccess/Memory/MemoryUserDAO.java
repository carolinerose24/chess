package dataaccess.Memory;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;
import service.AlreadyTakenException;

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
    String username = user.username();

    // if username is already in the table, throw an exception?? a userAlreadyFound exception????
    if(usersTable.containsKey(username)) throw new DataAccessException("Username is already taken"); //Error: already taken
    usersTable.put(username, user);
  }

  @Override
  public UserData getUser(String username) throws DataAccessException {
    if(usersTable.containsKey(username)) {
      return usersTable.get(username);
    } else {
      throw new DataAccessException("User doesn't exist");
    }
  }



  @Override
  public boolean isUsernameFree(String username){
    if(usersTable.containsKey(username)) return false;
    return true; // it doesn't contain it
  }

}
