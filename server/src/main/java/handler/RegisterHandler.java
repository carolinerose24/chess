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
  public Object handle(Request request, Response response)  {
    Gson gson = new Gson();
    RegisterRequest registerReq = gson.fromJson(request.body(), RegisterRequest.class);
    JsonObject jsonObject = new JsonObject();

    try{
      AuthData authData = new UserService(authDAO, userDAO).register(registerReq);
      response.status(200);
      jsonObject.addProperty("username", authData.username());
      jsonObject.addProperty("authToken", authData.authToken());

    } catch(BadRequestException e) {
      response.status(400);
      jsonObject.addProperty("message", "Error: Bad Request - Empty Field");

    } catch(AlreadyTakenException e) {
      response.status(403);
      jsonObject.addProperty("message", "Error: Already Taken Username");

    } catch(DataAccessException e) {
      response.status(500);
      jsonObject.addProperty("message", "Error: Couldn't Register User");
    }

    return jsonObject;
    }
}
