package model.responses;

public class CreateGameResponse {
  private String gameID;

  public CreateGameResponse(String gameID) {
    this.gameID=gameID;
  }

  public String getGameID() {
    return gameID;
  }

  public void setGameID(String gameID) {
    this.gameID=gameID;
  }
}
