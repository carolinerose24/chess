package handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.requests.LoginRequest;
import service.UnauthorizedException;
import service.UserService;
import spark.Request;
import spark.Response;

public class LoginHandler extends EventHandler{
  public LoginHandler(AuthDAO authDAO, UserDAO userDAO) {
    super(authDAO, userDAO);
  }


  @Override
  public Object handle(Request request, Response response) throws UnauthorizedException, DataAccessException {
    Gson gson = new Gson();
    LoginRequest loginReq = gson.fromJson(request.body(), LoginRequest.class);
    JsonObject jsonObject = new JsonObject();

    AuthData authData = new UserService(authDAO, userDAO).login(loginReq);
    response.status(200);
    jsonObject.addProperty("username", authData.username());
    jsonObject.addProperty("authToken", authData.authToken());

    return jsonObject;
  }
}
