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
    Collection<ChessMove> possMoves = new ArrayList<>();
    possMoves.addAll(forwardOneAndTwo(board, myPosition, currentPiece.getTeamColor()));
    possMoves.addAll(diagonalKills(board, myPosition, currentPiece.getTeamColor()));
    return possMoves;
  }


  private Collection<ChessMove> forwardOneAndTwo(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){
    ChessPiece currentPiece = board.getPiece(myPosition);
    int startingRow = myPosition.getRow();
    int startingCol = myPosition.getColumn();
    Collection<ChessMove> possMoves = new ArrayList<>();

    // for white pieces
    int advanceOne = 1;
    int advanceTwo = 2;
    int checkRowPromotion = 8;
    int checkRowHaventMoved = 2;

    if(color == ChessGame.TeamColor.BLACK){
      // going forward is a minus one
      // going forward 2 is minus 2
      advanceOne = -1;
      advanceTwo = -2;
      checkRowPromotion = 1;
      checkRowHaventMoved = 7;
    }

    ChessPosition endPositionF1 = new ChessPosition(startingRow + advanceOne, startingCol);
    if (endPositionF1.checkValidPosition() && board.getPiece(endPositionF1) == null){
      if(endPositionF1.getRow() == checkRowPromotion){
        possMoves.add(new ChessMove(myPosition, endPositionF1, ChessPiece.PieceType.QUEEN));
        possMoves.add(new ChessMove(myPosition, endPositionF1, ChessPiece.PieceType.BISHOP));
        possMoves.add(new ChessMove(myPosition, endPositionF1, ChessPiece.PieceType.KNIGHT));
        possMoves.add(new ChessMove(myPosition, endPositionF1, ChessPiece.PieceType.ROOK));
      } else {
        possMoves.add(new ChessMove(myPosition, endPositionF1, null));
      }
    }

    // FORWARD 2
    ChessPosition endPositionF2 = new ChessPosition(startingRow + advanceTwo, startingCol);
    if(startingRow == checkRowHaventMoved && board.getPiece(endPositionF1) == null && board.getPiece(endPositionF2) == null){
      possMoves.add(new ChessMove(myPosition, endPositionF2, null));
    }
    return possMoves;
  }

  private Collection<ChessMove> diagonalKills(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color){

    ChessPiece currentPiece = board.getPiece(myPosition);
    int startingRow = myPosition.getRow();
    int startingCol = myPosition.getColumn();
    Collection<ChessMove> possMoves = new ArrayList<>();

    // white pieces
    int advanceOne = 1;
    int rowPromotion = 8;

    if(color == ChessGame.TeamColor.BLACK){
      advanceOne = -1;
      rowPromotion = 1;
    }

    // LEFT KILL (its left)
    ChessPosition endPositionLeftKill = new ChessPosition(startingRow + advanceOne, startingCol+1);
    if(endPositionLeftKill.checkValidPosition() && board.getPiece(endPositionLeftKill) != null && board.getPiece(endPositionLeftKill).getTeamColor() != color){
      if(endPositionLeftKill.getRow() == rowPromotion){
        possMoves.add(new ChessMove(myPosition, endPositionLeftKill, ChessPiece.PieceType.QUEEN));
        possMoves.add(new ChessMove(myPosition, endPositionLeftKill, ChessPiece.PieceType.BISHOP));
        possMoves.add(new ChessMove(myPosition, endPositionLeftKill, ChessPiece.PieceType.KNIGHT));
        possMoves.add(new ChessMove(myPosition, endPositionLeftKill, ChessPiece.PieceType.ROOK));
      } else {
        possMoves.add(new ChessMove(myPosition, endPositionLeftKill, null));
      }
    }

    // RIGHT KILL
    ChessPosition endPositionRightKill = new ChessPosition(startingRow + advanceOne, startingCol-1);
    if(endPositionRightKill.checkValidPosition() && board.getPiece(endPositionRightKill) != null && board.getPiece(endPositionRightKill).getTeamColor() != color){
      if(endPositionRightKill.getRow() == rowPromotion){
        possMoves.add(new ChessMove(myPosition, endPositionRightKill, ChessPiece.PieceType.QUEEN));
        possMoves.add(new ChessMove(myPosition, endPositionRightKill, ChessPiece.PieceType.BISHOP));
        possMoves.add(new ChessMove(myPosition, endPositionRightKill, ChessPiece.PieceType.KNIGHT));
        possMoves.add(new ChessMove(myPosition, endPositionRightKill, ChessPiece.PieceType.ROOK));
      } else {
        possMoves.add(new ChessMove(myPosition, endPositionRightKill, null));
      }
    }
    return possMoves;
  }

}
