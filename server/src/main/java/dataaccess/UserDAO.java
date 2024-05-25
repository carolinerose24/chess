package dataaccess;

import model.UserData;

import javax.xml.crypto.Data;

public interface UserDAO {
  // User needs to have these methods:

  // create user, doesn't need to return anything
  void createUser(UserData user) throws DataAccessException;

  // get the UserData from the username
  UserData getUser(String username);

  // maybe some more methods like verify user or see if the username is already in the database??
  // return like booleans

  boolean isUsernameFree(String username) throws DataAccessException;


  // same as the others
  void clear() throws DataAccessException;

}
