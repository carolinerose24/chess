package service;

public class UnauthorizedException extends ChessException{
  public UnauthorizedException(){}

  public UnauthorizedException(String message){
    super(message);
  }

  public UnauthorizedException(Throwable cause){
    super(cause);
  }
}
