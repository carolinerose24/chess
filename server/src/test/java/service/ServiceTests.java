package service;

import dataaccess.*;
import dataaccess.memory.*;
import model.UserData;
import model.requests.*;
import org.junit.jupiter.api.*;

public class ServiceTests {

  AuthDAO authDAO = new MemoryAuthDAO();
  GameDAO gameDAO = new MemoryGameDAO();
  UserDAO userDAO = new MemoryUserDAO();

  @Test
  @Order(1)
  @DisplayName("Clear Works")
  public void clearWorks() throws Exception {
    userDAO.createUser(new UserData("username1", "password1", "email1@a.com"));
    gameDAO.createGame("Charlie's Game");
    authDAO.createAndInsertAuthToken("username1");

    ClearService clearService = new ClearService(authDAO, gameDAO, userDAO);
    Assertions.assertDoesNotThrow(clearService::clear, "DataAccessException was thrown by Clear");
  }


  @Test
  @Order(2)
  @DisplayName("Register Works")
  public void registerWorks() throws Exception {

    UserService userService = new UserService(authDAO, userDAO);
    Assertions.assertDoesNotThrow(() -> userService.register(new RegisterRequest("a", "b", "c")),
            "Exception was thrown when adding first User");

    Assertions.assertDoesNotThrow(() -> userService.register(new RegisterRequest("b", "b", "c")),
            "Exception was thrown when adding second User");

    Assertions.assertDoesNotThrow(() -> userService.register(new RegisterRequest("c", "b", "c")),
            "Exception was thrown when adding third User");
  }


  @Test
  @Order(3)
  @DisplayName("Register Fails")
  public void registerFails() throws Exception {

    userDAO.createUser(new UserData("username1", "password1", "email1@a.com"));
    UserService userService = new UserService(authDAO, userDAO);
    Assertions.assertThrows(AlreadyTakenException.class, () -> userService.register(new RegisterRequest("username1", "a", "bb")),
            "Adding a user when the username is already taken, should throw an Already Taken Exception");

    Assertions.assertThrows(BadRequestException.class, () -> userService.register(new RegisterRequest("", "a", "bb")),
            "Empty Username Field, should throw a Bad Request Exception");
  }


  @Test
  @Order(4)
  @DisplayName("Login Works")
  public void loginWorks() throws Exception {
    userDAO.createUser(new UserData("username1", "password1", "email1@a.com"));
    UserService userService = new UserService(authDAO, userDAO);
    Assertions.assertDoesNotThrow(() -> userService.login(new LoginRequest("username1", "password1")),
            "Exception was thrown when logging in with valid user");
  }


  @Test
  @Order(5)
  @DisplayName("Login Fails")
  public void loginFails() throws Exception{
    userDAO.createUser(new UserData("username1", "password1", "email1@a.com"));
    UserService userService = new UserService(authDAO, userDAO);

    Assertions.assertThrows(UnauthorizedException.class, () -> userService.login(new LoginRequest("username1", "a")),
            "Bad Password, should throw Unauthorized Exception");

    Assertions.assertThrows(UnauthorizedException.class, () -> userService.login(new LoginRequest("user", "a")),
            "Bad username and password, should throw Unauthorized Exception");

  }


  @Test
  @Order(6)
  @DisplayName("Logout Works")
  public void logoutWorks() throws Exception{
    userDAO.createUser(new UserData("username1", "password1", "email1@a.com"));
    String authToken = authDAO.createAndInsertAuthToken("username1");
    UserService userService = new UserService(authDAO, userDAO);

    Assertions.assertDoesNotThrow(() -> userService.logout(new AuthRequest(authToken)),
            "Exception was thrown when logging out with valid user");
  }


  @Test
  @Order(7)
  @DisplayName("Logout Fails")
  public void logoutFails() throws Exception{
    userDAO.createUser(new UserData("username1", "password1", "email1@a.com"));
    authDAO.createAndInsertAuthToken("username1");
    UserService userService = new UserService(authDAO, userDAO);

    Assertions.assertThrows(UnauthorizedException.class, () -> userService.logout(new AuthRequest("bad auth token")),
            "Bad auth token, should throw Unauthorized Exception");
  }

  @Test
  @Order(8)
  @DisplayName("Create Game Works")
  public void createGameWorks() throws Exception{

    userDAO.createUser(new UserData("username1", "password1", "email1@a.com"));
    String authToken = authDAO.createAndInsertAuthToken("username1");
    GameService gameService = new GameService(authDAO, gameDAO);

    Assertions.assertDoesNotThrow(() -> gameService.createGame(new CreateGameRequest(authToken, "Charlie vs Adam")),
            "Exception was thrown when creating a valid game");
  }


