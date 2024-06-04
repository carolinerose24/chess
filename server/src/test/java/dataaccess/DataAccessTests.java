package dataaccess;

import dataaccess.sql.SQLAuthDAO;
import dataaccess.sql.SQLGameDAO;
import dataaccess.sql.SQLUserDAO;
import model.AuthData;
import model.UserData;
import model.requests.RegisterRequest;
import org.junit.jupiter.api.*;
import service.ClearService;
import service.UserService;

public class DataAccessTests {

  UserDAO userDAO;
  AuthDAO authDAO;
  GameDAO gameDAO;

  String authToken;
  UserData existingUser = new UserData("username1", "password1", "email1@a.com");

  public DataAccessTests(){
    try{
      userDAO = new SQLUserDAO();
      authDAO = new SQLAuthDAO();
      gameDAO = new SQLGameDAO();
    } catch(DataAccessException e){
      throw new RuntimeException("Error: Couldn't Setup Database");
    }
  }

  @BeforeEach
  public void setup() throws Exception {
    ClearService clearService = new ClearService(authDAO, gameDAO, userDAO);
    clearService.clear();

    UserService userService = new UserService(authDAO, userDAO);
    authToken = userService.register(new RegisterRequest(existingUser.username(),
            existingUser.password(), existingUser.email())).authToken();
  }


  @Test
  @Order(1)
  @DisplayName("Clear Works - USER")
  public void clearWorksUser() throws Exception {
    userDAO.createUser(new UserData("u1", "p1", "e1@a.com"));
    Assertions.assertDoesNotThrow(() -> userDAO.clear(), "DataAccessException was thrown by Clear");
  }

  @Test
  @Order(2)
  @DisplayName("Clear Works - GAME")
  public void clearWorksGame() throws Exception {
    gameDAO.createGame("Clear Test Game 1");
    Assertions.assertDoesNotThrow(() -> gameDAO.clear(), "DataAccessException was thrown by Clear");
  }

  @Test
  @Order(3)
  @DisplayName("Clear Works - AUTH")
  public void clearWorksAuth() throws Exception {
    userDAO.createUser(new UserData("u1", "p1", "e1@a.com"));
    authDAO.createAndInsertAuthToken("u1");
    Assertions.assertDoesNotThrow(() -> userDAO.clear(), "DataAccessException was thrown by Clear");
    Assertions.assertDoesNotThrow(() -> authDAO.clear(), "DataAccessException was thrown by Clear");
  }



  // USER DAO -
  //createUser
  //getUser
  //clear


  @Test
  @Order(4)
  @DisplayName("Create User - WORKS")
  public void createUserWorks() throws Exception{
    Assertions.assertDoesNotThrow(() -> userDAO.createUser(new UserData("a1", "123", "e@a.com")),
            "DataAccessException thrown creating a valid user");
  }

  @Test
  @Order(5)
  @DisplayName("Create User - FAILS")
  public void createUserFails() throws Exception{
    userDAO.createUser(new UserData("a1", "123", "e@a.com"));
    Assertions.assertThrows(DataAccessException.class, () -> userDAO.createUser(new UserData("a1", "123", "e@a.com")),
            "Create didn't throw a Data AE when putting in a duplicate user");
  }

  @Test
  @Order(6)
  @DisplayName("Get User - WORKS")
  public void getUserWorks() throws Exception{
    userDAO.createUser(new UserData("a1", "123", "e@a.com"));
    Assertions.assertNotNull(userDAO.getUser("a1"), "Didn't return valid user data");
  }

  @Test
  @Order(7)
  @DisplayName("Get User - FAILS")
  public void getUserFails() throws Exception{
    Assertions.assertNull(userDAO.getUser("u100"), "Didn't return null for invalid user");
    Assertions.assertNull(userDAO.getUser("    "), "Didn't return null for invalid user");
  }


  // AUTH DAO -
  //createAndInsertAuthToken
  //deleteAuthToken
  //getAuthToken
  //clear

  @Test
  @Order(8)
  @DisplayName("Create and Insert Auth - WORKS")
  public void createAuthWorks() throws Exception{
    userDAO.createUser(new UserData("userONE", "pass1", "email1"));
    Assertions.assertDoesNotThrow(() -> authDAO.createAndInsertAuthToken("userONE"),
            "DataAccessException thrown creating a valid user auth token");
  }

  @Test
  @Order(9)
  @DisplayName("Create and Insert Auth - FAILS")
  public void createAuthFails() throws Exception{
    Assertions.assertThrows(DataAccessException.class, () -> authDAO.createAndInsertAuthToken(""),
            "DataAccessException should be thrown when username is empty");
  }

  @Test
  @Order(10)
  @DisplayName("Delete Auth - WORKS")
  public void deleteAuthWorks() throws Exception{
    userDAO.createUser(new UserData("user1", "pass1", "email1"));
    String auth = authDAO.createAndInsertAuthToken("user1");
    Assertions.assertDoesNotThrow(() -> authDAO.deleteAuthToken(auth),
            "DataAccessException thrown when username is empty");

  }

  @Test
  @Order(11)
  @DisplayName("Delete Auth - FAILS")
  public void deleteAuthFails() throws Exception{
    Assertions.assertThrows(DataAccessException.class, () -> authDAO.deleteAuthToken(""),
            "DataAccessException should be thrown when auth token is invalid");
  }

  @Test
  @Order(12)
  @DisplayName("Get Auth - WORKS")
  public void getAuthWorks() throws Exception{
    userDAO.createUser(new UserData("user1", "pass1", "email1"));
    String auth = authDAO.createAndInsertAuthToken("user1");
    Assertions.assertEquals(authDAO.getAuthToken(auth), new AuthData(auth, "user1"),
            "Didn't get the right data from Auth Table");
  }

  @Test
  @Order(13)
  @DisplayName("Get Auth - FAILS")
  public void getAuthFails() throws Exception{
    Assertions.assertNull(authDAO.getAuthToken("fake auth"), "Should return null when fake auth");
  }



  // GAME DAO -
  //createGame
  //getGame
  //listGames
  //updateGame
  //clear


  



















//  @Test
//  @Order(1)
//  @DisplayName("Clear Works - ALL")
//  public void clearWorksALL() throws Exception {
//    userDAO.createUser(new UserData("u1", "p1", "e1@a.com"));
//    userDAO.createUser(new UserData("u2", "p1", "e1@a.com"));
//    userDAO.createUser(new UserData("u3", "p1", "e1@a.com"));
//    userDAO.createUser(new UserData("u4", "p1", "e1@a.com"));
//
//    gameDAO.createGame("Clear Test Game 1");
//    gameDAO.createGame("Clear Test Game 2");
//    gameDAO.createGame("Clear Test Game 3");
//    gameDAO.createGame("Clear Test Game 4");
//
//    authDAO.createAndInsertAuthToken("u1");
//    authDAO.createAndInsertAuthToken("u2");
//    authDAO.createAndInsertAuthToken("u3");
//    authDAO.createAndInsertAuthToken("u4");
//
//    ClearService clearService = new ClearService(authDAO, gameDAO, userDAO);
//    Assertions.assertDoesNotThrow(clearService::clear, "DataAccessException was thrown by Clear");
//  }

}


