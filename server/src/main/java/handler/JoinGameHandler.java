package handler;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.requests.JoinGameRequest;
import service.AlreadyTakenException;
import service.BadRequestException;
import service.GameService;
import service.UnauthorizedException;
import spark.Request;
import spark.Response;

public class JoinGameHandler extends EventHandler{
  public JoinGameHandler(AuthDAO authDAO, GameDAO gameDAO) {
    super(authDAO, gameDAO);
  }


  @Override
  public Object handle(Request request, Response response) throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

    Gson gson = new Gson();
    String authToken = request.headers("Authorization");
    String playerColor = gson.fromJson(request.body(), JoinGameRequest.class).playerColor();
    int gameID = gson.fromJson(request.body(), JoinGameRequest.class).gameID();
    JoinGameRequest joinReq = new JoinGameRequest(authToken, playerColor, gameID);

    new GameService(authDAO, gameDAO).joinGame(joinReq);
    response.status(200);
    return "{}";
  }
}
