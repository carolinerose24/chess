package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMovesCalculator implements PieceMovesCalculator{
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

    ChessPiece currentPiece = board.getPiece(myPosition);
    int startingRow = myPosition.getRow();
    int startingCol = myPosition.getColumn();
    ChessGame.TeamColor currentColor = currentPiece.getTeamColor();
    Collection<ChessMove> possMoves = new ArrayList<>();

    ChessPosition newPosition;


    // check going right
    newPosition = new ChessPosition(startingRow, startingCol+1);
    while(newPosition.checkValidPosition()){
      if(board.getPiece(newPosition) == null){
        possMoves.add(new ChessMove(myPosition, newPosition, null));
      } else if(board.getPiece(newPosition) != null){ //when we run into a piece
        if(currentColor != board.getPiece(newPosition).getTeamColor()){
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      newPosition = new ChessPosition(startingRow, newPosition.getColumn() + 1);
    }

    // check going left
    newPosition = new ChessPosition(startingRow, startingCol-1);
    while(newPosition.checkValidPosition()){
      if(board.getPiece(newPosition) == null){
        possMoves.add(new ChessMove(myPosition, newPosition, null));
      } else if(board.getPiece(newPosition) != null){ //when we run into a piece
        if(currentColor != board.getPiece(newPosition).getTeamColor()){
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      newPosition = new ChessPosition(startingRow, newPosition.getColumn() - 1);
    }

    // check going down
    newPosition = new ChessPosition(startingRow -1, startingCol);
    while(newPosition.checkValidPosition()){
      if(board.getPiece(newPosition) == null){
        possMoves.add(new ChessMove(myPosition, newPosition, null));
      } else if(board.getPiece(newPosition) != null){ //when we run into a piece
        if(currentColor != board.getPiece(newPosition).getTeamColor()){
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      newPosition = new ChessPosition(newPosition.getRow() -1, startingCol);
    }

    // check going up
    newPosition = new ChessPosition(startingRow +1, startingCol);
    while(newPosition.checkValidPosition()){
      if(board.getPiece(newPosition) == null){
        possMoves.add(new ChessMove(myPosition, newPosition, null));
      } else if(board.getPiece(newPosition) != null){ //when we run into a piece
        if(currentColor != board.getPiece(newPosition).getTeamColor()){
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      newPosition = new ChessPosition(newPosition.getRow() +1, startingCol);
    }















    return possMoves;
  }
}