  @Test
  @Order(9)
  @DisplayName("Create Game Fails")
  public void createGameFails() throws Exception{
    userDAO.createUser(new UserData("username1", "password1", "email1@a.com"));
    String authToken = authDAO.createAndInsertAuthToken("username1");
    GameService gameService = new GameService(authDAO, gameDAO);

    Assertions.assertThrows(UnauthorizedException.class, () -> gameService.createGame(new CreateGameRequest("bad auth token", "name")),
            "Bad auth token, should throw Unauthorized Exception");

    Assertions.assertThrows(BadRequestException.class, () -> gameService.createGame(new CreateGameRequest(authToken, " ")),
            "Blank Game Name, should throw Bad Request Exception");
  }

  @Test
  @Order(10)
  @DisplayName("Join Game Works")
  public void joinGameWorks() throws Exception{

    userDAO.createUser(new UserData("username1", "password1", "email1@a.com"));
    String authToken = authDAO.createAndInsertAuthToken("username1");
    int gameID1 = gameDAO.createGame("Game 100");
    int gameID2 = gameDAO.createGame("Game 200");


    GameService gameService = new GameService(authDAO, gameDAO);
    Assertions.assertDoesNotThrow(() -> gameService.joinGame(new JoinGameRequest(authToken, "BLACK", gameID1)),
            "Exception was thrown when joining a valid game as black");

    Assertions.assertDoesNotThrow(() -> gameService.joinGame(new JoinGameRequest(authToken, "WHITE", gameID2)),
            "Exception was thrown when joining a valid game as white");
  }


  @Test
  @Order(11)
  @DisplayName("Join Game Fails")
  public void joinGameFails() throws Exception{

    userDAO.createUser(new UserData("username1", "password1", "email1@a.com"));
    String authToken = authDAO.createAndInsertAuthToken("username1");

    userDAO.createUser(new UserData("username2", "password1", "email1@a.com"));
    String authToken2 = authDAO.createAndInsertAuthToken("username2");

    int gameID1 = gameDAO.createGame("Game 100");
    int gameID2 = gameDAO.createGame("Game 100");
    GameService gameService = new GameService(authDAO, gameDAO);

    Assertions.assertThrows(UnauthorizedException.class, () -> gameService.joinGame(new JoinGameRequest("Bad Auth Token", "WHITE", gameID1)),
            "Bad auth token, should throw Unauthorized Exception");

    Assertions.assertThrows(BadRequestException.class, () -> gameService.joinGame(new JoinGameRequest(authToken, "WHITE", 123456)),
            "Game doesn't exists, should throw Bad Request Exception");

    // Add a valid instance of white for gameID2 with user2
    Assertions.assertDoesNotThrow(() -> gameService.joinGame(new JoinGameRequest(authToken2, "WHITE", gameID2)),
            "Exception was thrown when joining a valid game as white");

    Assertions.assertThrows(AlreadyTakenException.class, () -> gameService.joinGame(new JoinGameRequest(authToken, "WHITE", gameID2)),
            "White is already taken, should throw Already Taken Exception");

  }

  @Test
  @Order(12)
  @DisplayName("List Game Works")
  public void listGameWorks() throws Exception{

    userDAO.createUser(new UserData("username1", "password1", "email1@a.com"));
    String authToken = authDAO.createAndInsertAuthToken("username1");
    gameDAO.createGame("Game 100");
    gameDAO.createGame("Game 200");
    GameService gameService = new GameService(authDAO, gameDAO);

    Assertions.assertDoesNotThrow(() -> gameService.listGames(new AuthRequest(authToken)),
            "Exception was thrown when listing games with valid auth token");
  }


  @Test
  @Order(13)
  @DisplayName("List Game Fails")
  public void listGameFails() throws Exception{

    userDAO.createUser(new UserData("username1", "password1", "email1@a.com"));
    String authToken = authDAO.createAndInsertAuthToken("username1");
    gameDAO.createGame("Game 100");
    gameDAO.createGame("Game 200");
    GameService gameService = new GameService(authDAO, gameDAO);

    Assertions.assertThrows(UnauthorizedException.class, () -> gameService.listGames(new AuthRequest("123")),
            "Bad auth token, should throw Unauthorized Exception");

  }

}
