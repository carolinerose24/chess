package handler;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.requests.ClearRequest;
import service.ChessGeneralException;

public class CreateGameHandler extends EventHandler{
  public CreateGameHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
    super(authDAO, gameDAO, userDAO);
  }


  protected Object getResponse(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO, ClearRequest req, String authToken) throws ChessGeneralException {
    return null;
  }
}
