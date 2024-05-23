package handler;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.requests.ClearRequest;
import model.requests.LoginRequest;
import model.requests.RegisterRequest;
import model.responses.UserResponse;
import service.ChessGeneralException;
import service.UserService;
import spark.Request;
import spark.Response;

import java.net.HttpURLConnection;

public class LoginHandler extends EventHandler{
  public LoginHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
    super(authDAO, gameDAO, userDAO);
  }


  @Override
  public Object handle(Request request, Response response) throws ChessGeneralException {
    Gson gson = new Gson();
    LoginRequest loginReq = gson.fromJson(request.body(), LoginRequest.class);
    UserResponse result = getResponse(authDAO, userDAO, loginReq);
    response.status(HttpURLConnection.HTTP_OK);
    return gson.toJson(result);
  }

  private UserResponse getResponse(AuthDAO authDAO, UserDAO userDAO, LoginRequest req) throws ChessGeneralException {
    AuthData authData = new UserService(authDAO, userDAO).login(req);
    return new UserResponse(authData.username(), authData.authToken());
  }
}
