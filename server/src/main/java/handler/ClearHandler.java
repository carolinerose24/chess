package handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.responses.EmptyResponse;
import service.ChessGeneralException;
import service.ClearService;
import spark.Request;
import spark.Response;

import java.net.HttpURLConnection;

public class ClearHandler extends EventHandler {

  public ClearHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
    super(authDAO, gameDAO, userDAO);
  }

  @Override
  public Object handle(Request request, Response response) {
    EmptyResponse result = getResponse(authDAO, gameDAO, userDAO);
    if(result.success()){
      response.status(HttpURLConnection.HTTP_OK);
    } else {
      response.status(HttpURLConnection.HTTP_INTERNAL_ERROR);
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("message", result.message());
      return jsonObject;
    }
    return "{}";
  }

  private EmptyResponse getResponse(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
    try{
      new ClearService(authDAO, gameDAO, userDAO).clear();
      return new EmptyResponse(true, "{}");
    } catch (DataAccessException e){
      return new EmptyResponse(false, "Error: Couldn't Clear Database");
    }
  }
}
