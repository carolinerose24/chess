package handler;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import service.ChessGeneralException;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class EventHandler implements Route {

  // These data members will be inherited? by the other handlers??
  // but not all handlers need all of them???
  protected AuthDAO authDAO;
  protected GameDAO gameDAO;
  protected UserDAO userDAO;

  public EventHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
    this.authDAO = authDAO;
    this.gameDAO = gameDAO;
    this.userDAO = userDAO;
  }


  @Override
  public Object handle(Request request, Response response) throws ChessGeneralException { // can throw an exception?????

    // get the auth token first, might be empty though...
//    String authToken = request.headers("Authorization");

    // To use gson, I need to know what class the Request Object belongs to
//    Gson gson = new Gson();
//
//    ClearRequest clearReq = gson.fromJson(request.body(), ClearRequest.class);
//    // I need to know what type of class to read it in as??
//
//
//    Object result = getResponse(authDAO, gameDAO, userDAO, clearReq, authToken);
////    EmptyResponse res = (EmptyResponse) result; // this seems bad to do
//
//    response.status(HttpURLConnection.HTTP_OK);
//
////    System.out.println(gson.toJson(res));
//
//    return gson.toJson(result);

    // GSON STUFF HERE
    // read in request json to object
    // call the service
    // translate that back into json
    // response.set body = ____
    // still return response.body
    // empty --> use "" or "{}" or "null"

    return null;
  }

//  protected abstract Object getResponse(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO, ClearRequest req, String authToken) throws ChessException;
  // call this is handler, have it go to the overridden methods in the other Handlers

}
