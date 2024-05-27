package handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.requests.ClearRequest;
import model.requests.LoginRequest;
import model.requests.RegisterRequest;
import model.responses.UserResponse;
import org.w3c.dom.html.HTMLParagraphElement;
import service.ChessGeneralException;
import service.UnauthorizedException;
import service.UserService;
import spark.Request;
import spark.Response;

import java.net.HttpURLConnection;

public class LoginHandler extends EventHandler{
  public LoginHandler(AuthDAO authDAO, UserDAO userDAO) {
    super(authDAO, userDAO);
  }


  @Override
  public Object handle(Request request, Response response)  {
    Gson gson = new Gson();
    LoginRequest loginReq = gson.fromJson(request.body(), LoginRequest.class);
    UserResponse result = getResponse(authDAO, userDAO, loginReq);
    JsonObject jsonObject = new JsonObject();

    if(result.success()){
      response.status(HttpURLConnection.HTTP_OK);
      jsonObject.addProperty("username", result.username());
      jsonObject.addProperty("authToken", result.authToken());
    } else {
      jsonObject.addProperty("message", result.message());
      // now check which type of error (like in register) to set the right status

      if(result.message().contains("auth")){
        response.status(HttpURLConnection.HTTP_UNAUTHORIZED); // 401 - unauthorized
      } else {
        response.status(HttpURLConnection.HTTP_INTERNAL_ERROR); // 500 - data access error
      }
    }
    return jsonObject;
  }

  private UserResponse getResponse(AuthDAO authDAO, UserDAO userDAO, LoginRequest req) {

    try{
      AuthData authData = new UserService(authDAO, userDAO).login(req);
      return new UserResponse(authData.username(), authData.authToken(), true, "");
    } catch(UnauthorizedException e) {
      return new UserResponse("", "", false, "Error: unauthorized");
    } catch(DataAccessException e){
      return new UserResponse("", "", false, "Error: Couldn't Login");
    }
  }
}
