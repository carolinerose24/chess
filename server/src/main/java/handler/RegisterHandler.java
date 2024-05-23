package handler;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.requests.RegisterRequest;
import model.responses.UserResponse;
import service.ChessGeneralException;
import service.UserService;
import spark.Request;
import spark.Response;

import java.net.HttpURLConnection;

public class RegisterHandler extends EventHandler{
  public RegisterHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
    super(authDAO, gameDAO, userDAO);
  }

  @Override
  public Object handle(Request request, Response response) throws ChessGeneralException {
//    String authToken = request.headers("Authorization");
    // To use gson, I need to know what class the Request Object belongs to
    Gson gson = new Gson();
    RegisterRequest registerReq = gson.fromJson(request.body(), RegisterRequest.class);
    UserResponse result = getResponse(authDAO, userDAO, registerReq);
    response.status(HttpURLConnection.HTTP_OK);
    return gson.toJson(result);
  }

  private UserResponse getResponse(AuthDAO authDAO, UserDAO userDAO, RegisterRequest req) throws ChessGeneralException {
    AuthData authData = new UserService(authDAO, userDAO).register(req);
    return new UserResponse(authData.username(), authData.authToken());
  }

}
