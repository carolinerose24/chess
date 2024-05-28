package handler;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import spark.Request;
import spark.Response;

public class JoinGameHandler extends EventHandler{
  public JoinGameHandler(AuthDAO authDAO, GameDAO gameDAO) {
    super(authDAO, gameDAO);
  }


  @Override
  public Object handle(Request request, Response response) {










    return null;
  }
}
