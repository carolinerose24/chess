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
import model.requests.RegisterRequest;
import model.responses.EmptyResponse;
import model.responses.UserResponse;
import service.ChessGeneralException;
import service.UnauthorizedException;
import service.UserService;
import spark.Request;
import spark.Response;

import java.net.HttpURLConnection;

public class LogoutHandler extends EventHandler{

  public LogoutHandler(AuthDAO authDAO) {
    super(authDAO);
  }

  @Override
  public Object handle(Request request, Response response) {
    String authToken = request.headers("Authorization");
    AuthRequest authReq = new AuthRequest(authToken);
    EmptyResponse result = getResponse(authDAO, authReq);
    if(result.success()){
      response.status(HttpURLConnection.HTTP_OK);
      return "{}";
    } else  {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("message", result.message());
      if (result.message().contains("auth")){
        response.status(HttpURLConnection.HTTP_UNAUTHORIZED); // 401 - unauthorized
      } else {
        response.status(HttpURLConnection.HTTP_INTERNAL_ERROR); // 500 - data access error
      }
      return jsonObject;
    }
  }

  private EmptyResponse getResponse(AuthDAO authDAO, AuthRequest req)  {

    try{
      new UserService(authDAO, userDAO).logout(req);
      return new EmptyResponse(true, "");
    } catch(DataAccessException e){
      return new EmptyResponse(false, "Error: Couldn't Logout");
    } catch(UnauthorizedException e){
      return new EmptyResponse(false, "Error: unauthorized");
    }
  }
}
