package service;

import chess.ChessGame;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import model.requests.AuthRequest;
import model.requests.CreateGameRequest;
import model.requests.JoinGameRequest;
import model.responses.CreateGameResponse;
import model.responses.ListGamesResponse;

import java.util.Collection;

public class GameService {

  private final AuthDAO authDAO;
  private final GameDAO gameDAO;

  public GameService(AuthDAO authDAO, GameDAO gameDAO){
    this.authDAO = authDAO;
    this.gameDAO = gameDAO;
  }


  public Integer createGame(CreateGameRequest req) throws BadRequestException, UnauthorizedException, DataAccessException {

    // if empty game name, throw bad request exception
    if(req.gameName() == null || req.gameName().isBlank()){
      throw new BadRequestException("Error: The Game Name was Blank");
    }

    // check validate auth token, else throw unAUTH exception
    AuthData auth = authDAO.getAuthToken(req.authToken());
    if(auth == null) throw new UnauthorizedException("Error: Invalid Credentials"); // no auth token in table like this

    return gameDAO.createGame(req.gameName());
  }


  public void joinGame(JoinGameRequest req) throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {
    // takes in an auth token, the game name, and which player color to add










  }



  public ListGamesResponse listGames(AuthRequest authReq){
    // just GameData or a collection of game data???? not sure how to handle more than I record/row

//    Collection<ChessGame> games =

    return null;
  }

}
