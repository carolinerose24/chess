package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator implements PieceMovesCalculator{
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

    ChessPiece currentPiece = board.getPiece(myPosition);
    int startingRow = myPosition.getRow();
    int startingCol = myPosition.getColumn();


    ChessGame.TeamColor currentColor = currentPiece.getTeamColor();
    Collection<ChessMove> possMoves = new ArrayList<>();

    Collection<ChessMove> allKnightMoves = new ArrayList<>();
    allKnightMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow+2, startingCol+1), null));
    allKnightMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow+2, startingCol-1), null));

    allKnightMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow+1, startingCol+2), null));
    allKnightMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow+1, startingCol-2), null));

    allKnightMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow-2, startingCol+1), null));
    allKnightMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow-2, startingCol-1), null));

    allKnightMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow-1, startingCol+2), null));
    allKnightMoves.add(new ChessMove(myPosition, new ChessPosition(startingRow-1, startingCol-2), null));


    for(ChessMove move: allKnightMoves) {
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
