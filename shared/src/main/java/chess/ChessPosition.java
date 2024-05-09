package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private final int row;
    private final int col;

    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public ChessPosition(String pos){
        //A7 or D3
        //the letter is the column, the number is the row
        char letter = pos.charAt(0);
        char number = pos.charAt(1);

        if (Character.isUpperCase(letter)) {
            // Convert uppercase letter to column number
            col = letter - 'A' + 1;
        } else {
            // Convert lowercase letter to column number
            col = letter - 'a' + 1;
        }
        row = Character.getNumericValue(number);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition that=(ChessPosition) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "ChessPosition{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
