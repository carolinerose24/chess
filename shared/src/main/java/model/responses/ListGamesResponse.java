package model.responses;

import chess.ChessGame;

import java.util.ArrayList;

public record ListGamesResponse(ArrayList<ChessGame> games) {
}
