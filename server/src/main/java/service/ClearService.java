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

  public void clear() throws ChessGeneralException {

    // or just have it throw a data access exception???????
//    authDAO.clear(); //just auth not memory
//    gameDAO.clear();
//    userDAO.clear();

    try{
      // try to clear all the data from EACH table
      authDAO.clear(); //just auth not memory
      gameDAO.clear();
      userDAO.clear();
    } catch(DataAccessException e){ // this turns the DataAccessException into a ChessException, which I can handle???
      throw new ChessGeneralException(e);
    }


  }

}
