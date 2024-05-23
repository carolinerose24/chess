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
  
  public void clear(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) throws DataAccessException{

    // or just have it throw a data access exception???????
    authDAO.clear(); //just auth not memory
    gameDAO.clear();
    userDAO.clear();

//    try{
//      authDAO.clear(); //just auth not memory
//      gameDAO.clear();
//      userDAO.clear();
//    } catch(DataAccessException e){
//      throw new ChessException(e);
//    }


  }

}
