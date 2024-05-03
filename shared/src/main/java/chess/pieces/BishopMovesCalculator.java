package chess.pieces;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalculator implements PieceMovesCalculator {
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

    ChessPiece currentPiece = board.getPiece(myPosition);
    int startingRow = myPosition.getRow();
    int startingCol = myPosition.getColumn();

    // declare something to hold the possible moves - what we will return
    Collection<ChessMove> possMoves = new ArrayList<>();


    // bishops can move all 4 diagonals as long as they are not blocked
    // blocked by their own piece, can't move there or further

    // blocked by other team piece, can go there but no further

    // blocked by EDGES OF THE BOARD


    // create a loop that does row+1, col-1 until it reaches an invalid place (off of the board)
    //create a var upleft for possible positions that are up and left of it

//    while()









    return List.of();
  }
}
