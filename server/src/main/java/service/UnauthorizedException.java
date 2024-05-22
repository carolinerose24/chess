package service;

public class UnauthorizedException extends Exception{
  public UnauthorizedException(){}

  public UnauthorizedException(String message){
    super(message);
  }

  // not sure if I need more in here...

}
