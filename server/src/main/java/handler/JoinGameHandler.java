package handler;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.requests.ClearRequest;
import service.ChessGeneralException;
import spark.Request;
import spark.Response;

public class JoinGameHandler extends EventHandler{
  public JoinGameHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
    super(authDAO, gameDAO, userDAO);
  }


  @Override
  public Object handle(Request request, Response response) {
    return super.handle(request, response);







  }

  protected Object getResponse(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO, ClearRequest req, String authToken) throws ChessGeneralException {
    return null;
  }
}
