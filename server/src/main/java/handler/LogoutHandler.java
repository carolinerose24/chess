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
  public Object handle(Request request, Response response) throws UnauthorizedException, DataAccessException {
    String authToken = request.headers("Authorization");
    AuthRequest authReq = new AuthRequest(authToken);
    JsonObject jsonObject = new JsonObject();

    new UserService(authDAO, userDAO).logout(authReq);
    response.status(200);
    return "{}";
  }
}
