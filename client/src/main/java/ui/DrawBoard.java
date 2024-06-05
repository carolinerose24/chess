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



  private void drawChessBoard(PrintStream out) {
    for (int boardRow = 0; boardRow < 8; ++boardRow) {
      drawRowOfSquares(out, boardRow, (boardRow % 2 == 0)); //will pass if white is first or not
    }
  }
  private void drawChessBoardBack(PrintStream out) {
    for (int boardRow = 7; boardRow >= 0; boardRow = boardRow-1) {
      drawRowOfSquaresBack(out, boardRow, (boardRow % 2 == 1)); //will pass if white is first or not
    }
  }


  private void drawRowOfSquares(PrintStream out, int row, boolean whiteFirst){

    printOneBlackSquare(out);
    int displayedRow = 8 - row;
    setGrey(out);
    out.print(EMPTY + displayedRow + EMPTY);

    // print the squares in between
    for(int i = 0; i < 8; i++){
      String piece = board.get(row).get(i);
      String pieceColor = null;
      String pieceType = null;

      if(piece != null && !piece.equals("EMPTY")){ //not null and not empty
        String[] parts = piece.split(" ");
        pieceColor = parts[0];
        pieceType = parts[1];
      }

      if(whiteFirst){
        printSquare(out, "WHITE", pieceType, pieceColor);
        whiteFirst = false;
      } else {
        printSquare(out, "BLACK", pieceType, pieceColor);
        whiteFirst = true;
      }

    }

    // print last grey block with row number:
    setGrey(out);
    out.print(EMPTY + displayedRow + EMPTY);
    setBlack(out);
    out.println();
  }
  private void drawRowOfSquaresBack(PrintStream out, int row, boolean whiteFirst){

    printOneBlackSquare(out);
    int displayedRow = 8 - row;
    setGrey(out);
    out.print(EMPTY + displayedRow + EMPTY);

    // print the squares in between
    for(int i = 0; i < 8; i++){
      String piece = board.get(row).get(7-i);
      String pieceColor = null;
      String pieceType = null;

      if(piece != null && !piece.equals("EMPTY")){ //not null and not empty
        String[] parts = piece.split(" ");
        pieceColor = parts[0];
        pieceType = parts[1];
      }

      if(whiteFirst){
        printSquare(out, "WHITE", pieceType, pieceColor);
        whiteFirst = false;
      } else {
        printSquare(out, "BLACK", pieceType, pieceColor);
        whiteFirst = true;
      }

    }

    // print last grey block with row number:
    setGrey(out);
    out.print(EMPTY + displayedRow + EMPTY);
    setBlack(out);
    out.println();
  }


  private void printSquare(PrintStream out, String squareColor, String pieceType, String pieceColor){

    if(squareColor.equalsIgnoreCase("WHITE")){
      out.print(SET_BG_COLOR_LIGHT);
    } else if(squareColor.equalsIgnoreCase("BLACK")) {
      out.print(SET_BG_COLOR_DARK);
    }

    if(pieceType != null && pieceColor != null){
      if(pieceColor.equalsIgnoreCase("WHITE")){
        out.print(SET_TEXT_COLOR_DARK_BLUE);
        out.print(SET_TEXT_ITALIC);
        out.print(EMPTY + pieceType + EMPTY);
        out.print(RESET_TEXT_ITALIC);
      } else if(pieceColor.equalsIgnoreCase("BLACK")) {
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_TEXT_BOLD);
        out.print(EMPTY + pieceType + EMPTY);
        out.print(RESET_TEXT_BOLD_FAINT);
      }
    } else {
      out.print("   "); //print an empty block
    }
  }

  private static void printOneBlackSquare(PrintStream out){
    out.print(SET_BG_COLOR_BLACK);
    out.print("   ");
  }


  private static void setBlack(PrintStream out) {
    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_BLACK);
  }

  private static void setGrey(PrintStream out) {
    out.print(SET_BG_COLOR_LIGHT_GREY);
    out.print(SET_TEXT_COLOR_BLACK);
  }


}
