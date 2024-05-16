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

    // is this all the variables that we need?
    private TeamColor teamColor;
    private ChessBoard board;

    public ChessGame(){
        // white always goes first, not sure if i need this or not
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

        // if it is currently in check, can only add moves that take you out of check
        // but we always want to add moves that don't put you IN check


        // get all possible moves it can make, now check if any of the moves make it go into check
        Collection<ChessMove> possMoves = currentPiece.pieceMoves(board, startPosition);
        Collection<ChessMove> validatedMoves = new ArrayList<>();


        // is in check??

//        if(isInCheckmate(currentColor)){
//            // if there are no possible moves because we are in checkmate, return null?
////            return null; // NOT SURE ABOUT THIS, or an empty list?
//            return validatedMoves; // returning an empty list??????? or just null??
//        } else

        if(isInCheck(currentColor)){
            // need to make a move that gets up out of check

            // now make a copied board and test out all the moves
            for(ChessMove move : possMoves){
                // make the move on a copied board, then see if you are in check after
                ChessBoard copiedBoard = (ChessBoard) board.clone();
                copiedBoard.addPiece(move.getStartPosition(), null);
                copiedBoard.addPiece(move.getEndPosition(), new ChessPiece(currentColor, currentType));

                if(!isInCheck(currentColor, copiedBoard)){ // if the move doesn't make us go into check/gets us out of check
                    validatedMoves.add(move);
                } // else don't add this move
            }
            return validatedMoves;
        } else {
            // can make any of the moves that this piece can go to
            return possMoves;
        }



        // does this call pieceMoves(ChessBoard board, ChessPosition myPosition) ?
        //get the piece at that location? from the board? call piece Moves on that??


        // this calls the isInCheck or checkMate methods inside it
        // does this by calling make move on a COPIED board, see if it leads to check or mate


        //first check if WE (the team whose turn it is) is in check, so if we can move

        //if yes, check if it is checkmate?
        // else

        // next write this method, while just calling isInCheckMate for now, then finish writing it in a bit
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


        // if there is no piece at the start position,
        if(board.getPiece(move.getStartPosition()) == null){
            throw new InvalidMoveException("There is no piece at the starting position.");
        }

        ChessPiece currentPiece = board.getPiece(move.getStartPosition());

        TeamColor currentColor = currentPiece.getTeamColor();
        ChessPiece.PieceType currentType = currentPiece.getPieceType();
        ChessPiece.PieceType promotionType = move.getPromotionPiece(); // this may be null


        // if the start or end position are not on the board
        if(!move.getStartPosition().checkValidPosition()){
            throw new InvalidMoveException("The starting position was not on the board.");
        } else if(!move.getEndPosition().checkValidPosition()){
            throw new InvalidMoveException("The ending position was not on the board.");
        }


        // then check if your piece cannot move there
        // see what valid moves this piece can make
        Collection<ChessMove> possMoves = validMoves(move.getStartPosition());
        if(possMoves.isEmpty()){
            throw new InvalidMoveException("There are no valid moves for this piece.");
        } else if(!possMoves.contains(move)) {
            //if it doesn't have that move, it can't move there
            throw new InvalidMoveException("This isn't a valid move for this piece.");
        }




        // if you are in check after the move -->
        // MAKE THE MOVE ON A COPIED BOARD
        ChessBoard copiedBoard = (ChessBoard) board.clone();
        copiedBoard.addPiece(move.getStartPosition(), null);
        copiedBoard.addPiece(move.getEndPosition(), new ChessPiece(currentColor, currentType));

        // now check if this team is in check??
        // not sure how to call isInCheck on the copied board, not the real board??
        // NEED TO WORK MORE ON THIS PART @@@@@@@@@@

        if(isInCheck(teamColor, copiedBoard)){
            throw new InvalidMoveException("This leaves you in check.");
        }



        // now check if it is your turn???

        // if it is not your turn, return false -----> not sure if this is the right way to do it though...
        /// ACTUALLY THIS MIGHT BE WRONG because this method doesn't pass in a color ???????


        // why would this kill the PROMOTION TESTS???????
        if(currentColor != getTeamTurn()){
            throw new InvalidMoveException("It is not your turn.");
        }

        /////////// MIGHT NEED TO FIX THIS LATER @@@@@@@@@@@@^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^



        // IF NONE OF THOSE THINGS THREW AN EXCEPTION
        // delete piece at start position, move it to end position on the REAL board
        board.addPiece(move.getStartPosition(), null); // removes the old position


        if(promotionType != null){
            board.addPiece(move.getEndPosition(), new ChessPiece(currentColor, promotionType));
        } else {
            board.addPiece(move.getEndPosition(), new ChessPiece(currentColor, currentType));
        }


        // update whose turn it is after a successful move????
        // opposite of current color
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


    // make the same method but overload the parameter arguments
    public boolean isInCheck(TeamColor teamColor, ChessBoard cb){
        // when I am checking if the copied board is in check after a move, use this method...
        ChessPosition currentKingSpot = findKingPosition(teamColor);
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPosition newPos = new ChessPosition(i, j);
                if(cb.getPiece(newPos) != null && cb.getPiece(newPos).getTeamColor() != teamColor){
                    //if there is a piece there and it is the opposite color
                    ChessPiece currentPiece = cb.getPiece(newPos);
                    Collection<ChessMove> moves = currentPiece.pieceMoves(cb, newPos);
                    for(ChessMove move : moves){
                        // now check if any of the moves end in the king spot
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
        if(isInCheck(teamColor)){
            //code for seeing if we can move out of check
            //iterate through the whole board, make every move of that team on a copied board
            // see if any moves get you out of check, if not return TRUE


            // go through entire board, make each possible move on a copied board, see if ANY of them get us out of check
            for(int i = 1; i < 9; i++){
                for(int j = 1; j < 9; j++){
                    //iterate through the board
                    ChessPosition currentPos = new ChessPosition(i,j);
                    if(board.getPiece(currentPos) != null && board.getPiece(currentPos).getTeamColor() == teamColor){
                        // it we find a piece of OUR color
                        Collection<ChessMove> possMoves = validMoves(currentPos);
                        if(!possMoves.isEmpty()){
                            return false; // this means there is a move we can make
                        }
                    }

                }
            }




            return true; // if we went through every piece and has NO moves
        } else {return false;}

        // check all the places the king can move, if in check
        // check if you can move any other pieces to stop the check???

        //ChessBoard copiedBoard = (ChessBoard) board.clone();
        // isvalid --> make the move on a COPIED board, see if it goes to check

        //throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {

        // if it is not your turn, return false -----> not sure if this is the right way to do it though...
        if(teamColor != getTeamTurn()){
            return false;
        }

        // if you are in check, return false
        if(isInCheck(teamColor)){
            return false;
        }

        // now get all valid moves
        Collection<ChessMove> currentValidMoves =new ArrayList<>();
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                //iterate through the board
                ChessPosition currentPos = new ChessPosition(i,j);
                if(board.getPiece(currentPos) != null && board.getPiece(currentPos).getTeamColor() == teamColor){
                    // if there is a piece there, and it is your color
                    currentValidMoves.addAll(validMoves(currentPos)); // add any valid moves to the list
                }

            }
        }
        // once it iterates, see if the list is empty
        if(currentValidMoves.isEmpty()){
            return true;
        }
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
