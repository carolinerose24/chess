package ui;

import facade.ServerFacade;
import model.GameData;
import model.requests.AuthRequest;
import model.requests.CreateGameRequest;
import model.requests.LoginRequest;
import model.requests.RegisterRequest;
import model.responses.CreateGameResponse;
import model.responses.ListGamesResponse;
import model.responses.UserResponse;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Repl {

  private boolean quit = false;
  private boolean loggedIn = false;

  private ServerFacade facade;

  // do i need to keep data members for things like current username?? to keep track of the current user???
  private String loggedInUsername;
  private String currentAuthToken;

  private boolean hasListedGames = false;
  private HashMap<Integer, String> gamesMap;

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


  // method for 2nd menu

  private void secondMenu(Scanner scanner){

    // print the 4 options, read in the response
    System.out.println();
    System.out.println("Type in the number corresponding to the desired option");
    System.out.println("1 - Create a new chess game");
    System.out.println("2 - List all current game");
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

        // first check if there are any game to be joined, if not then tell them they need to create a game

        // if there are game, then print all the game names and a number by it??

        // then have them choose a game number
        // then say if they want to join as black or white
        // if that position is already taken, then


        // Type the number corresponding with the game you would like to join?


        // also need to print the chess board here @@@@


        printGamesList(); // this will print all current game



        if(!hasListedGames){ // REVISIT THIS LATER @@
          System.out.println("You need to print the list of game before you can join or observe one.");
        } else {
          joinGame(scanner);
        }
        break;
      case "4":
        observeGame(scanner);
        // also need to print the chess board here @@@@
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
      System.out.println("Successfully created the game with the following info: ");
      System.out.println("Game Name: " + newGameName);
      System.out.println("Game ID: " + resp.gameID());
    }
  }

  private void listCurrentGames(){

    ListGamesResponse resp = facade.listGames(new AuthRequest(currentAuthToken));

    if(resp == null){
      System.out.println("Couldn't print the game at this time.");
    } else {

//      System.out.println("It wasn't an error, it got to this point");

//      System.out.println(resp.game());

      if(resp.game() == null || resp.game().isEmpty()){
        System.out.println("No current game to show.");
      } else {

        // it is not empty, need to print everything here

        // have a global variable that is a map or list of game ID (that we just created??)

        int counter = 1;
        gamesMap = new HashMap<>();

        for (GameData gameData : resp.game()) {

          System.out.println("Game " + counter + ": ");
          System.out.println("Name: " + gameData.gameName());
          System.out.println("White Player: " + gameData.whiteUsername());
          System.out.println("Black Player: " + gameData.blackUsername());
          System.out.println();

          gamesMap.put(counter, gameData.gameName());
          counter = counter + 1;

          // Display other parts of the object as needed
        }

      }
    }




    // we need to create a numbering system
    // and show the game NAME + the players usernames

  }

  private void joinGame(Scanner scanner){

    System.out.println("What game would you like to join? Type in a number: ");
    String gameToJoin = scanner.nextLine();




  }

  private void observeGame(Scanner scanner){

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









  private void printGamesList(){
    if(gamesMap == null){
      System.out.println("There are no current game to join or observe.");
    } else {
      for (Map.Entry<Integer, String> entry : gamesMap.entrySet()) {
        System.out.println(entry.getKey() + ", Name: " + entry.getValue());
      }
    }
  }




  // method for 3rd menu (won't be fully fleshed out yet)

}
