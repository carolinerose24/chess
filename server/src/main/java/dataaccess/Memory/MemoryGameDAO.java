package dataaccess.Memory;

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
  public Integer createGame(GameData gameData) throws DataAccessException {
    // return a STRING OR return the GameData object???

    int highestKey = 1;
    if(!gameTable.isEmpty()){ // if the table isn't empty, then we can loop through it
      highestKey = gameTable.keySet().stream().max(Integer::compare).orElseThrow() + 1;
    }

    GameData newGame = new GameData(highestKey, gameData.whiteUsername(), gameData.blackUsername(),
            gameData.gameName(), gameData.game());
    gameTable.put(newGame.gameID(), newGame);
    return gameData.gameID();
  }

  @Override
  public GameData getGame(int gameID) throws DataAccessException {
    if(gameTable.get(gameID) != null) return gameTable.get(gameID);
    throw new DataAccessException("Game not found");
  }

  @Override
  public Collection<GameData> listGames() throws DataAccessException {
    return gameTable.values();
  }

  @Override
  public void updateGame(GameData game) throws DataAccessException {
    // check if there is a game of this ID
    if(!gameTable.containsKey(game.gameID())) throw new DataAccessException("No game with this ID");

    // then check that they actually passed a non null ChessGame object
    if(game.game() == null) throw new DataAccessException("The Chess Game object is empty");

    // then remove the old map value, and re add it with the new game
    gameTable.remove(game.gameID());
    gameTable.put(game.gameID(), game);
  }

  @Override
  public void clear() throws DataAccessException {
    gameTable.clear();
  }
}
