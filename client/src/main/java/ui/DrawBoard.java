package ui;

import chess.ChessGame;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static ui.EscapeSequences.*;

public class DrawBoard {

  private final static String EMPTY = " ";
  private ArrayList<ArrayList<String>> board = new ArrayList<>(8);
  // I think I could also have it pass in a ChessBoard or ChessGame???
  // chess game AND which perspective

  // Pass in a new ChessGame(ChessGame, teamColor - enum)
  // do this in phase 6

  public DrawBoard(ArrayList<ArrayList<String>> board){ // with parameters, pass in the 2D array, do this more later??
    this.board = board;
    draw();
  }

  public DrawBoard(ChessGame chessGame){
    //set the booard = this new game, the team color
    // call draw from a certain perspective
  }



  // need anoother method for highlihgting possible moves

  public DrawBoard(){ // no parameters, set squares equal to a default board
    setDefaultBoard();
    draw();
  }

  private void draw(){
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
//    out.print(ERASE_SCREEN);
    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_LIGHT_GREY);

    drawWhitePerspective(out);

    out.print(SET_TEXT_COLOR_LIGHT_GREY);
    drawBlackPerspective(out);

    out.print(SET_TEXT_COLOR_RESET_WHITE);
    out.print(SET_BG_COLOR_RESET_GREY);
  }

  private void drawWhitePerspective(PrintStream out){
    out.println("          WHITE PERSPECTIVE");
    drawHeaders(out);
    drawChessBoard(out);
    drawHeaders(out);
    out.print(SET_BG_COLOR_BLACK);
    out.println();
  }
  private void drawBlackPerspective(PrintStream out){
    out.println("          BLACK PERSPECTIVE");
    drawHeadersBack(out);
    drawChessBoardBack(out);
    drawHeadersBack(out);
    out.print(SET_BG_COLOR_BLACK);
    out.println();
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

  private void setDefaultBoard(){

    for (int i = 0; i < 8; ++i) {
      ArrayList<String> row = new ArrayList<>(Arrays.asList(new String[8]));
      for (int j = 0; j < 8; ++j) {
        row.set(j, "EMPTY");
      }
      board.add(row);
    }

    // White pieces
    board.get(7).set(0, "WHITE R");
    board.get(7).set(1, "WHITE N");
    board.get(7).set(2, "WHITE B");
    board.get(7).set(3, "WHITE Q");
    board.get(7).set(4, "WHITE K");
    board.get(7).set(5, "WHITE B");
    board.get(7).set(6, "WHITE N");
    board.get(7).set(7, "WHITE R");
    for (int i = 0; i < 8; ++i) {
      board.get(6).set(i, "WHITE P");
    }

    // Black pieces
    board.get(0).set(0, "BLACK R");
    board.get(0).set(1, "BLACK N");
    board.get(0).set(2, "BLACK B");
    board.get(0).set(3, "BLACK Q");
    board.get(0).set(4, "BLACK K");
    board.get(0).set(5, "BLACK B");
    board.get(0).set(6, "BLACK N");
    board.get(0).set(7, "BLACK R");
    for (int i = 0; i < 8; ++i) {
      board.get(1).set(i, "BLACK P");
    }
  }

}
