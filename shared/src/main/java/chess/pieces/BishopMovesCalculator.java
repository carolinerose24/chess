package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalculator implements PieceMovesCalculator {
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

    ChessPiece currentPiece = board.getPiece(myPosition);
    int startingRow = myPosition.getRow();
    int startingCol = myPosition.getColumn();

    ChessGame.TeamColor currentColor = currentPiece.getTeamColor();
    Collection<ChessMove> possMoves = new ArrayList<>();


    // UP RIGHT
    ChessPosition newPosition = new ChessPosition(startingRow+1, startingCol+1);
    int new_row = startingRow +1;
    int new_col = startingCol +1;
    while(newPosition.checkValidPosition()){ //while it is a valid position
      if(board.getPiece(newPosition) == null){ // valid and empty
        possMoves.add(new ChessMove(myPosition, newPosition, null)); // add it
      } else if(board.getPiece(newPosition) != null){
        // valid spot but a piece IS there, now check color of the piece
        if(currentColor != board.getPiece(newPosition).getTeamColor()){ // different teams
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      new_row = newPosition.getRow() + 1;
      new_col = newPosition.getColumn() + 1;
      newPosition = new ChessPosition(new_row, new_col);
    }


    // DOWN LEFT
    newPosition = new ChessPosition(startingRow-1, startingCol-1);
    while(newPosition.checkValidPosition()){ //while it is a valid position
      if(board.getPiece(newPosition) == null){ // valid and empty
        possMoves.add(new ChessMove(myPosition, newPosition, null)); // add it
      } else if(board.getPiece(newPosition) != null){
        // valid spot but a piece IS there, now check color of the piece
        if(currentColor != board.getPiece(newPosition).getTeamColor()){ // different teams
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      new_row = newPosition.getRow() - 1;
      new_col = newPosition.getColumn() -1;
      newPosition = new ChessPosition(new_row, new_col);
    }


    // DOWN RIGHT
    newPosition = new ChessPosition(startingRow-1, startingCol+1);
    while(newPosition.checkValidPosition()){ //while it is a valid position
      if(board.getPiece(newPosition) == null){ // valid and empty
        possMoves.add(new ChessMove(myPosition, newPosition, null)); // add it
      } else if(board.getPiece(newPosition) != null){
        // valid spot but a piece IS there, now check color of the piece
        if(currentColor != board.getPiece(newPosition).getTeamColor()){ // different teams
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      new_row = newPosition.getRow() - 1;
      new_col = newPosition.getColumn() +1;
      newPosition = new ChessPosition(new_row, new_col);
    }


    // UP LEFT
    newPosition = new ChessPosition(startingRow+1, startingCol-1);
    new_row = startingRow+1;//something to start
    new_col = startingCol-1; //

    while(newPosition.checkValidPosition()){ //while it is a valid position
//      if(board.getPiece(newPosition) == null){ // valid and empty
      if(board.getPiece(new ChessPosition(new_row, new_col)) == null){
        //when we do get piece at the new position, it needs to be -1 for the 0to7 index
        possMoves.add(new ChessMove(myPosition, newPosition, null)); // add it
      } else if(board.getPiece(newPosition) != null){
        // valid spot but a piece IS there, now check color of the piece
        if(currentColor != board.getPiece(newPosition).getTeamColor()){ // different teams
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      new_row = newPosition.getRow() + 1;
      new_col = newPosition.getColumn() -1;
      newPosition = new ChessPosition(new_row, new_col);
    }


    return possMoves;
  }
}
