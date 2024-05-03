package chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    // these will be numbers 1 - 8 for either
    private final int row;
    private final int col;

    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return row;
    }


    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return col;
    }


    // added helper function to see if a position is on the board
    public boolean checkValidPosition() {
        // Check if both row and column are within the range 1 to 8
        if (row >= 1 && row <= 8 && col >= 1 && col <= 8) {
            return true; // Position is valid
        } else {
            return false; // Position is invalid
        }
    }

    // helper function to see if a pawn is up for promotion?, no, not a very often occuring case

}
