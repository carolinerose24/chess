package dataaccess;

import dataaccess.sql.SQLAuthDAO;
import dataaccess.sql.SQLGameDAO;
import dataaccess.sql.SQLUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import service.ClearService;

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

  // need a clear test for EACH DAO class???



  // AUTH DAO -
  //createAndInsertAuthToken
  //deleteAuthToken
  //getAuthToken
  //clear



  // GAME DAO -
  //createGame
  //getGame
  //listGames
  //updateGame
  //clear

  // USER DAO -
  //createUser
  //getUser
  //clear


  // AND THEN i need to work on reducing code duplication, because it is everywhere

}


