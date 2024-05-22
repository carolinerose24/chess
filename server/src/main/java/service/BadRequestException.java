package service;

public class BadRequestException extends Exception{
  public BadRequestException(){}

  public BadRequestException(String message) {
    super(message);
  }
}
