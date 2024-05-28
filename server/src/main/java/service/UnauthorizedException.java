package service;

public class UnauthorizedException extends Exception {
  public UnauthorizedException(){}

  public UnauthorizedException(String message){
    super(message);
  }

  public UnauthorizedException(Throwable cause){
    super(cause);
  }
}
