package dataaccess.memory;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {

  private HashMap<String, AuthData> authTable = new HashMap<>();

  @Override
  public String createAndInsertAuthToken(String username) throws DataAccessException {
    String newAuth = UUID.randomUUID().toString();
    if(!authTable.containsKey(newAuth)) {
      authTable.put(newAuth, new AuthData(newAuth, username));
      return newAuth;
    }
    else {throw new DataAccessException("Auth Token already exists in table");} // should never happen???
  }

  @Override
  public void deleteAuthToken(String authToken)  {
    authTable.remove(authToken); // should do nothing if that auth token isn't in the table??
    // will return null if there wasn't that key to remove
  }

  @Override
  public AuthData getAuthToken(String authToken)  {
    return authTable.get(authToken); // returns null if not there?
  }

  @Override
  public void clear() throws DataAccessException {
    authTable.clear(); // this empties out the whole hashmap, or I could assign it to a new HashMap
    // but clearing it is easier
  }

}
