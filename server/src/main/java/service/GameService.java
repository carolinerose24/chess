package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.requests.ClearRequest;
import model.requests.CreateGameRequest;

public class GameService {


  public GameData createGame(CreateGameRequest req) throws ChessException{





//    try {
//      isAuthorized(authData.authToken());
//      if(gameData.gameName() == null) throw new BadRequestException("The game needs a name.");
//
//      GameData newGame = new GameData(0, null, null,
//              gameData.gameName(), new ChessGame());
//      // do i need to make gameID be something different for each??
//
//      newGame = GameDAO.createGame(newGame); // something like this???
//      return newGame;
//
//    } catch(DataAccessException e){
//      throw new ChessException(e);
//    }



    // go into game database, create a new game and give it that name
    // return the ID

    // not sure if gameName should be passed like that, but I think it is OK to pass a GameData object where just the
    // game name is initialized and everything else is null
    return null;
  }


  public void joinGame() throws ChessException{
    // takes in an auth token, the game name, and which player color to add

  }



  public GameData listGames(AuthData authdata){
    // just GameData or a collection of game data???? not sure how to handle more than I record/row
    return null;
  }


  private void isAuthorized(String authToken) throws ChessException{
    // if wrong, will throw an exception
    // if right, will just return to where it was called and keep going
    // I don't think that this needs to return anything
//
//    try {
//      AuthData authData = AuthDAO.getAuthToken(authToken);
//      // if it returns null, that means we didn't find anything there
//      if(authData == null) throw new UnauthorizedException("Error: unauthorized");
//    } catch (DataAccessException e){
//      throw new ChessException(e);
//    }
//

  }




}
