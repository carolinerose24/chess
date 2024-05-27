package handler;

import com.google.gson.JsonObject;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import service.ClearService;
import spark.Request;
import spark.Response;



public class ClearHandler extends EventHandler {

  public ClearHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
    super(authDAO, gameDAO, userDAO);
  }

  @Override
  public Object handle(Request request, Response response) {

    try{
      new ClearService(authDAO, gameDAO, userDAO).clear();
      response.status(200);
      return "{}";

    } catch (DataAccessException e){
      response.status(500);
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("message", "Error: Couldn't Clear Database");
      return jsonObject;
    }
  }
}
