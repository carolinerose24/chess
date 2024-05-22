package model.responses;

import chess.ChessGame;

import java.util.ArrayList;

public class ListGamesResponse {

  private ArrayList<ChessGame> games;

  public ListGamesResponse(ArrayList<ChessGame> games) {
    this.games=games;
  }
}
