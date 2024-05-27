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
  public Object handle(Request request, Response response) {

    Gson gson = new Gson();
    String authToken = request.headers("Authorization");
    String gameName = gson.fromJson(request.body(), CreateGameRequest.class).gameName();
    CreateGameRequest createReq = new CreateGameRequest(authToken, gameName);
    JsonObject jsonObject = new JsonObject();

    try{
      int gameID = new GameService(authDAO, gameDAO).createGame(createReq);
      response.status(200);
      jsonObject.addProperty("gameID", gameID);

    } catch(BadRequestException e) {
      response.status(400);
      jsonObject.addProperty("message", "Error: Bad Request");

    } catch(UnauthorizedException e ){
      response.status(401);
      jsonObject.addProperty("message", "Error: unauthorized");

    } catch(DataAccessException e){
      response.status(500);
      jsonObject.addProperty("message", "Error: Couldn't Create Game");
    }
    return jsonObject;
  }
}
