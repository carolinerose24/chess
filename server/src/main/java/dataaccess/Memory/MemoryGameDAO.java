package dataaccess.Memory;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

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
  }

  @Override
  public Collection<GameData> listGames() throws DataAccessException {
    return gameTable.values(); // throws DAE - never here, but maybe later in SQL DAO
  }

  @Override
  public void updateGame(GameData game) throws DataAccessException {
    gameTable.remove(game.gameID());
    gameTable.put(game.gameID(), game);
  }

  @Override
  public void clear() throws DataAccessException {
    gameTable.clear(); // clears the hash map table
  }
}
