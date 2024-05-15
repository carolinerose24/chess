package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    // is this all the variables that we need?
    private TeamColor teamColor;
    private ChessBoard board;


    public ChessGame(){
        // nothing in here, don't have a board to pass in to start, makes a new one
        // white always goes first
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


        // does this call pieceMoves(ChessBoard board, ChessPosition myPosition) ?
        //get the piece at that location? from the board? call piece Moves on that??


        // this calls the isInCheck or checkMate methods inside it
        // does this by calling make move on a COPIED board, see if it leads to check or mate



        //first check if WE (the team whose turn it is) is in check, so if we can move

        //if yes, check if it is checkmate?
        // else


//        return pieceMoves(board, startPosition);

        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {


        // not sure if we need the try/catch block because the method throws the exception
        // might need to add something to the InvalidMoveException class????


        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        //check if any of the opposing teams pieces have valid moves that lead to the King spot
        // if any of them do, return true?


        //iterate over the board
        // if (get piece) != null and it is the opposite color, see what valid moves it has
        // then see if any of those moves land them in the square where the king is
        // if so, return TRUE (break)
        // else if you iterate through all of it, return FALSE




        // isvalid --> make the move on a COPIED board, see if it goes to check

        // Clone the original board



        ChessBoard copiedBoard = (ChessBoard) board.clone();
        ChessPosition currentKingSpot = findKingPosition(teamColor);


        // if current team color is BLACK ---> no, just check if it is the oppostite color during the loop
        // iterate through the board, get all the possible moves for each piece

        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPosition newPos = new ChessPosition(i, j);
                if(board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != teamColor){
                    //if there is a piece there and it is the opposite color
                    ChessPiece currentPiece = board.getPiece(newPos);
                    Collection<ChessMove> moves = currentPiece.pieceMoves(board, newPos);
                    for(ChessMove move : moves){
                        // now check if any of the moves end in the king spot
                        if(move.getEndPosition().equals(currentKingSpot)){
                            return true;
                        }
                    }

                }
            }
        }
        return false; // none of the pieces ended in the king spot, so that color is not in check
    }


    private ChessPosition findKingPosition(TeamColor teamColor){
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPosition newPos = new ChessPosition(i, j);
                if(board.getPiece(newPos) != null && board.getPiece(newPos).getPieceType() == ChessPiece.PieceType.KING && board.getPiece(newPos).getTeamColor() == teamColor){
                    return newPos; // if not null, is a king, and the right color
                }
            }
        }
        return null; // should never reach here, there will always be a king
    }






    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {

        // check the current position of the king, if it is in check
        // check all the places the king can move, if in check
        // check if you can move any other pieces to stop the check???

        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        // call valid moves on a team, and if none, return true

        //check if it is that teams turn
        // then check all the pieces, see if any have valid moves

        throw new RuntimeException("Not implemented");
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



    // never hurts to have these, not sure if we need them though

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
