package dataaccess.Memory;

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
  public void deleteAuthToken(String authToken) throws DataAccessException {
    authTable.remove(authToken);
  }

  @Override
  public AuthData getAuthToken(String authToken) throws DataAccessException {
    return authTable.get(authToken); // returns null if not there?
  }

  @Override
  public void clear() throws DataAccessException {
    authTable.clear();
  }

}
