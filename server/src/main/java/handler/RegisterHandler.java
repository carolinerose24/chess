package handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.requests.RegisterRequest;
import service.AlreadyTakenException;
import service.BadRequestException;
import service.UserService;
import spark.Request;
import spark.Response;

public class RegisterHandler extends EventHandler{
  public RegisterHandler(AuthDAO authDAO,  UserDAO userDAO) {
    super(authDAO, userDAO);
  }

  @Override
  public Object handle(Request request, Response response) throws BadRequestException, AlreadyTakenException, DataAccessException{
    Gson gson = new Gson();
    RegisterRequest registerReq = gson.fromJson(request.body(), RegisterRequest.class);
    AuthData authData = new UserService(authDAO, userDAO).register(registerReq);
    response.status(200);
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("username", authData.username());
    jsonObject.addProperty("authToken", authData.authToken());
    return jsonObject;
  }

}
