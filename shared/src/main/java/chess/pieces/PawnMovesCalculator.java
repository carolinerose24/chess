package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMovesCalculator implements PieceMovesCalculator {


  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

    // variables we will use
    ChessPiece currentPiece = board.getPiece(myPosition);
    int startingRow = myPosition.getRow();
    int startingCol = myPosition.getColumn();

    // declare something to hold the possible moves - what we will return
    Collection<ChessMove> possMoves = new ArrayList<>();


    // FOR BLACK PIECES
    if(currentPiece.getTeamColor() == ChessGame.TeamColor.BLACK){ //not sure if .equals or == for an enum

      // NEED MAKE 4 IDENTICAL MOVES FOR THE DIFFERENT THINGS THAT CAN BE PROMOTED
      // promotionalType can be NULL --> if it is not promoting (not in the 1st or last row, return it as NULL)?


      // FORWARD 1
      ChessPosition endPosition_forward1 = new ChessPosition(startingRow-1, startingCol); //1 row down
//      if (endPosition_forward1.getRow() != 0 && board.getPiece(endPosition_forward1) == null){
      if (endPosition_forward1.checkValidPosition() && board.getPiece(endPosition_forward1) == null){
        //if not going off the board and nothing in front

        // if you are on the first row need to consider promotion
        if(endPosition_forward1.getRow() == 1){
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, ChessPiece.PieceType.QUEEN));
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, ChessPiece.PieceType.BISHOP));
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, ChessPiece.PieceType.KNIGHT));
          possMoves.add(new ChessMove(myPosition, endPosition_forward1, ChessPiece.PieceType.ROOK));
        } else { // else just move forward one normally
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
      // if the row+1, col-1 space is not null (AND THAT PIECE IS THE OTHER COLOR)
      // AND not off the edge of the board, then it is a possible move
      // ALSO need to check if it is at the ends of the board (can promote)
      ChessPosition endPosition_leftKill = new ChessPosition(startingRow-1, startingCol+1);
      // chess piece at leftkill endposition = ___, and then get the TYPE
      // if a valid position, if a piece at LK, and if the piece is WHITE
      if(endPosition_leftKill.checkValidPosition() && board.getPiece(endPosition_leftKill) != null && board.getPiece(endPosition_leftKill).getTeamColor() == ChessGame.TeamColor.WHITE){

        //check promotion
        if(endPosition_leftKill.getRow() == 1){
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, ChessPiece.PieceType.QUEEN));
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, ChessPiece.PieceType.BISHOP));
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, ChessPiece.PieceType.KNIGHT));
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, ChessPiece.PieceType.ROOK));
        } else { // else just move forward one normally
          possMoves.add(new ChessMove(myPosition, endPosition_leftKill, null));
        }
      }


      // RIGHT KILL
      // if the row+1, col+1 space is not null (AND THAT PIECE IS THE OTHER COLOR)
      // AND not off the edge of the board that way, it is a possible move
      // then check if row+1 is at the end, possible promotion
      ChessPosition endPosition_rightKill = new ChessPosition(startingRow-1, startingCol-1);
      if(endPosition_rightKill.checkValidPosition() && board.getPiece(endPosition_rightKill) != null && board.getPiece(endPosition_rightKill).getTeamColor() == ChessGame.TeamColor.WHITE){

        //check promotion
        if(endPosition_rightKill.getRow() == 1){
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, ChessPiece.PieceType.QUEEN));
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, ChessPiece.PieceType.BISHOP));
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, ChessPiece.PieceType.KNIGHT));
          possMoves.add(new ChessMove(myPosition, endPosition_rightKill, ChessPiece.PieceType.ROOK));
        } else { // else just move forward one normally
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
        } else { // else just move forward one normally
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
      // if the row+1, col+1 space is not null (AND THAT PIECE IS THE OTHER COLOR)
      // AND not off the edge of the board that way, it is a possible move
      // then check if row+1 is at the end, possible promotion
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
