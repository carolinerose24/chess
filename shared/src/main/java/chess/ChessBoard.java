package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[][] squares = new ChessPiece[8][8];

    public ChessBoard() {
        // not sure if i need anything inside this --> to initialize pieces? or if that is in reset board
        //maybe call reset board here????
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()][position.getColumn()] = piece;
        //takes a position and a piece, put the piece in the right place
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()][position.getColumn()];
        // do i need to do anything to make it return null elsewise??
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {

        // MIGHT JUST call the add Piece function a bunch of times???

        // BLACK PIECE ON TOP
        // add rooks at the corners of the board:
//        for(int i = 1; i < 5; i++){
//            addPiece();
//        }

//        ChessPosition topleft = new ChessPosition(1,1);
//        ChessPiece rook = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK) //fix later??
//        addPiece(topleft, rook);






        // not sure if i should use 1-8 or 0-7 @@@@@@@@@@@@@@@@@@

        //maybe create some private helper functions for this????

        addPiece(new ChessPosition(0,0),
                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));

        addPiece(new ChessPosition(7,7),
                new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));




//        throw new RuntimeException("Not implemented");
        // creates an instance of chess piece
    }
}
