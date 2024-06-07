package ui;

import java.util.Scanner;

public class Repl {

  boolean quit = false;
  boolean loggedIn = false;

  // do i need to keep data members for things like current username?? to keep track of the current user???
  String loggedInUsername;


  // write a main method inside of this class??
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
        if(!registerNewUser(scanner)){
          //it wasn't able to register the user
          System.out.println("Wasn't able to register a new user, please try again.");
        } else {
          System.out.println("Created user successfully, now logging in:");
          loggedIn = true;
        }
        break;
      case "2":
        if(!loginExistingUser(scanner)){
          System.out.println("Wasn't able to login this user, please try again.");
        } else {
          System.out.println("Logged in successfully.");
          loggedIn = true;
        }
        // prompt for username and password
        // will need to check if valid credentials

        break;
      case "3":
        // just display some stuff?
        System.out.println("Displaying Help Text:");
        System.out.println("To access the chess server, you must first login or register.");
        System.out.println("To register a new user, you will be asked for a username, password, and email.");
        System.out.println("To login, you must provide your username and password.");
        System.out.println("If you wish to end the program, choose the quit option.");
        break;
      case "4":
        System.out.println("Thanks for playing, quitting the program now.");
        quit = true;
        break;
      default:
        System.out.println("Invalid option. Please try again.");
    }
  }



  private boolean registerNewUser(Scanner scanner){

    boolean validUsername = false;
    boolean validPassword = false;
    boolean validEmail = false;

    String username = null;
    String password = null;
    String email = null;


    System.out.print("Enter a new username: ");
    while(!validUsername){
      username = scanner.nextLine();
      if(username != null && !username.isBlank()){
        // is valid
        validUsername = true;
      } else {
        System.out.println("Invalid Username, it cannot be blank or white spaces only");
        System.out.println();
        System.out.print("Reenter username: ");
      }
    }

    System.out.print("Enter a new password: ");
    while(!validPassword){
      password = scanner.nextLine();
      if(password != null && !password.isBlank()){
        validPassword = true;
      } else {
        System.out.println("Invalid Password, it cannot be blank or white spaces only");
        System.out.println();
        System.out.print("Reenter password: ");
      }
    }

    System.out.print("Enter a new email: ");
    while(!validEmail){
      email = scanner.nextLine();
      if(email != null && !email.isBlank()){
        validEmail = true;
      } else {
        System.out.println("Invalid Email, it cannot be blank or white spaces only");
        System.out.println();
        System.out.print("Reenter email: ");
      }
    }



    // call server facade, try and have it add this new user
    // if it returns a username already taken error, then we need to tell the user that name is take
    if(ServerFacade.registerUser(username, password, email) == false){
      System.out.println("This username is already taken.");
      return false;
    } // OR COOULD RETURN A 500 message..., would need to handle that and say the SERVER couldn't do it....

    // if it doesn't return false, the operation worked
    loggedInUsername = username;
    return true;
  }

  private boolean loginExistingUser(Scanner scanner){

    System.out.print("Enter existing username: ");
    String username = scanner.nextLine();

    System.out.print("Enter existing password: ");
    String password = scanner.nextLine();

    // call server facade . login user
    if(ServerFacade.loginUser(username, password) == false){
      System.out.println("Invalid Login Credentials");
      return false;
      // this could mean the username was wrong OR the password was bad
    }

    loggedInUsername = username;
    return true;
  }




  // method for 2nd menu

  // method for 3rd menu (won't be fully fleshed out yet)





}
