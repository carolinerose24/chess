package service;

public class AlreadyTakenException extends ChessGeneralException {

  public AlreadyTakenException(){}

  public AlreadyTakenException(String message){
    super(message);
  }

  // for throwing up the initial error
  public AlreadyTakenException(Throwable cause){
    super(cause);
  }
}
