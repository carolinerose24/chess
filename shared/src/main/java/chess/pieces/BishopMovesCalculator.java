package chess.pieces;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalculator implements PieceMovesCalculator {
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    Collection<ChessMove> possMoves = new ArrayList<>();
    possMoves.addAll(getBishopMovesOneWay(board, myPosition, 1, 1));
    possMoves.addAll(getBishopMovesOneWay(board, myPosition, -1, 1));
    possMoves.addAll(getBishopMovesOneWay(board, myPosition, -1, -1));
    possMoves.addAll(getBishopMovesOneWay(board, myPosition, 1, -1));
    return possMoves;
  }

  public Collection<ChessMove> getBishopMovesOneWay(ChessBoard board, ChessPosition myPosition, int rowAdvance, int colAdvance){
    ChessPiece currentPiece = board.getPiece(myPosition);
    int startingRow = myPosition.getRow();
    int startingCol = myPosition.getColumn();
    ChessGame.TeamColor currentColor = currentPiece.getTeamColor();
    Collection<ChessMove> possMoves = new ArrayList<>();
    ChessPosition newPosition;

    newPosition = new ChessPosition(startingRow + rowAdvance, startingCol + colAdvance);
    while(newPosition.checkValidPosition()){ //while it is a valid position
      if(board.getPiece(newPosition) == null){ // valid and empty
        possMoves.add(new ChessMove(myPosition, newPosition, null)); // add it
      } else if(board.getPiece(newPosition) != null){
        if(currentColor != board.getPiece(newPosition).getTeamColor()){ // different teams
          possMoves.add(new ChessMove(myPosition, newPosition, null));
        }
        break;
      }
      newPosition = new ChessPosition(newPosition.getRow() + rowAdvance, newPosition.getColumn() + colAdvance);
    }
    return possMoves;
  }

}
