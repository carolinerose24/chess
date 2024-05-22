package model.requests;

public class JoinGameRequest extends AuthRequest{

  private String playerColor;
  private int gameID;


  public JoinGameRequest(String authToken, String playerColor, int gameID) {
    super(authToken);
    this.playerColor = playerColor;
    this.gameID = gameID;
  }

  public int getGameID() {
    return gameID;
  }

  public void setGameID(int gameID) {
    this.gameID=gameID;
  }

  public String getPlayerColor() {
    return playerColor;
  }

  public void setPlayerColor(String playerColor) {
    this.playerColor=playerColor;
  }
}
