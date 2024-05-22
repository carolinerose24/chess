package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RookMovesCalculator implements PieceMovesCalculator{
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

    Collection<ChessMove> possMoves = new ArrayList<>();
    possMoves.addAll(getRookMovesHorizontal(board, myPosition, 1));
    possMoves.addAll(getRookMovesHorizontal(board, myPosition, -1));
    possMoves.addAll(getRookMovesVertical(board, myPosition, 1));
    possMoves.addAll(getRookMovesVertical(board, myPosition, -1));
    return possMoves;
  }

  public Collection<ChessMove> getRookMovesHorizontal(ChessBoard board, ChessPosition myPosition, int colAdvance){
    ChessPiece currentPiece = board.getPiece(myPosition);
    int startingRow = myPosition.getRow();
    int startingCol = myPosition.getColumn();
    ChessGame.TeamColor currentColor = currentPiece.getTeamColor();
    Collection<ChessMove> possMoves = new ArrayList<>();
    ChessPosition newPosition;

    newPosition = new ChessPosition(startingRow, startingCol + colAdvance);
    while(newPosition.checkValidPosition()){
      if(board.getPiece(newPosition) == null){
        possMoves.add(new ChessMove(myPosition, newPosition, null));
      } else if(board.getPiece(newPosition) != null){ //when we run into a piece
        if(currentColor != board.getPiece(newPosition).getTeamColor()){
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      newPosition = new ChessPosition(startingRow, newPosition.getColumn() + colAdvance);
    }
    return possMoves;
  }

  public Collection<ChessMove> getRookMovesVertical(ChessBoard board, ChessPosition myPosition, int rowAdvance){
    ChessPiece currentPiece = board.getPiece(myPosition);
    int startingRow = myPosition.getRow();
    int startingCol = myPosition.getColumn();
    ChessGame.TeamColor currentColor = currentPiece.getTeamColor();
    Collection<ChessMove> possMoves = new ArrayList<>();
    ChessPosition newPosition;

    newPosition = new ChessPosition(startingRow + rowAdvance, startingCol);
    while(newPosition.checkValidPosition()){
      if(board.getPiece(newPosition) == null){
        possMoves.add(new ChessMove(myPosition, newPosition, null));
      } else if(board.getPiece(newPosition) != null){ //when we run into a piece
        if(currentColor != board.getPiece(newPosition).getTeamColor()){
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      newPosition = new ChessPosition(newPosition.getRow() + rowAdvance, startingCol);
    }
    return possMoves;
  }
}
