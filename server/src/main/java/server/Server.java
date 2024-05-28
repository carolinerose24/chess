package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.Memory.MemoryAuthDAO;
import dataaccess.Memory.MemoryGameDAO;
import dataaccess.Memory.MemoryUserDAO;
import dataaccess.UserDAO;
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

        // SOME OF THE HANDLERS WILL NEED PARAMETERS???
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        UserDAO userDAO = new MemoryUserDAO();


        // pass these through where needed ---> just pass everything right now because i made them
        // data members in the extended class, see if it works this way

        // DB - clear
        Spark.delete("/db", new ClearHandler(authDAO, gameDAO, userDAO)); // needs all 3

        // SESSION - login and logout
        Spark.post("/session", new LoginHandler(authDAO, userDAO)); // doesn't need GameDAO
        Spark.delete("/session", new LogoutHandler(authDAO)); // just auth

        // USER - register
        Spark.post("/user", new RegisterHandler(authDAO, userDAO)); // doesn't need GameDAO

        // GAME - list games, create game, join game
        Spark.get("/game", new ListGamesHandler(authDAO, gameDAO, userDAO));
        Spark.post("/game", new CreateGameHandler(authDAO, gameDAO)); // just auth and game
        Spark.put("/game", new JoinGameHandler(authDAO, gameDAO)); // just auth and game


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
