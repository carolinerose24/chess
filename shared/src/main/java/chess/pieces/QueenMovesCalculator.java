package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueenMovesCalculator implements PieceMovesCalculator{


  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    Collection<ChessMove> allMoves = new ArrayList<>();

    BishopMovesCalculator bishopMovesCalculator = new BishopMovesCalculator();
    allMoves.addAll(bishopMovesCalculator.getBishopMovesOneWay(board, myPosition, 1, 1));
    allMoves.addAll(bishopMovesCalculator.getBishopMovesOneWay(board, myPosition, -1, 1));
    allMoves.addAll(bishopMovesCalculator.getBishopMovesOneWay(board, myPosition, -1, -1));
    allMoves.addAll(bishopMovesCalculator.getBishopMovesOneWay(board, myPosition, 1, -1));

    RookMovesCalculator rookMovesCalculator = new RookMovesCalculator();
    allMoves.addAll(rookMovesCalculator.getRookMovesVertical(board, myPosition, 1));
    allMoves.addAll(rookMovesCalculator.getRookMovesVertical(board, myPosition, -1));
    allMoves.addAll(rookMovesCalculator.getRookMovesHorizontal(board, myPosition, 1));
    allMoves.addAll(rookMovesCalculator.getRookMovesHorizontal(board, myPosition, -1));
    return allMoves;
  }
}
