package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;

import javax.xml.crypto.Data;

public class GameService {


  public GameData listGames(AuthData authdata){
    // just GameData or a collection of game data???? not sure how to handle more than I record/row
    return null;
  }

  public GameData createGame(AuthData authData, GameData gameData) throws Exception{
    // takes in an AUTH token and a game name
    // returns the game ID if done correctly

    // check auth token is okay


    try {
      isAuthorized(authData.authToken());
      if(gameData.gameName() == null) throw new BadRequestException("The game needs a name.");

      GameData newGame = new GameData(0, null, null,
              gameData.gameName(), new ChessGame());
      // do i need to make gameID be something different for each??

      newGame = GameDAO.createGame(newGame); // something like this???
      return newGame;



    } catch(DataAccessException e){

    }





    isAuthorized(authData.authToken()); // throws an error if not

    // check that they passed in a real game name and it is not empty
    if(gameData.gameName() == null) throw new RuntimeException("The game needs a name"); // @@@ SHOULD THIS BE A SPECIFIC ONE??????

    GameData newGame = new GameData(1, null, null,
            gameData.gameName(), new ChessGame());

    newGame = GameDAO.createGame(newGame); // something like this???

    // this is where we make a new gameID, so do a random number??

    return newGame;


    // go into game database, create a new game and give it that name

    // return the ID








    // not sure if gameName should be passed like that, but I think it is OK to pass a GameData object where just the
    // game name is initialized and everything else is null
    return null;
  }

  public void clear(AuthData authData){

  }


  private void isAuthorized(String authToken){
    // if wrong, will throw an exception
    // if right, will just return to where it was called and keep going

    try {
      AuthData authData = AuthDAO.getAuthToken(authToken);
      // if it returns null
      if(authData == null) throw new UnauthorizedException("Error: unauthorized");


    } catch (DataAccessException e){

    }
  }









}
