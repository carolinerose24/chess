package handler;

import chess.ChessGame;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class EventHandler<T> implements Route {

  // talk in a request and response?

//  public EventHandler(){} not sure if i need a constructor for this...



  @Override
  public Object handle(Request request, Response response) { // can throw an exception?????
    // stuff to override the handler here

    // GSON STUFF HERE??

    var serializer = new Gson();
//    // now get the auth token, and the body
    String authorizationToken = request.headers("Authorization");

    // now we need to get the type of command



    // things that they can pass in:
//    String username = request.queryParams("username");
//    String password = request.queryParams("password");
//    String email = request.queryParams("email");









//
//
//
//    // need to figure out what type of command it is sending up to handle
//
//    request.body(); // once we know what type of command it is, we know what it is sending us
//









//    var game = new ChessGame();
//
//// serialize to JSON
//    var json = serializer.toJson(game);
//
//// deserialize back to ChessGame
//    game = serializer.fromJson(json, ChessGame.class);


    return null;
  }




  // have a method for handle(req, resp)

  // override it in each other handler






}
