package handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.requests.RegisterRequest;
import model.responses.UserResponse;
import service.AlreadyTakenException;
import service.BadRequestException;
import service.UserService;
import spark.Request;
import spark.Response;

import java.net.HttpURLConnection;

public class RegisterHandler extends EventHandler{
  public RegisterHandler(AuthDAO authDAO,  UserDAO userDAO) {
    super(authDAO, userDAO);
  }

  @Override
  public Object handle(Request request, Response response)  {
    Gson gson = new Gson();
    RegisterRequest registerReq = gson.fromJson(request.body(), RegisterRequest.class);
    UserResponse result = getResponse(authDAO, userDAO, registerReq);

    JsonObject jsonObject = new JsonObject();
    if(result.success()){
      jsonObject.addProperty("username", result.username());
      jsonObject.addProperty("authToken", result.authToken());
      response.status(HttpURLConnection.HTTP_OK);
    } else {
      jsonObject.addProperty("message", result.message());
      if(result.message().contains("Empty Field")) {
        response.status(HttpURLConnection.HTTP_BAD_REQUEST); // 400 - bad request
      } else if(result.message().contains("Already Taken")){
        response.status(HttpURLConnection.HTTP_FORBIDDEN); // 403 - already taken
      } else
        response.status(HttpURLConnection.HTTP_INTERNAL_ERROR); // 500 - data access exception
      }
    return jsonObject;
    }

  private UserResponse getResponse(AuthDAO authDAO, UserDAO userDAO, RegisterRequest req)  {
    try{
      AuthData authData = new UserService(authDAO, userDAO).register(req);
      return new UserResponse(authData.username(), authData.authToken(), true, "");
    } catch(BadRequestException e) {
      return new UserResponse("", "", false, "Error: Empty Field");
    } catch(DataAccessException e) {
      return new UserResponse("", "", false, "Error: Couldn't Register User");
    } catch(AlreadyTakenException e) {
      return new UserResponse("", "", false, "Error: Username Already Taken");
    }
  }

}
