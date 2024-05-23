package dataaccess.Memory;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {

  private HashMap<String, AuthData> authTable = new HashMap<>();

  @Override
  public void createAuthToken(AuthData authData) throws DataAccessException {
//    String newAuth = UUID.randomUUID().toString();

    if(!authTable.containsKey(authData.authToken())) {authTable.put(authData.authToken(), authData);}
    else {throw new DataAccessException("Auth Token already exists in table");}
  }

  @Override
  public void deleteAuthToken(String authToken) throws DataAccessException {
    authTable.remove(authToken);
  }

  @Override
  public AuthData getAuthToken(String authToken) throws DataAccessException {
    return authTable.get(authToken); // returns empty if not there?
  }

  @Override
  public void clear() throws DataAccessException {
    authTable.clear();
  }

}
