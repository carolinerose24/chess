package service;

public class ChessException extends Exception{

  public ChessException(){}

  // for just a message
  public ChessException(String message){
    super(message);
  }

  // for throwing up the initial error
  public ChessException(Throwable cause){
    super(cause);
  }
}
