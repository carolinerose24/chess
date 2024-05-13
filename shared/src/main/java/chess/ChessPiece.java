package chess;

import chess.pieces.*;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece implements Cloneable{

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

        ChessPiece currentPiece = board.getPiece(myPosition);
        switch (currentPiece.getPieceType()) {
            case KING:
                KingMovesCalculator KMC = new KingMovesCalculator();
                return KMC.pieceMoves(board, myPosition);
            case QUEEN:
                QueenMovesCalculator QMC = new QueenMovesCalculator();
                return QMC.pieceMoves(board, myPosition);
            case BISHOP:
                BishopMovesCalculator BMC = new BishopMovesCalculator();
                return BMC.pieceMoves(board, myPosition);
            case ROOK:
                RookMovesCalculator RMC = new RookMovesCalculator();
                return RMC.pieceMoves(board, myPosition);
            case KNIGHT:
                KnightMovesCalculator KnMC = new KnightMovesCalculator();
                return KnMC.pieceMoves(board, myPosition);
            case PAWN:
                PawnMovesCalculator PMC = new PawnMovesCalculator();
                return PMC.pieceMoves(board, myPosition);
//            default:
//                System.out.println("Unknown piece type");
//                break;
        }

        return null; // should never get here
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that=(ChessPiece) o;
        return type == that.type && pieceColor == that.pieceColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, pieceColor);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "type=" + type +
                ", pieceColor=" + pieceColor +
                '}';
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); // Just delegate to Object's implementation
    }
}



