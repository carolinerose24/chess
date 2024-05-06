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

    // declare something to hold the possible moves - what we will return
    Collection<ChessMove> possMoves = new ArrayList<>();


    // bishops can move all 4 diagonals as long as they are not blocked
    // blocked by their own piece, can't move there or further

    // blocked by other team piece, can go there but no further

    // blocked by EDGES OF THE BOARD


    // create a loop that does row+1, col-1 until it reaches an invalid place (off of the board)
    //create a var upleft for possible positions that are up and left of it



    // first generate all diagonal positions that are on the board and are POSSIBLE
    // then check if they are blocked by something? NO

    // check upleft 1, see if valid, add it to the list
    // check upleft 2, etc
    // go until we reach another piece, check if it is oppositce color, if so add it
      // OR until we reach the end of the board

    // repeat this in all 4 directions:




//    // UP LEFT
//    ChessPosition newPosition = new ChessPosition(startingRow+1, startingCol-1);
//    int new_row = startingRow+1;//something to start
//    int new_col = startingCol-1; //
//
//    while(newPosition.checkValidPosition()){ //while it is a valid position
////      if(board.getPiece(newPosition) == null){ // valid and empty
//      if(board.getPiece(new ChessPosition(new_row, new_col)) == null){
//        //when we do get piece at the new position, it needs to be -1 for the 0to7 index
//        possMoves.add(new ChessMove(myPosition, newPosition, null)); // add it
//      } else if(board.getPiece(new ChessPosition(new_row, new_col)) != null){ //might need to do the +/- thing here too??
////      } else if(board.getPiece(newPosition) != null){
//        // valid spot but a piece IS there, now check color of the piece
//        if(currentColor != board.getPiece(newPosition).getTeamColor()){ // different teams
//          possMoves.add(new ChessMove(myPosition, newPosition, null));
//          break;
//        } // else do nothing
//      }
//      new_row = newPosition.getRow() + 1;
//      new_col = newPosition.getColumn() -1;
//      newPosition = new ChessPosition(new_row, new_col);
//    }


//    // UP Right
//    newPosition = new ChessPosition(startingRow+1, startingCol+1);
//    new_row = startingRow+1;//something to start
//    new_col = startingCol+1; //
//
//    while(newPosition.checkValidPosition()){ //while it is a valid position
////      if(board.getPiece(newPosition) == null){ // valid and empty
//
//
//      // if i do -1 for both, it gets the current chess piece? but doesn't do this above?????
//      if(board.getPiece(new ChessPosition(new_row, new_col)) == null){
//        //when we do get piece at the new position, it needs to be -1 for the 0to7 index
//        possMoves.add(new ChessMove(myPosition, newPosition, null)); // add it
//      } else if(board.getPiece(new ChessPosition(new_row, new_col)) != null){ //might need to do the +/- thing here too??
////      } else if(board.getPiece(newPosition) != null){
//        // valid spot but a piece IS there, now check color of the piece
//        if(currentColor != board.getPiece(newPosition).getTeamColor()){ // different teams
//          possMoves.add(new ChessMove(myPosition, newPosition, null));
//          break;
//        } // else do nothing
//      }
//      new_row = newPosition.getRow() + 1;
//      new_col = newPosition.getColumn() +1;
//      newPosition = new ChessPosition(new_row, new_col);
//    }








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
          break;
        } // else do nothing
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
          break;
        } // else do nothing
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
          break;
        } // else do nothing
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
      } else if(board.getPiece(new ChessPosition(new_row, new_col)) != null){ //might need to do the +/- thing here too??
//      } else if(board.getPiece(newPosition) != null){
        // valid spot but a piece IS there, now check color of the piece
        if(currentColor != board.getPiece(newPosition).getTeamColor()){ // different teams
          possMoves.add(new ChessMove(myPosition, newPosition, null));
          break;
        } // else do nothing
      }
      new_row = newPosition.getRow() + 1;
      new_col = newPosition.getColumn() -1;
      newPosition = new ChessPosition(new_row, new_col);
    }


    return possMoves;
  }
}
