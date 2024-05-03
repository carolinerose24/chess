package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMovesCalculator implements PieceMovesCalculator {

//  @Override
//  public List<ChessMove> pieceMoves(ChessPosition currentPosition) {
//    // Implementation for calculating pawn moves
//  }


  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
//    return List.of();

    //takes in the board, which has the 2D array of pieces

    //how a pawn moves:

    //Chess position has its Row and Col

    // Chess board has 2D array of chess pieces, which have a type and a color
    // to access them
    int startingRow = myPosition.getRow();
    int startingCol = myPosition.getColumn();

    board.getPiece(myPosition); // returns a ChessPiece, which has a color and type

    //just write general rules right now? don't know which side it will be from????
    // OR --> if white, can go -1 vs if black, move +1?? or something???


    // get the piece that we are working with (SHOULD BE A PAWN)
    ChessPiece currentPiece = board.getPiece(myPosition);

    // declare something to hold the possible moves
    List<ChessMove> possMoves = new ArrayList<>();


    if(currentPiece.getTeamColor() == ChessGame.TeamColor.BLACK){ //not sure if .equals or == for an enum
      ChessPiece.PieceType promotionalType = ChessPiece.PieceType.QUEEN; //have this for now

      // NEED MAKE 4 IDENTICAL MOVES FOR THE DIFFERENT THINGS THAT CAN BE PROMOTED
      // promotionalType can be NULL --> if it is not promoting (not in the 1st or last row, return it as NULL)?


      // can go forward 1 ---> first need to check if anything is in front of me OR if i am at the end of the board
      ChessPosition endPosition = new ChessPosition(startingRow+1, startingCol);
      // if no piece in front AND you are not about to go off the edge of the board @@ FIX THIS
      // maybe change the order of these statements
      if (endPosition.getRow() != 0 &&endPosition.getRow() != 9 && board.getPiece(endPosition) == null ){
        //Then you are allowed to move forward 1

        // if you are on the first or last row, need to consider promotion
        if(endPosition.getRow() == 1 || endPosition.getRow() == 8){
          possMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.QUEEN));
          possMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.BISHOP));
          possMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.KNIGHT));
          possMoves.add(new ChessMove(myPosition, endPosition, ChessPiece.PieceType.ROOK));
        } else { // else just move forward one normally
          possMoves.add(new ChessMove(myPosition, endPosition, null));
        }
      }

      //if the two spaces in front are open AND we are on the right row
      // can move 2 spaces forward


      // LEFT KILL
      // if the row+1, col-1 space is not null (AND THAT PIECE IS THE OTHER COLOR)
      // AND not off the edge of the board, then it is a possible move
      // ALSO need to check if it is at the ends of the board (can promote)


      // RIGHT KILL
      // if the row+1, col+1 space is not null (AND THAT PIECE IS THE OTHER COLOR)
      // AND not off the edge of the board that way, it is a possible move
      // then check if row+1 is at the end, possible promotion



    }




//    if()








    //pawn can go forwards by 1
    // go side ways to kill forwards (if something is THERE)
    //can more forward by 2 on the first move (check the position, what row it is in)

    return possMoves;
  }



}
