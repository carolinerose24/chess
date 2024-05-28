package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class ClearService {

  private AuthDAO authDAO;
  private GameDAO gameDAO;
  private UserDAO userDAO;

  public ClearService(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO){
    this.authDAO = authDAO;
    this.gameDAO = gameDAO;
    this.userDAO = userDAO;
  }

  public void clear() throws DataAccessException {
    authDAO.clear(); //just auth not memory or SQL
    gameDAO.clear();
    userDAO.clear();
  }
}
