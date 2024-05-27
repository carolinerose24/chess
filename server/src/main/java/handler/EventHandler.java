package handler;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import service.ChessGeneralException;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class EventHandler implements Route {

  // These data members will be inherited by the other handlers
  protected AuthDAO authDAO;
  protected GameDAO gameDAO;
  protected UserDAO userDAO;

  public EventHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
    this.authDAO = authDAO;
    this.gameDAO = gameDAO;
    this.userDAO = userDAO;
  }

  public EventHandler(AuthDAO authDAO, UserDAO userDAO){
    this.authDAO = authDAO;
    this.userDAO = userDAO;
  } // USE this for the register/login stuff

  public EventHandler(AuthDAO authDAO, GameDAO gameDAO){
    this.authDAO = authDAO;
    this.gameDAO = gameDAO;
  } // use this for the game ones??

  public EventHandler(AuthDAO authDAO){
    this.authDAO = authDAO;
  } // logout


  @Override
  public Object handle(Request request, Response response)  {
    return null;
  }
}
