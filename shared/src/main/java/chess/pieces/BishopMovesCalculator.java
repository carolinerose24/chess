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
    ChessPosition newPosition;


    // UP LEFT
    newPosition = new ChessPosition(startingRow+1, startingCol-1);
    while(newPosition.checkValidPosition()){ //while it is a valid position
      if(board.getPiece(newPosition) == null){ // valid and empty
        possMoves.add(new ChessMove(myPosition, newPosition, null)); // add it
      } else if(board.getPiece(newPosition) != null){
        if(currentColor != board.getPiece(newPosition).getTeamColor()){ // different teams
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      newPosition = new ChessPosition(newPosition.getRow() + 1, newPosition.getColumn() - 1);
    }

    // UP RIGHT
    newPosition = new ChessPosition(startingRow+1, startingCol+1);
    while(newPosition.checkValidPosition()){ //while it is a valid position
      if(board.getPiece(newPosition) == null){ // valid and empty
        possMoves.add(new ChessMove(myPosition, newPosition, null)); // add it
      } else if(board.getPiece(newPosition) != null){
        if(currentColor != board.getPiece(newPosition).getTeamColor()){ // different teams
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      newPosition = new ChessPosition(newPosition.getRow() + 1, newPosition.getColumn() + 1);
    }

    // DOWN LEFT
    newPosition = new ChessPosition(startingRow-1, startingCol-1);
    while(newPosition.checkValidPosition()){ //while it is a valid position
      if(board.getPiece(newPosition) == null){ // valid and empty
        possMoves.add(new ChessMove(myPosition, newPosition, null)); // add it
      } else if(board.getPiece(newPosition) != null){
        if(currentColor != board.getPiece(newPosition).getTeamColor()){ // different teams
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      newPosition = new ChessPosition(newPosition.getRow() - 1, newPosition.getColumn() - 1);
    }

    // DOWN RIGHT

    newPosition = new ChessPosition(startingRow-1, startingCol+1);
    while(newPosition.checkValidPosition()){ //while it is a valid position
      if(board.getPiece(newPosition) == null){ // valid and empty
        possMoves.add(new ChessMove(myPosition, newPosition, null)); // add it
      } else if(board.getPiece(newPosition) != null){
        if(currentColor != board.getPiece(newPosition).getTeamColor()){ // different teams
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      newPosition = new ChessPosition(newPosition.getRow() - 1, newPosition.getColumn() + 1);
    }



    return possMoves;
  }
}
