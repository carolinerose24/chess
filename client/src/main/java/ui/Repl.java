package ui;

import facade.ServerFacade;
import model.requests.AuthRequest;
import model.requests.CreateGameRequest;
import model.requests.LoginRequest;
import model.requests.RegisterRequest;
import model.responses.CreateGameResponse;
import model.responses.UserResponse;



import java.util.Scanner;

public class Repl {

  boolean quit = false;
  boolean loggedIn = false;

  ServerFacade facade;

  // do i need to keep data members for things like current username?? to keep track of the current user???
  String loggedInUsername;
  String currentAuthToken;

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



//
//  private void registerNewUser(Scanner scanner){
//
//    boolean validUsername = false;
//    boolean validPassword = false;
//    boolean validEmail = false;
//
//    String username = null;
//    String password = null;
//    String email = null;
//
//
//    System.out.print("Enter a new username: ");
//    while(!validUsername){
//      username = scanner.nextLine();
//      if(username != null && !username.isBlank()){
//        // is valid
//        validUsername = true;
//      } else {
//        System.out.println("Invalid Username, it cannot be blank or white space only");
//        System.out.println();
//        System.out.print("Reenter username: ");
//      }
//    }
//
//    System.out.print("Enter a new password: ");
//    while(!validPassword){
//      password = scanner.nextLine();
//      if(password != null && !password.isBlank()){
//        validPassword = true;
//      } else {
//        System.out.println("Invalid Password, it cannot be blank or white space only");
//        System.out.println();
//        System.out.print("Reenter password: ");
//      }
//    }
//
//    System.out.print("Enter a new email: ");
//    while(!validEmail){
//      email = scanner.nextLine();
//      if(email != null && !email.isBlank()){
//        validEmail = true;
//      } else {
//        System.out.println("Invalid Email, it cannot be blank or white space only");
//        System.out.println();
//        System.out.print("Reenter email: ");
//      }
//    }
//
//    RegisterRequest req = new RegisterRequest(username, password, email);
//    UserResponse res = facade.registerUser(req);
//
//    // won't have an auth token to return if it fails????
//
//    if(res == null){
//      System.out.println("Wasn't able to register a new user, please try again.");
//    } else {
//      loggedIn = true;
//      loggedInUsername = username;
//      currentAuthToken = res.authToken();
//      System.out.println("Created user successfully, now logging in as: " + loggedInUsername);
//    }
//  }

  private void loginExistingUser(Scanner scanner){

    System.out.print("Enter existing username: ");
    String username = scanner.nextLine();

    System.out.print("Enter existing password: ");
    String password = scanner.nextLine();

    UserResponse res = facade.loginUser(new LoginRequest(username, password));

    if(res.authToken().isBlank()){
      // if the operation fails
      System.out.println("This username or password was incorrect.");
      System.out.println("Wasn't able to login this user, please try again.");
      // will need to change this later....??

    } else {
      loggedIn = true;
      loggedInUsername = username;
      currentAuthToken = res.authToken();
      System.out.println("Logged in successfully as " + loggedInUsername);
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
    System.out.println("2 - List all current games");
    System.out.println("3 - Join a game");
    System.out.println("4 - Observe a game");
    System.out.println("5 - Logout as user: " + loggedInUsername);
    System.out.println("6 - Display help text");


    String input = scanner.nextLine();

    switch (input) {
      case "1":
        createNewGame(scanner);

        // I guess all of these have a change of returning a 500 error, how should I account for that??
        // like should they all return a boolean then????
        break;
      case "2":
        listCurrentGames();
        break;
      case "3":
        joinGame(scanner);
        break;
      case "4":
        observeGame(scanner);
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

//    CreateGameResponse res = facade.createGame(new CreateGameRequest(currentAuthToken, newGameName));
//
//    if(res.gameID())


  }

  private void listCurrentGames(){

    // how to handle if there are NO current games???

    // we need to create a numbering system
    // and show the game NAME + the players usernames


    // call server facade, if nothing is returned, then print that:
    System.out.println("No current games to show.");

  }

  private void joinGame(Scanner scanner){

  }

  private void observeGame(Scanner scanner){

  }


  private void displayHelpTextMenuTwo(){
    System.out.println("Displaying Help Text");
    System.out.println("You are currently logged in as: " + loggedInUsername);
    System.out.println("To create a new chess game, you will be asked to name it.");
    System.out.println("To see all current games, choose list games.");
    System.out.println("To join or play a game, you must specify which game and which color.");
    System.out.println("To observer a game, type in the game number.");
    System.out.println("To return to the previous menu, choose logout.");
  }










  // method for 3rd menu (won't be fully fleshed out yet)

}
