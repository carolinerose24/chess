package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.*;

import dataaccess.sql.SQLAuthDAO;
import dataaccess.sql.SQLGameDAO;
import dataaccess.sql.SQLUserDAO;
import handler.*;
import service.AlreadyTakenException;
import service.BadRequestException;
import service.UnauthorizedException;
import spark.*;

public class Server {

    public static void main(String[] args) {
        new Server().run(8080);
    }


    public int run(int desiredPort) {
      Spark.port(desiredPort);

      Spark.staticFiles.location("web");

      // Register your endpoints and handle exceptions here.

      // change these to SQL DAO objects
//        AuthDAO authDAO = new MemoryAuthDAO();
//        GameDAO gameDAO = new MemoryGameDAO();
//        UserDAO userDAO = new MemoryUserDAO();


      UserDAO userDAO;
      AuthDAO authDAO;
      GameDAO gameDAO;

      try {
//        DatabaseManager.createDatabase();
        userDAO=new SQLUserDAO();
        authDAO=new SQLAuthDAO();
        gameDAO=new SQLGameDAO();

      } catch (DataAccessException e) {
        // not sure if i want to catch this here?????
//        throw new Exception("SOMETHING IS WRONG");
        throw new RuntimeException(e);
      }




      // DB - clear
      Spark.delete("/db", new ClearHandler(authDAO, gameDAO, userDAO));

      // SESSION - login and logout (auth and user)
      Spark.post("/session", new LoginHandler(authDAO, userDAO));
      Spark.delete("/session", new LogoutHandler(authDAO));

      // USER - register (auth and user)
      Spark.post("/user", new RegisterHandler(authDAO, userDAO));

      // GAME - list games, create game, join game (auth and game)
      Spark.get("/game", new ListGamesHandler(authDAO, gameDAO));
      Spark.post("/game", new CreateGameHandler(authDAO, gameDAO));
      Spark.put("/game", new JoinGameHandler(authDAO, gameDAO));


      // Handle the Exceptions here
      Spark.exception(BadRequestException.class, (exception, request, response) -> {
        response.status(400);
        response.body(new Gson().toJson(createJsonError("Error: Bad Request")));
      });

      Spark.exception(UnauthorizedException.class, (exception, request, response) -> {
        response.status(401); // unauthorized
        response.body(new Gson().toJson(createJsonError("Error: Unauthorized")));
      });

      Spark.exception(AlreadyTakenException.class, (exception, request, response) -> {
        response.status(403); // already taken
        response.body(new Gson().toJson(createJsonError("Error: Already Taken")));
      });

      Spark.exception(DataAccessException.class, (exception, request, response) -> {
        response.status(500); // Data Access Error
        response.body(new Gson().toJson(createJsonError("Error: Couldn't Perform Action")));
      });


      Spark.awaitInitialization();
      return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private static JsonObject createJsonError(String message) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", message);
        return jsonObject;
    }
}
