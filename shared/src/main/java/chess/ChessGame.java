package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamColor;
    private ChessBoard board;

    public ChessGame(){
        teamColor = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
    }


    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamColor = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // if there isn't a piece at the start position, return null
        if(board.getPiece(startPosition) == null){
            return null;
        }

        ChessPiece currentPiece = board.getPiece(startPosition);
        TeamColor currentColor = currentPiece.getTeamColor();
        ChessPiece.PieceType currentType = currentPiece.getPieceType();

        Collection<ChessMove> possMoves = currentPiece.pieceMoves(board, startPosition);
        Collection<ChessMove> validatedMoves = new ArrayList<>();

        for(ChessMove move : possMoves){
            ChessBoard copiedBoard = (ChessBoard) board.clone();
            copiedBoard.addPiece(move.getStartPosition(), null);
            copiedBoard.addPiece(move.getEndPosition(), new ChessPiece(currentColor, currentType));

            if(!isInCheck(currentColor, copiedBoard)){
                validatedMoves.add(move);
            }
        }
        return validatedMoves;
    }


    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if(move == null){
            throw new InvalidMoveException("The move was null or empty.");
        }

        if(board.getPiece(move.getStartPosition()) == null){
            throw new InvalidMoveException("There is no piece at the starting position.");
        }

        ChessPiece currentPiece = board.getPiece(move.getStartPosition());
        TeamColor currentColor = currentPiece.getTeamColor();
        ChessPiece.PieceType currentType = currentPiece.getPieceType();
        ChessPiece.PieceType promotionType = move.getPromotionPiece(); // this may be null

        if(currentColor != getTeamTurn()){
            throw new InvalidMoveException("It is not your turn.");
        }

        // if the start or end position are not on the board
        if(!move.getStartPosition().checkValidPosition()){
            throw new InvalidMoveException("The starting position was not on the board.");
        } else if(!move.getEndPosition().checkValidPosition()){
            throw new InvalidMoveException("The ending position was not on the board.");
        }

        // now check all possible moves
        Collection<ChessMove> possMoves = validMoves(move.getStartPosition());
        if(possMoves.isEmpty()){
            throw new InvalidMoveException("There are no valid moves for this piece.");
        } else if(!possMoves.contains(move)) {
            throw new InvalidMoveException("This isn't a valid move for this piece.");
        }

        ChessBoard copiedBoard = (ChessBoard) board.clone();
        copiedBoard.addPiece(move.getStartPosition(), null);
        copiedBoard.addPiece(move.getEndPosition(), new ChessPiece(currentColor, currentType));
        if(isInCheck(teamColor, copiedBoard)){
            throw new InvalidMoveException("This leaves you in check.");
        }

        board.addPiece(move.getStartPosition(), null); // removes the old position
        if(promotionType != null){
            board.addPiece(move.getEndPosition(), new ChessPiece(currentColor, promotionType));
        } else {
            board.addPiece(move.getEndPosition(), new ChessPiece(currentColor, currentType));
        }
        setTeamTurn(getOpposite(currentColor));
    }

    private TeamColor getOpposite(TeamColor col){
        if(col == TeamColor.WHITE){
            return TeamColor.BLACK;
        } else {
            return TeamColor.WHITE;
        }
    }


    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition currentKingSpot = findKingPosition(teamColor);
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPosition newPos = new ChessPosition(i, j);
                if(board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != teamColor){
                    ChessPiece currentPiece = board.getPiece(newPos);
                    Collection<ChessMove> moves = currentPiece.pieceMoves(board, newPos);
                    for(ChessMove move : moves){
                        if(move.getEndPosition().equals(currentKingSpot)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    // make the same method but overload the parameter arguments
    public boolean isInCheck(TeamColor teamColor, ChessBoard cb){
        ChessPosition currentKingSpot = findKingPosition(teamColor, cb);
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPosition newPos = new ChessPosition(i, j);
                if(cb.getPiece(newPos) != null && cb.getPiece(newPos).getTeamColor() != teamColor){
                    ChessPiece currentPiece = cb.getPiece(newPos);
                    Collection<ChessMove> moves = currentPiece.pieceMoves(cb, newPos);
                    for(ChessMove move : moves){
                        if(move.getEndPosition().equals(currentKingSpot)){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    private ChessPosition findKingPosition(TeamColor teamColor){
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPosition newPos = new ChessPosition(i, j);
                if(board.getPiece(newPos) != null && board.getPiece(newPos).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(newPos).getTeamColor() == teamColor){
                    return newPos;
                }
            }
        }
        return null;
    }


    private ChessPosition findKingPosition(TeamColor teamColor, ChessBoard cb){
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPosition newPos = new ChessPosition(i, j);
                if(cb.getPiece(newPos) != null && cb.getPiece(newPos).getPieceType() == ChessPiece.PieceType.KING && cb.getPiece(newPos).getTeamColor() == teamColor){
                    return newPos;
                }
            }
        }
        return null;
    }



    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor)){
            for(int i = 1; i < 9; i++){
                for(int j = 1; j < 9; j++){
                    ChessPosition currentPos = new ChessPosition(i,j);
                    if(board.getPiece(currentPos) != null && board.getPiece(currentPos).getTeamColor() == teamColor){
                        Collection<ChessMove> possMoves = validMoves(currentPos);
                        if(!possMoves.isEmpty()){return false;}
                    }
                }
            }
            return true;
        } else {return false;}
    }


    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if(teamColor != getTeamTurn()){
            return false;
        }

        if(isInCheck(teamColor)){
            return false;
        }

        Collection<ChessMove> currentValidMoves = new ArrayList<>();
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPosition currentPos = new ChessPosition(i,j);
                if(board.getPiece(currentPos) != null && board.getPiece(currentPos).getTeamColor() == teamColor){
                    currentValidMoves.addAll(validMoves(currentPos));
                }
            }
        }
        if(currentValidMoves.isEmpty()){return true;}
        return false;
    }


    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }


    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame=(ChessGame) o;
        return teamColor == chessGame.teamColor && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "teamColor=" + teamColor +
                ", board=" + board +
                '}';
    }
}
