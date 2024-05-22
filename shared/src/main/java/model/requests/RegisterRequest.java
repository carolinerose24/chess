package model.requests;

public class RegisterRequest extends LoginRequest{
  private String email;


  public RegisterRequest(String username, String password, String email) {
    super(username, password);
    this.email = email;
  }
}
