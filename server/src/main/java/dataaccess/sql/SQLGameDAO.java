package dataaccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import model.GameData;
import model.requests.JoinGameRequest;
import service.GameService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQLGameDAO implements GameDAO {


  private final String createGameTableStatement =
          """
        CREATE TABLE IF NOT EXISTS GameData (
        `gameID` INT NOT NULL AUTO_INCREMENT,
        `whiteUsername` VARCHAR(256) DEFAULT NULL,
        `blackUsername` VARCHAR(256) DEFAULT NULL,
        `gameName` VARCHAR(256) NOT NULL,
        `game` LONGTEXT DEFAULT NULL,
        PRIMARY KEY (`gameID`)
        )
        """
  ;


  public SQLGameDAO() throws DataAccessException{
    makeTables();
  }

  private void makeTables() throws DataAccessException{
    try(var conn = DatabaseManager.getConnection();
        var preparedStatement = conn.prepareStatement(createGameTableStatement)){
      preparedStatement.executeUpdate();
    } catch(SQLException e){
      throw new DataAccessException("Error: Couldn't create the Game Table");
    }
  }


  @Override
  public Integer createGame(String gameName) throws DataAccessException {

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO GameData (gameName) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, gameName);
      preparedStatement.executeUpdate();

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          return generatedKeys.getInt(1);
        } else {
          throw new SQLException("Creating game failed, no ID obtained.");
        }
      }

    } catch (SQLException e) {
      throw new DataAccessException("Error: Couldn't add new game");
    }

  }

  @Override
  public GameData getGame(int gameID) throws DataAccessException {

    try (Connection conn = DatabaseManager.getConnection();
         var preparedStatement = conn.prepareStatement("SELECT * FROM GameData WHERE gameID = ?")) {

      preparedStatement.setInt(1, gameID);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          String whiteUsername = resultSet.getString("whiteUsername");
          String blackUsername = resultSet.getString("blackUsername");
          String gameName = resultSet.getString("gameName");
          ChessGame game = deserialize(resultSet.getString("game"));
          return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
        } else {
          return null; // No game with this ID, so return null
        }
      }
    } catch (SQLException e) {
      throw new DataAccessException("Error: Couldn't get game data");
    }
  }

  @Override
  public Collection<GameData> listGames() throws DataAccessException {
    Collection<GameData> gameList = new ArrayList<>();
    try (Connection connection = DatabaseManager.getConnection();
         var preparedStatement = connection.prepareStatement("SELECT * FROM GameData");
         ResultSet resultSet = preparedStatement.executeQuery()) {

      while (resultSet.next()) {
        int gameID = resultSet.getInt("gameID");
        String whiteUsername = resultSet.getString("whiteUsername");
        String blackUsername = resultSet.getString("blackUsername");
        String gameName = resultSet.getString("gameName");
        ChessGame game = deserialize(resultSet.getString("game"));

        GameData gameData = new GameData(gameID, whiteUsername, blackUsername, gameName, game);
        gameList.add(gameData);
      }
    } catch (SQLException e) {
      throw new DataAccessException("Error: Couldn't get game list");
    }
    return gameList;
  }

  @Override
  public void updateGame(GameData game) throws DataAccessException {
    String replaceSql = """
    REPLACE INTO GameData (gameID, whiteUsername, blackUsername, gameName, game) 
    VALUES (?, ?, ?, ?, ?)
    """;

    try (Connection conn = DatabaseManager.getConnection();
         var replaceStatement = conn.prepareStatement(replaceSql)) {
      replaceStatement.setInt(1, game.gameID());
      replaceStatement.setString(2, game.whiteUsername());
      replaceStatement.setString(3, game.blackUsername());
      replaceStatement.setString(4, game.gameName());
      replaceStatement.setString(5, serialize(game.game()));
      replaceStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error: Couldn't add or update game data");
    }
  }

  @Override
  public void clear() throws DataAccessException {
    try (Connection conn = DatabaseManager.getConnection();
         var preparedStatement = conn.prepareStatement("DELETE FROM GameData")) {
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error: Couldn't clear game data");
    }
  }


  // code for JSON to object - Deserialize
  private ChessGame deserialize(String json){
    Gson gson = new Gson();
    return gson.fromJson(json, ChessGame.class);
  }

  // code for Object to JSON - SERIALIZE
  private String serialize(ChessGame game){
    Gson gson = new Gson();
    return gson.toJson(game);
  }

}
