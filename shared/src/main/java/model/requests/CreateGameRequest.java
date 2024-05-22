package model.requests;

public class CreateGameRequest extends AuthRequest{

  private String gameName;

  public CreateGameRequest(String authToken, String gameName) {
    super(authToken);
    this.gameName = gameName;
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName=gameName;
  }
}
