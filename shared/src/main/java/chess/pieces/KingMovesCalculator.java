package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMovesCalculator implements PieceMovesCalculator {
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    ChessPiece currentPiece = board.getPiece(myPosition);

    int startingRow = myPosition.getRow();
    int startingCol = myPosition.getColumn();

    ChessGame.TeamColor currentColor = currentPiece.getTeamColor();
    Collection<ChessMove> possMoves = new ArrayList<>();

    Collection<ChessMove> allKingMoves = new ArrayList<>();
    allKingMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow+1, startingCol), null));
    allKingMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow+1, startingCol+1), null));
    allKingMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow, startingCol+1), null));
    allKingMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow-1, startingCol+1), null));

    allKingMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow-1, startingCol), null));
    allKingMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow-1, startingCol-1), null));
    allKingMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow, startingCol-1), null));
    allKingMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow+1, startingCol-1), null));


    for(ChessMove move: allKingMoves) {
      if(move.getEndPosition().checkValidPosition()){ //valid move (on the board)
        //now check if there is already a piece there
        if(board.getPiece(move.getEndPosition()) == null){ // valid and empty
          possMoves.add(new ChessMove(myPosition, move.getEndPosition(), null));

        } else if(board.getPiece(move.getEndPosition()) != null){
          // valid spot but a piece IS there, now check color of the piece
          if(currentColor != board.getPiece(move.getEndPosition()).getTeamColor()){ // different teams
            possMoves.add(new ChessMove(myPosition, move.getEndPosition(), null));
          }
        } //remove the BREAK
      }
    }
    return possMoves;
  }
}
