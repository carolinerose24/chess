package handler;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import spark.Request;
import spark.Response;

public class ClearHandler extends EventHandler {

  public ClearHandler(){

  }

  public ClearHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
    super();
  }

  @Override
  public Object handle(Request request, Response response) {
    return super.handle(request, response);
  }
  
  // take in a clear request, eventually returns a clear handler @@@




}
