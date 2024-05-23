package service;

public class ChessGeneralException extends Exception{

  public ChessGeneralException(){}

  // for just a message
  public ChessGeneralException(String message){
    super(message);
  }

  // for throwing up the initial error
  public ChessGeneralException(Throwable cause){
    super(cause);
  }
}
