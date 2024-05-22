package server;

import handler.*;
import model.requests.ClearRequest;
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

        // DB - clear
        Spark.delete("/db", new ClearHandler());

        // SESSION - login and logout
        Spark.post("/session", new LoginHandler());
        Spark.delete("/session", new LogoutHandler());

        // USER - register
        Spark.post("/user", new RegisterHandler());

        // GAME - list games, create game, join game
        Spark.get("/game", new ListGamesHandler());
        Spark.post("/game", new CreateGameHandler());
        Spark.put("/game", new JoinGameHandler());





        // do I need something here to handle exceptions? does SPARK have something for that?






        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
