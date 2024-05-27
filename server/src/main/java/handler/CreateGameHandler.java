package handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.requests.AuthRequest;
import model.requests.ClearRequest;
import model.requests.CreateGameRequest;
import model.requests.RegisterRequest;
import model.responses.CreateGameResponse;
import model.responses.EmptyResponse;
import model.responses.UserResponse;
import service.*;
import spark.Request;
import spark.Response;

import java.net.HttpURLConnection;

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


    CreateGameResponse result = getResponse(authDAO, gameDAO, createReq);
    JsonObject jsonObject = new JsonObject();

    if(result.success()){
      response.status(HttpURLConnection.HTTP_OK);
      jsonObject.addProperty("message", result.message());
    } else  {
      jsonObject.addProperty("message", result.message());

      if (result.message().contains("auth")){
        response.status(HttpURLConnection.HTTP_UNAUTHORIZED); // 401 - unauthorized
      } else if(result.message().contains("bad request")) {
        response.status(HttpURLConnection.HTTP_BAD_REQUEST); // 400 - bad request
      } else {
        response.status(HttpURLConnection.HTTP_INTERNAL_ERROR); // 500 - data access error
      }

    }

    return jsonObject;
  }

  protected CreateGameResponse getResponse(AuthDAO authDAO, GameDAO gameDAO,  CreateGameRequest req){
//    try{
//
//      String gameID = new GameService(authDAO, gameDAO).createGame(req);
//      return new CreateGameResponse( , true, "");
//
//
//
//      CreateGameResponse response = gameDAO.createGame(CreateGameRequest req)
//
//
//    } catch(BadRequestException e) {
//
//    } catch(UnauthorizedException e ){
//
//    } catch(DataAccessException e){
//
//    }



    return null;
  }
}
