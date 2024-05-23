package service;

public class BadRequestException extends ChessException{
  public BadRequestException(){}

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(Throwable cause){
    super(cause);
  }
}
