package dataaccess;

import chess.ChessGame;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;

public interface GameDAO {

  // the methods that need to be called in here are one that manipulate the game database and CLEAR

  // create a new game from a name --> but pass it is as a full GameData object
  Integer createGame(GameData gameData) throws DataAccessException;

  // get game from ID
  GameData getGame(int gameID) throws DataAccessException;

  // return a list of all the games
  Collection<GameData> listGames() throws DataAccessException;

  // update the chess game from game ID
  void updateGame(GameData game) throws DataAccessException;


  // clear method: doesn't return anything
  void clear() throws DataAccessException;


}
