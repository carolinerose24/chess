package model.requests;

public class AuthRequest {
  private String authToken;

  public AuthRequest(String authToken){
    this.authToken = authToken;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }
}
