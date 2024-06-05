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


}
