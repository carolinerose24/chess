package handler;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.responses.EmptyResponse;
import service.ChessGeneralException;
import service.ClearService;
import spark.Request;
import spark.Response;

import java.net.HttpURLConnection;

public class ClearHandler extends EventHandler {


  public ClearHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
    super(authDAO, gameDAO, userDAO);

  }

  @Override
  public Object handle(Request request, Response response) throws ChessGeneralException {
    Gson gson = new Gson();
//    ClearRequest clearReq = gson.fromJson(request.body(), ClearRequest.class);
    EmptyResponse result = getResponse(authDAO, gameDAO, userDAO);
    response.status(HttpURLConnection.HTTP_OK);
    return gson.toJson(result);
  }

  private EmptyResponse getResponse(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) throws ChessGeneralException {
    new ClearService(authDAO, gameDAO, userDAO).clear();
    return new EmptyResponse();
  }

}
