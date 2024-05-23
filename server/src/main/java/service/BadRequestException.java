package service;

public class BadRequestException extends ChessGeneralException {
  public BadRequestException(){}

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(Throwable cause){
    super(cause);
  }
}
