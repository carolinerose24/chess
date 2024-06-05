package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static ui.EscapeSequences.*;

public class DrawBoard {

  private final String EMPTY = " ";
  private ArrayList<ArrayList<String>> board = new ArrayList<>(8);


  public DrawBoard(ArrayList<ArrayList<String>> board){ // with parameters, pass in the 2D array, do this more later??
    this.board = board;
    draw();
  }

  public DrawBoard(){ // no parameters, set squares equal to a default board
    setDefaultBoard();
    draw();
  }


  private void draw(){
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    out.print(ERASE_SCREEN);

    // for WHITE perspective
    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_LIGHT_GREY);
    out.println("          WHITE PERSPECTIVE");

    drawHeaders(out);
    drawChessBoard(out);
    drawHeaders(out);
    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_WHITE);

    out.println(); //empty line between the 2 boards

    // for BLACK perspective ...
    out.print(SET_TEXT_COLOR_LIGHT_GREY);
    out.println("          BLACK PERSPECTIVE");
    drawHeadersBack(out);
    drawChessBoardBack(out);
    drawHeadersBack(out);

    out.println();
    out.print(RESET_TEXT_COLOR);
    out.print(RESET_BG_COLOR);
  }

  private void drawHeaders(PrintStream out) {
    printOneBlackSquare(out);
    setGrey(out);
    String[] headers = { "  ", "a", "b", "c", "d", "e", "f", "g", "h", "  "};
    for (int boardCol = 0; boardCol < 10; ++boardCol) {
      setGrey(out);
      out.print(headers[boardCol]);
      if (boardCol < 9) {
        out.print(EMPTY.repeat(2));
      }
    }
    out.print(RESET_BG_COLOR);
    out.println();
  }
  private void drawHeadersBack(PrintStream out) {
    printOneBlackSquare(out);
    setGrey(out);
    String[] headers = { "  ", "h", "g", "f", "e", "d", "c", "b", "a", "  "};
    for (int boardCol = 0; boardCol < 10; ++boardCol) {
      setGrey(out);
      out.print(headers[boardCol]);
      if (boardCol < 9) {
        out.print(EMPTY.repeat(2));
      }
    }
    out.print(RESET_BG_COLOR);
    out.println();
  }

}
