package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMovesCalculator implements PieceMovesCalculator {


  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

    ChessPiece currentPiece = board.getPiece(myPosition);
    int startingRow = myPosition.getRow();
    int startingCol = myPosition.getColumn();

    // declare something to hold the possible moves - what we will return
    Collection<ChessMove> possMoves = new ArrayList<>();


    // FOR BLACK PIECES
    if(currentPiece.getTeamColor() == ChessGame.TeamColor.BLACK){

      // FORWARD 1
      ChessPosition endPosition_forward1 = new ChessPosition(startingRow-1, startingCol);
      if (endPosition_forward1.checkValidPosition() && board.getPiece(endPosition_forward1) == null){
        if(endPosition_forward1.getRow() == 1){
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, ChessPiece.PieceType.QUEEN));
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, ChessPiece.PieceType.BISHOP));
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, ChessPiece.PieceType.KNIGHT));
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, ChessPiece.PieceType.ROOK));
        } else {
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, null));
        }
      }

      // FORWARD 2
      //check if they are on row 7 (haven't moved yet) AND if 2 spaces in front are empty
      ChessPosition endPosition_forward2 = new ChessPosition(startingRow-2, startingCol);
      if(startingRow == 7 && board.getPiece(endPosition_forward1) == null && board.getPiece(endPosition_forward2) == null){
        possMoves.add(new ChessMove(myPosition, endPosition_forward2, null));
      }

      // LEFT KILL (its left)
      ChessPosition endPosition_leftKill = new ChessPosition(startingRow-1, startingCol+1);
      if(endPosition_leftKill.checkValidPosition() && board.getPiece(endPosition_leftKill) != null && board.getPiece(endPosition_leftKill).getTeamColor() == ChessGame.TeamColor.WHITE){
        if(endPosition_leftKill.getRow() == 1){
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, ChessPiece.PieceType.QUEEN));
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, ChessPiece.PieceType.BISHOP));
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, ChessPiece.PieceType.KNIGHT));
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, ChessPiece.PieceType.ROOK));
        } else {
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, null));
        }
      }


      // RIGHT KILL
      ChessPosition endPosition_rightKill = new ChessPosition(startingRow-1, startingCol-1);
      if(endPosition_rightKill.checkValidPosition() && board.getPiece(endPosition_rightKill) != null && board.getPiece(endPosition_rightKill).getTeamColor() == ChessGame.TeamColor.WHITE){
        if(endPosition_rightKill.getRow() == 1){
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, ChessPiece.PieceType.QUEEN));
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, ChessPiece.PieceType.BISHOP));
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, ChessPiece.PieceType.KNIGHT));
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, ChessPiece.PieceType.ROOK));
        } else {
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, null));
        }
      }
    }


    // for WHITE pieces
    if(currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE){

      // FORWARD 1
      ChessPosition endPosition_forward1 = new ChessPosition(startingRow+1, startingCol); //1 row up
      if (endPosition_forward1.checkValidPosition() && board.getPiece(endPosition_forward1) == null){
        if(endPosition_forward1.getRow() == 8){
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, ChessPiece.PieceType.QUEEN));
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, ChessPiece.PieceType.BISHOP));
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, ChessPiece.PieceType.KNIGHT));
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, ChessPiece.PieceType.ROOK));
        } else {
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, null));
        }
      }

      // FORWARD 2
      ChessPosition endPosition_forward2 = new ChessPosition(startingRow+2, startingCol);
      if(startingRow == 2 && board.getPiece(endPosition_forward1) == null && board.getPiece(endPosition_forward2) == null){
        possMoves.add(new ChessMove(myPosition, endPosition_forward2, null));
      }

      // LEFT KILL (its left)
      ChessPosition endPosition_leftKill = new ChessPosition(startingRow+1, startingCol-1);
      if(endPosition_leftKill.checkValidPosition() && board.getPiece(endPosition_leftKill) != null && board.getPiece(endPosition_leftKill).getTeamColor() == ChessGame.TeamColor.BLACK){
        if(endPosition_leftKill.getRow() == 8){
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, ChessPiece.PieceType.QUEEN));
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, ChessPiece.PieceType.BISHOP));
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, ChessPiece.PieceType.KNIGHT));
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, ChessPiece.PieceType.ROOK));
        } else {
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, null));
        }
      }

      // RIGHT KILL
      ChessPosition endPosition_rightKill = new ChessPosition(startingRow+1, startingCol+1);
      if(endPosition_rightKill.checkValidPosition() && board.getPiece(endPosition_rightKill) != null && board.getPiece(endPosition_rightKill).getTeamColor() == ChessGame.TeamColor.BLACK){
        if(endPosition_rightKill.getRow() == 8){
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, ChessPiece.PieceType.QUEEN));
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, ChessPiece.PieceType.BISHOP));
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, ChessPiece.PieceType.KNIGHT));
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, ChessPiece.PieceType.ROOK));
        } else {
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, null));
        }
      }

    }

    return possMoves;
  }

}
