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


  public CreateGameResponse createGame(CreateGameRequest req) throws BadRequestException, UnauthorizedException, DataAccessException {

    // if empty game name, throw bad request exception
    if(req.gameName() == null || req.gameName().isBlank()){
      throw new BadRequestException("Error: The Game Name was Blank");
    }

    // check validate auth token, else throw unAUTH exception
    AuthData auth = authDAO.getAuthToken(req.authToken());
    if(auth == null) throw new UnauthorizedException("Error: Invalid Credentials"); // no auth token in table like this

    return new CreateGameResponse(gameDAO.createGame(req.gameName()));
  }


  public void joinGame(JoinGameRequest req) throws BadRequestException, UnauthorizedException, AlreadyTakenException, DataAccessException {

    // check auth token, see if valid, or throw unAUTH
    AuthData auth = authDAO.getAuthToken(req.authToken());
    if(auth == null) throw new UnauthorizedException("Error: Invalid Credentials");
    String username = auth.username();

    // check gameID, if empty or not in database, throw badRequest
    GameData game = gameDAO.getGame(req.gameID());
    if(game == null) throw new BadRequestException("Error: Game Doesn't Exist"); // wrong gameID


    String playerColor = req.playerColor();
    // now check if that color is taken already
    if(playerColor != null && playerColor.equals("WHITE")){
      if(game.whiteUsername() != null) throw new AlreadyTakenException("Error: White is already taken");
      gameDAO.updateGame(new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game()));

    } else if(playerColor != null && playerColor.equals("BLACK")){
      if(game.blackUsername() != null) throw new AlreadyTakenException("Error: Black is already taken");
      gameDAO.updateGame(new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game()));

    } else { // if it is null or not B/W
      throw new BadRequestException("Error: Invalid Color"); // change this for observing the game??
    }

    // WILL THIS BE DIFFERENT FOR WHEN WE ARE JUST UPDATING THE GAME? AND NOT JOINING IT?

  }

  public ListGamesResponse listGames(AuthRequest req) throws UnauthorizedException, DataAccessException{
    AuthData auth = authDAO.getAuthToken(req.authToken());
    if(auth == null) throw new UnauthorizedException("Error: Invalid Credentials");
    return new ListGamesResponse(gameDAO.listGames());
  }

}
