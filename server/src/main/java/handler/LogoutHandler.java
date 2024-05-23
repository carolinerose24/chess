package handler;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.requests.AuthRequest;
import model.requests.ClearRequest;
import model.requests.RegisterRequest;
import model.responses.EmptyResponse;
import model.responses.UserResponse;
import service.ChessGeneralException;
import service.UserService;
import spark.Request;
import spark.Response;

import java.net.HttpURLConnection;

public class LogoutHandler extends EventHandler{

  public LogoutHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
    super(authDAO, gameDAO, userDAO);
  }


  @Override
  public Object handle(Request request, Response response) throws ChessGeneralException {
    String authToken = request.headers("Authorization");
    AuthRequest authReq = new AuthRequest(authToken);

    EmptyResponse result = getResponse(authDAO, authReq);
    response.status(HttpURLConnection.HTTP_OK);

    Gson gson = new Gson();
    return gson.toJson(result);
  }

  private EmptyResponse getResponse(AuthDAO authDAO, AuthRequest req) throws ChessGeneralException {
    new UserService(authDAO, userDAO).logout(req);
    return new EmptyResponse();
  }



}
