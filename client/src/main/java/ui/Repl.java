package ui;

import facade.ServerFacade;
import model.GameData;
import model.requests.*;
import model.responses.CreateGameResponse;
import model.responses.ListGamesResponse;
import model.responses.UserResponse;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Repl {

  private boolean quit = false;
  private boolean loggedIn = false;
  private boolean inPlayMode = false;

  private ServerFacade facade;

  // do i need to keep data members for things like current username?? to keep track of the current user???
  private String loggedInUsername;
  private String currentAuthToken;

  private boolean hasListedGames = false;
  private HashMap<Integer, GameData> gamesMap;

  public Repl(){
    facade = new ServerFacade("http://localhost:8080");
  }


  // write a main method inside of this class??
  // or call this method from the main class?

  public void runMenus() {

    Scanner scanner = new Scanner(System.in);
    // start a while loop
    while(!quit){
      // throw the first menu up, return what

      if(!loggedIn){ // IF NOT LOGGED IN, CALL THE FIRST MENU
        firstMenu(scanner);
        // this menu can set logged in to TRUE
      }

      //if logged in, will stay on second menu
      while(loggedIn){

        // call second menu HERE
        secondMenu(scanner);

        // can enter gameplay mode from here, but don't write this code out yet...
        if(inPlayMode){
          // this is where we will do stuff in phase 6
          // for now, just print out the chess board
          DrawBoard board = new DrawBoard();
          inPlayMode = false;
        }

      }

      // check that they didn't type quit, if so then set quit = true
    }
    scanner.close();
  }

  // method for 1st menu
  private void firstMenu(Scanner scanner){

    // print the 4 options, read in the response
    System.out.println();
    System.out.println("Type in the number corresponding to the desired option");
    System.out.println("1 - Register a new user");
    System.out.println("2 - Login an existing user");
    System.out.println("3 - Display help text");
    System.out.println("4 - Quit the program");

    String input = scanner.nextLine();
    switch (input) {
      case "1":
        registerNewUser(scanner);
        break;
      case "2":
        loginExistingUser(scanner);
        break;
      case "3":
        displayHelpTextMenuOne();
        break;
      case "4":
        System.out.println("Thanks for playing, quitting the program now.");
        quit = true;
        break;
      default:
        System.out.println("Invalid option. Please try again.");
    }
  }

  private void registerNewUser(Scanner scanner){

    System.out.print("Enter a new username: ");
    String username = scanner.nextLine();

    System.out.print("Enter a password: ");
    String password = scanner.nextLine();

    System.out.print("Enter your email: ");
    String email = scanner.nextLine();

    RegisterRequest req = new RegisterRequest(username, password, email);
    UserResponse res = facade.registerUser(req);

    if(res == null){
      System.out.println("Wasn't able to register a new user, please try again.");
    } else {
      loggedIn = true;
      loggedInUsername = username;
      currentAuthToken = res.authToken();
      System.out.println("Created user successfully, now logging in as: " + loggedInUsername);
    }
  }

  private void loginExistingUser(Scanner scanner){

    System.out.print("Enter existing username: ");
    String username = scanner.nextLine();

    System.out.print("Enter existing password: ");
    String password = scanner.nextLine();

    UserResponse res = facade.loginUser(new LoginRequest(username, password));

    if(res == null){
      System.out.println("Wasn't able to login with this username, please try again.");
    } else {
      loggedIn = true;
      loggedInUsername = username;
      currentAuthToken = res.authToken();
      System.out.println("Logged in successfully as: " + loggedInUsername);
    }
  }

  private void displayHelpTextMenuOne(){
    System.out.println("Displaying Help Text:");
    System.out.println("To access the chess server, you must first login or register.");
    System.out.println("To register a new user, you will be asked for a username, password, and email.");
    System.out.println("To login, you must provide your username and password.");
    System.out.println("If you wish to end the program, choose the quit option.");
  }


  private void secondMenu(Scanner scanner){

    // print the 4 options, read in the response
    System.out.println();
    System.out.println("Type in the number corresponding to the desired option");
    System.out.println("1 - Create a new chess game");
    System.out.println("2 - List all current games");
    System.out.println("3 - Join a game");
    System.out.println("4 - Observe a game");
    System.out.println("5 - Logout as user: " + loggedInUsername);
    System.out.println("6 - Display help text");


    String input = scanner.nextLine();

    switch (input) {
      case "1":
        createNewGame(scanner);
        break;
      case "2":
        listCurrentGames();
        hasListedGames = true;
        break;
      case "3":
        if(!hasListedGames){
          System.out.println("You need to print the list of game before you can join or observe one.");
        } else {
          if(joinGame(scanner)){
            inPlayMode = true;
          } //if it returns false, we do NOT enter gameplay mode
        }
        break;
      case "4":
        if(observeGame(scanner)){
          inPlayMode = true;
        }
        break;
      case "5":
        if(facade.logoutUser(new AuthRequest(currentAuthToken))){
          System.out.println("Logged out user: " + loggedInUsername);
          loggedIn = false;
        } else {
          System.out.println("Unable to logout at this time.");
        }
        break;
      case "6":
        displayHelpTextMenuTwo();
        break;
      default:
        System.out.println("Invalid option. Please try again.");
    }

  }

  private void createNewGame(Scanner scanner){

    System.out.println("Type in a name for your new chess game: ");
    String newGameName = scanner.nextLine();
    CreateGameResponse resp = facade.createGame(new CreateGameRequest(currentAuthToken, newGameName));
    if(resp == null){
      //there was some error
      System.out.println("Unable to create a new game at this time.");
    } else {
      System.out.println("Successfully created the game: " + newGameName);
    }
  }

  private void listCurrentGames(){

    ListGamesResponse resp = facade.listGames(new AuthRequest(currentAuthToken));

    if(resp == null){
      System.out.println("Couldn't print the game at this time.");
    } else {
      if(resp.game() == null || resp.game().isEmpty()){
        System.out.println("No current games to show.");
      } else {
        int counter = 1;
        gamesMap = new HashMap<>();
        for (GameData gameData : resp.game()) {
          System.out.println(counter + ". " + gameData.gameName());
          System.out.println("White Player: " + gameData.whiteUsername());
          System.out.println("Black Player: " + gameData.blackUsername());
          System.out.println();
          gamesMap.put(counter, gameData);
          counter = counter + 1;
        }

      }
    }

    // we need to create a numbering system
    // and show the game NAME + the players usernames

  }

  private boolean joinGame(Scanner scanner){

    if(printGamesList()){

      int gameToJoin;
      while (true) {
        System.out.println("Type the number of the game you want to join: ");
        String input = scanner.nextLine();

        try {
          gameToJoin = Integer.parseInt(input);
          if (gamesMap.containsKey(gameToJoin)) {
            break;
          } else {
            System.out.println("Invalid game number. Please try again.");
          }
        } catch (NumberFormatException e) {
          System.out.println("Invalid input. Please enter a valid number.");
        }
      }


//      String playerColor;
//      while (true) {
//        System.out.println("What color would you like to join this game as?");
//        System.out.println("Type B for BLACK or W for WHITE:");
//        playerColor = scanner.nextLine().trim();  // Trim to remove any leading/trailing whitespace
//
//        if (playerColor.equalsIgnoreCase("b") || playerColor.equalsIgnoreCase("w")) {
//          break;
//        } else {
//          System.out.println("Invalid input. Please type 'B' for BLACK or 'W' for WHITE.");
//        }
//      }

      System.out.println("What color would you like to join this game as?");
      System.out.println("Type BLACK or WHITE:");
      String playerColor = scanner.nextLine().trim();

      System.out.println("Attempting to join game " + gameToJoin + ": " + gamesMap.get(gameToJoin).gameName() + " as " + playerColor);
      System.out.println("...");

      boolean joinedGame = facade.joinGame(new JoinGameRequest(currentAuthToken, playerColor, gamesMap.get(gameToJoin).gameID()));
      if(joinedGame){
        System.out.println("Successfully joined " + gamesMap.get(gameToJoin).gameName()+ " as " + playerColor);
        return true;
      } else {
        System.out.println("Unable to join the game, please try again.");
        return false;
      }
    }
    // if it returns false, there are NO games
    return false;
  }

  private boolean observeGame(Scanner scanner){

    if(printGamesList()){
      int gameToJoin;
      while (true) {
        System.out.println("Type the number of the game you want to observe: ");
        String input = scanner.nextLine();

        try {
          gameToJoin = Integer.parseInt(input);
          if (gamesMap.containsKey(gameToJoin)) {
            break;
          } else {
            System.out.println("Invalid game number. Please try again.");
          }
        } catch (NumberFormatException e) {
          System.out.println("Invalid input. Please enter a valid number.");
        }
      }
    } // else there are no games to observe

    // will add functionality in phase 6???

    return true;
  }


  private void displayHelpTextMenuTwo(){
    System.out.println("Displaying Help Text");
    System.out.println("You are currently logged in as: " + loggedInUsername);
    System.out.println("To create a new chess game, you will be asked to name it.");
    System.out.println("To see all current game, choose list game.");
    System.out.println("To join or play a game, you must specify which game and which color.");
    System.out.println("To observer a game, type in the game number.");
    System.out.println("To return to the previous menu, choose logout.");
  }

  private boolean printGamesList(){
    if(gamesMap == null){
      System.out.println("There are no current games to join or observe.");
      return false;
    } else {
      System.out.println("Here is a list of the current games:");
      for (Map.Entry<Integer, GameData> entry : gamesMap.entrySet()) {
        System.out.println(entry.getKey() + ". " + entry.getValue().gameName());
      }
      return true;
    }
  }

  // method for 3rd menu (won't be fully fleshed out yet)

}
