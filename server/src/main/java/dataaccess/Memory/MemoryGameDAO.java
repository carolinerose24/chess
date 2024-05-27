package dataaccess.Memory;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MemoryGameDAO implements GameDAO {


  private HashMap<Integer, GameData> gameTable = new HashMap<>();

  @Override
  public Integer createGame(String gameName) throws DataAccessException {
    // leaves throws DAE for now, will need it for later code

    int highestKey = 1;
    if(!gameTable.isEmpty()){ // if the table isn't empty, then we can loop through it
      highestKey = gameTable.keySet().stream().max(Integer::compare).orElseThrow() + 1;
    }

    GameData newGame = new GameData(highestKey, null, null,
            gameName, new ChessGame());

    gameTable.put(newGame.gameID(), newGame);
    return newGame.gameID();
  }

  @Override
  public GameData getGame(int gameID) {
    return gameTable.get(gameID); // will return null if there isn't a game of this ID

//    if(gameTable.get(gameID) != null) return gameTable.get(gameID);
//    return null; // just to check if it exists, return null if it doesn't exist
  }

  @Override
  public Collection<GameData> listGames() throws DataAccessException {
    return gameTable.values(); // throws DAE - never here, but maybe later in SQL DAO
  }

  @Override
  public void updateGame(GameData game) throws DataAccessException {
    // check if there is a game of this ID
    if(!gameTable.containsKey(game.gameID())) throw new DataAccessException("No game with this ID");

    // then check that they actually passed a non null ChessGame object
    if(game.game() == null) throw new DataAccessException("The Chess Game object is empty");

    // then remove the old map value, and re add it with the new game (with the move or updated player
    gameTable.remove(game.gameID());
    gameTable.put(game.gameID(), game);



    // are these actually bad request exceptions??????
  }

  @Override
  public void clear() throws DataAccessException {
    gameTable.clear(); // clears the hash map table
  }
}
