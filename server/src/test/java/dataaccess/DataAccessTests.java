package dataaccess;

import dataaccess.memory.MemoryAuthDAO;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.memory.MemoryUserDAO;
import dataaccess.sql.SQLAuthDAO;
import dataaccess.sql.SQLGameDAO;
import dataaccess.sql.SQLUserDAO;
import model.UserData;
import model.requests.JoinGameRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.GameService;

public class DataAccessTests {

  UserDAO userDAO;
  AuthDAO authDAO;
  GameDAO gameDAO;

  public DataAccessTests(){
    try{
      userDAO = new SQLUserDAO();
      authDAO = new SQLAuthDAO();
      gameDAO = new SQLGameDAO();
    } catch(DataAccessException e){
      throw new RuntimeException("Error: Couldn't Setup Database");
    }
  }





  @Test
  @Order(1)
  @DisplayName("Clear Works")
  public void clearWorks() throws Exception {
    userDAO.createUser(new UserData("u1", "p1", "e1@a.com"));
    gameDAO.createGame("Clear Test Game 1");
    authDAO.createAndInsertAuthToken("u1");

    ClearService clearService = new ClearService(authDAO, gameDAO, userDAO);
    Assertions.assertDoesNotThrow(clearService::clear, "DataAccessException was thrown by Clear");
  }


//
//  @Test
//  @Order(2)
//  @DisplayName("Join Game Works")
//  public void joinGameWorks() throws Exception{
//
//    userDAO.createUser(new UserData("username1", "password1", "email1@a.com"));
//    String authToken = authDAO.createAndInsertAuthToken("username1");
//    int gameID1 = gameDAO.createGame("Game 100");
//    int gameID2 = gameDAO.createGame("Game 200");
//
//
//    GameService gameService = new GameService(authDAO, gameDAO);
//    Assertions.assertDoesNotThrow(() -> gameService.joinGame(new JoinGameRequest(authToken, "BLACK", gameID1)),
//            "Exception was thrown when joining a valid game as black");
//
//    Assertions.assertDoesNotThrow(() -> gameService.joinGame(new JoinGameRequest(authToken, "WHITE", gameID2)),
//            "Exception was thrown when joining a valid game as white");
//  }
}


