package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    //I just added these, think they should be fine
    private PieceType type;
    private ChessGame.TeamColor pieceColor;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
        //returns a COLLECTION - return all the move objects for everywhere the piece can go
        // start position is the same for all, end positions are different for each possible move
        // need to account for where the other pieces on the board are

        // ignore if it is your turn or not, ignore if you are in check/checkmate - account for somewhere else


        // where is the piece, what are legal places to move, which are the actual ones
        // don't matter what order the moves are returned



        // find the type of piece from the position and board
        // then use a switch case for what type of piece it is that then calls the MoveCalculator
        //new file for each new class then
        // this function should be shorter


        // EX ---->  getPiece(ChessPosition position) returns a ChessPiece - which has a type and color
        // THEN use the switch case to call the specific move for that PIECE

    }
}