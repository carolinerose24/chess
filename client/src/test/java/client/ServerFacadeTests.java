package client;

import facade.ServerFacade;
import model.UserData;
import model.requests.*;
import model.responses.CreateGameResponse;
import model.responses.ListGamesResponse;
import model.responses.UserResponse;
import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    String existingAuth;


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:8080");
    }

    @BeforeEach
    public void setup() {
        facade.clearServer(); //need to clear the database between each test

        //one user already logged in
        UserResponse userResponse = facade.registerUser(new RegisterRequest("user", "pass", "email"));
        existingAuth = userResponse.authToken();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    @DisplayName("Register Works")
    public void registerWorks() throws Exception {

        UserResponse userResp = facade.registerUser(new RegisterRequest("user1", "pass", "email"));
        String auth1 = userResp.authToken();
        assertTrue(auth1.length() > 10, "Auth token should be longer than 10 characters");
        assertEquals("user1", userResp.username(), "Should return the same username");
        Assertions.assertNotEquals(existingAuth, auth1, "Auth tokens should be different");
    }

    @Test
    @DisplayName("Register Fails")
    public void registerFails() throws Exception {
        facade.registerUser(new RegisterRequest("user1", "pass", "email"));
        Assertions.assertNull(facade.registerUser(new RegisterRequest("user1", "pass", "email")),
                "Username is already taken, should return null (because it threw an already taken exception)");

        Assertions.assertNull(facade.registerUser(new RegisterRequest("", "pass", "email")),
                "Empty field, should return null (because it threw an bad request exception)");
    }



    @Test
    @DisplayName("Login Works")
    public void loginWorks() throws Exception {

        // test that it makes a new auth token each login
        UserResponse resp = facade.loginUser(new LoginRequest("user", "pass"));
        assertTrue(!resp.authToken().equals(existingAuth), "Should make a new auth each time");

    }

    @Test
    @DisplayName("Login Fails")
    public void loginFails() throws Exception {

        Assertions.assertNull(facade.loginUser(new LoginRequest("user", "WRONG_PASSWORD")),
                "Logging in with the wrong password, should return null (because it threw an unauthorized exception)");
    }



    @Test
    @DisplayName("Logout Works")
    public void logoutWorks() throws Exception {

        Assertions.assertTrue(facade.logoutUser(new AuthRequest(existingAuth)),
                "A valid logout shouldn't throw an exception (return null)");

        Assertions.assertNull(facade.createGame(new CreateGameRequest(existingAuth, "new game")),
                "If there was a successful logout, this auth token should have been removed.");
    }

    @Test
    @DisplayName("Logout Fails")
    public void logoutFails() throws Exception {

        Assertions.assertFalse(facade.logoutUser(new AuthRequest("fake auth token")),
                "Fake auth token, should throw an exception (return false)");
    }



    @Test
    @DisplayName("Create Game Works")
    public void createGameWorks() throws Exception {

        facade.createGame(new CreateGameRequest(existingAuth, "New Game"));
        Assertions.assertNotNull(facade.createGame(new CreateGameRequest(existingAuth, "New Game")),
                "Should be able to create another game with the same name.");
    }

    @Test
    @DisplayName("Create Game Fails")
    public void createGameFails() throws Exception {
        Assertions.assertNull(facade.createGame(new CreateGameRequest(existingAuth, "")),
                "Empty field, should have thrown a bad request exception (returned null)");

        Assertions.assertNull(facade.createGame(new CreateGameRequest("fake_auth", "chess")),
                "Fake auth token, should be unauthorized and return null");
    }



    @Test
    @DisplayName("List Games Works")
    public void listGamesWorks() throws Exception {
        facade.createGame(new CreateGameRequest(existingAuth, "first"));
        facade.createGame(new CreateGameRequest(existingAuth, "second"));
        facade.createGame(new CreateGameRequest(existingAuth, "third"));

        ListGamesResponse resp = facade.listGames(new AuthRequest(existingAuth));
        Assertions.assertNotNull(resp, "There are games, the list shouldn't be null");
        Assertions.assertEquals(3, resp.game().size(), "Should have 3 games in there");
    }

    @Test
    @DisplayName("List Games Fails")
    public void listGamesFails() throws Exception {

        Assertions.assertTrue(facade.listGames(new AuthRequest(existingAuth)).game().isEmpty(),
                "Game list is empty, .isEmpty() should be true");

        facade.createGame(new CreateGameRequest(existingAuth, "first"));
        Assertions.assertNull(facade.listGames(new AuthRequest("fake_auth")),
                "Fake auth token, should be unauthorized to list games");
    }



    @Test
    @DisplayName("Join Game Works")
    public void joinGameWorks() throws Exception {

        CreateGameResponse resp = facade.createGame(new CreateGameRequest(existingAuth, "New Game"));
        facade.listGames(new AuthRequest(existingAuth));
        assertTrue(facade.joinGame(new JoinGameRequest(existingAuth,"WHITE", resp.gameID())),
                "Should be able to join this new game as white");
    }

    @Test
    @DisplayName("Join Game Fails")
    public void joinGameFails() throws Exception {

        CreateGameResponse resp = facade.createGame(new CreateGameRequest(existingAuth, "New Game"));
        facade.listGames(new AuthRequest(existingAuth));
        facade.joinGame(new JoinGameRequest(existingAuth, "WHITE", resp.gameID()));
        Assertions.assertFalse(facade.joinGame(new JoinGameRequest(existingAuth,"WHITE", resp.gameID())),
                "White is already taken, shouldn't be able to join");

        Assertions.assertFalse(facade.joinGame(new JoinGameRequest(existingAuth,"yellow", resp.gameID())),
                "Misspelled color name, should throw exception");

        Assertions.assertFalse(facade.joinGame(new JoinGameRequest("FAKE AUTH","BLACK", resp.gameID())),
                "Fake auth token, should throw exception because unauthorized");
    }


//
//    @Test
//    @DisplayName("Clear Works")
//    public void clearWorks() throws Exception {
//
//        facade.createGame(new CreateGameRequest(existingAuth, "First"));
//        facade.createGame(new CreateGameRequest(existingAuth, "Second"));
//        facade.createGame(new CreateGameRequest(existingAuth, "Third"));
//
//        facade.registerUser(new RegisterRequest("user1", "pass", "email"));
//        facade.registerUser(new RegisterRequest("user2", "pass", "email"));
//        facade.registerUser(new RegisterRequest("user3", "pass", "email"));
//
//        Assertions.assertDoesNotThrow(() -> facade.clearServer(), "Should be able to clear without issue");
//
//    }












}
