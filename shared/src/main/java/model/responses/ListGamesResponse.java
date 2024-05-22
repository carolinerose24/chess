package model.responses;

import chess.ChessGame;

import java.util.ArrayList;

public record ListGamesResponse(ArrayList<ChessGame> game) {
  // I think this needs to be named as game, not games??
}
