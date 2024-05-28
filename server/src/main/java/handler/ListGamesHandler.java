package handler;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.requests.AuthRequest;
import model.responses.ListGamesResponse;
import service.GameService;
import service.UnauthorizedException;
import spark.Request;
import spark.Response;

public class ListGamesHandler extends EventHandler{
  public ListGamesHandler(AuthDAO authDAO, GameDAO gameDAO) {
    super(authDAO, gameDAO);
  }

  @Override
  public Object handle(Request request, Response response) throws UnauthorizedException, DataAccessException {
    AuthRequest authReq = new AuthRequest(request.headers("Authorization"));
    ListGamesResponse listResponse = new GameService(authDAO, gameDAO).listGames(authReq);
    response.status(200);
    return new Gson().toJson(listResponse.game());
  }
}
