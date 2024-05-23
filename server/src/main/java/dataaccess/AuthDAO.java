package dataaccess;

import dataaccess.DataAccessException;
import model.AuthData;

public interface AuthDAO {

  // methods to clear, insert an auth, find an auth, and delete auth
  // If they can't access the database, they should throw an exception right??

  // create an auth
  void createAuthToken(AuthData authData) throws DataAccessException;

  // delete an Auth
  void deleteAuthToken(String authToken) throws DataAccessException;

  // see if there is an auth token of this string in the db
  AuthData getAuthToken(String authToken) throws DataAccessException;


  // clear method: doesn't return anything I think
  void clear() throws DataAccessException;

}
