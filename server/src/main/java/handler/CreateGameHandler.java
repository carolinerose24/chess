package handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.requests.CreateGameRequest;
import service.*;
import spark.Request;
import spark.Response;

public class CreateGameHandler extends EventHandler{
  public CreateGameHandler(AuthDAO authDAO, GameDAO gameDAO) {
    super(authDAO, gameDAO);
  }


  @Override
  public Object handle(Request request, Response response) throws BadRequestException, UnauthorizedException, DataAccessException {

    Gson gson = new Gson();
    String authToken = request.headers("Authorization");
    String gameName = gson.fromJson(request.body(), CreateGameRequest.class).gameName();
    CreateGameRequest createReq = new CreateGameRequest(authToken, gameName);
    JsonObject jsonObject = new JsonObject();

    int gameID = new GameService(authDAO, gameDAO).createGame(createReq);
    response.status(200);
    jsonObject.addProperty("gameID", gameID);
    return jsonObject;
  }
}
