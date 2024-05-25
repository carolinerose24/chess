package handler;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
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
  public Object handle(Request request, Response response) {
    Gson gson = new Gson();
//    ClearRequest clearReq = gson.fromJson(request.body(), ClearRequest.class);
    EmptyResponse result = getResponse(authDAO, gameDAO, userDAO);

    if(result.success()){
      response.status(HttpURLConnection.HTTP_OK);
    } else {
      response.status(HttpURLConnection.HTTP_INTERNAL_ERROR); // 500 is the only error for clear
      // need to get the right method from the service --> have the service catch the exceptions????????????
//      return gson.toJson(result); // AND CHANGE THIS to { "message": "Error: (description of error)" }
    }

    return gson.toJson(result);


  }

  private EmptyResponse getResponse(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {

    try{
      new ClearService(authDAO, gameDAO, userDAO).clear();
      return new EmptyResponse(true, "{}");
    } catch (DataAccessException e){ // should this be DataAccessException or just exception????
      return new EmptyResponse(false, "Error: Couldn't Clear Database");
    }



  }

}
