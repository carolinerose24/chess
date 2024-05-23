package server;

import dataaccess.AuthDAO;
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
        Spark.delete("/db", new ClearHandler(authDAO, gameDAO, userDAO));

        // SESSION - login and logout
        Spark.post("/session", new LoginHandler(authDAO, gameDAO, userDAO));
        Spark.delete("/session", new LogoutHandler(authDAO, gameDAO, userDAO));

        // USER - register
        Spark.post("/user", new RegisterHandler(authDAO, gameDAO, userDAO));

        // GAME - list games, create game, join game
        Spark.get("/game", new ListGamesHandler(authDAO, gameDAO, userDAO));
        Spark.post("/game", new CreateGameHandler(authDAO, gameDAO, userDAO));
        Spark.put("/game", new JoinGameHandler(authDAO, gameDAO, userDAO));


        // Handle the Exceptions here, like this?
        // am I missing any???? like do i need one for DataAccessException??????

        Spark.exception(BadRequestException.class, (exception, request, response) -> {
            response.status(400); // HTTP 400 Bad Request
            response.body(exception.getMessage()); // Send the exception message as the response body
        });

        Spark.exception(UnauthorizedException.class, (exception, request, response) -> {
            response.status(401);
            response.body(exception.getMessage());
        });

        Spark.exception(AlreadyTakenException.class, (exception, request, response) -> {
            response.status(403);
            response.body(exception.getMessage());
        });



        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
