package service;

public class UnauthorizedException extends ChessGeneralException {
  public UnauthorizedException(){}

  public UnauthorizedException(String message){
    super(message);
  }

  public UnauthorizedException(Throwable cause){
    super(cause);
  }
}
