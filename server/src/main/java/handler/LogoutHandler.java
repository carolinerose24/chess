package handler;

import com.google.gson.JsonObject;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.requests.AuthRequest;
import service.UnauthorizedException;
import service.UserService;
import spark.Request;
import spark.Response;

public class LogoutHandler extends EventHandler{

  public LogoutHandler(AuthDAO authDAO) {
    super(authDAO);
  }

  @Override
  public Object handle(Request request, Response response) {
    String authToken = request.headers("Authorization");
    AuthRequest authReq = new AuthRequest(authToken);
    JsonObject jsonObject = new JsonObject();

    try{
      new UserService(authDAO, userDAO).logout(authReq);
      response.status(200);
      return "{}";

    } catch(DataAccessException e){
      response.status(500);
      jsonObject.addProperty("message", "Error: Couldn't Logout");
      return jsonObject;

    } catch(UnauthorizedException e){
      response.status(401);
      jsonObject.addProperty("message", "Error: unauthorized");
      return jsonObject;
    }
  }
}
