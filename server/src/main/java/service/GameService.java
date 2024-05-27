package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.requests.AuthRequest;
import model.requests.CreateGameRequest;
import model.requests.JoinGameRequest;
import model.responses.CreateGameResponse;
import model.responses.ListGamesResponse;

public class GameService {

  private final AuthDAO authDAO;
  private final GameDAO gameDAO;

  public GameService(AuthDAO authDAO, GameDAO gameDAO){
    this.authDAO = authDAO;
    this.gameDAO = gameDAO;
  }


  public CreateGameResponse createGame(CreateGameRequest req) throws BadRequestException, UnauthorizedException, DataAccessException {


    // if empty game name, throw bad request exception

    // check validate auth token, else throw unAUTH exception

    // call create game in memory game DAO
    // which needs to return the String GameID









//    try {
//      isAuthorized(authData.authToken());
//      if(gameData.gameName() == null) throw new BadRequestException("The game needs a name.");
//
//      GameData newGame = new GameData(1, null, null,
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


  public void joinGame(JoinGameRequest req) throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
    // takes in an auth token, the game name, and which player color to add

  }



  public ListGamesResponse listGames(AuthRequest authReq){
    // just GameData or a collection of game data???? not sure how to handle more than I record/row
    return null;
  }


  private void isAuthorized(String authToken) throws DataAccessException{
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
