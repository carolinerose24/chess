package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class DrawBoard {

  private static final int BOARD_SIZE_IN_SQUARES = 8;
  private static final int SQUARE_SIZE_IN_CHARS = 3;
  private static final int LINE_WIDTH_IN_CHARS = 1;

  // and use a lot of ones form escape sequence

  private String [][] squares = new String[8][8];
  // just have it be Strings like BLACK_KING??
  public DrawBoard(String[][] squares){ // with parameters, pass in the 2D array, do this more later??
    this.squares = squares;
  }

  public DrawBoard(){ // no parameters, set squares equal to a default board

    // call all the methods in here @@@
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
//    out.print(ERASE_SCREEN); // decide if i want this after all
    drawHeaders(out);

    drawTicTacToeBoard(out);

    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_WHITE);


  }

  


}
