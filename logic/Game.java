package logic;
import java.util.ArrayList;

public class Game {  
  public Field[][] board;
  int flagsTotal;
  int flagsLeft;
  boolean gameOn;
  int numberMines = 10;
  public int fieldSizeR = 9;
  public int fieldSizeC = 9;
  int uncovered = 0;

  public void initialize() {
    gameOn = true;
    flagsLeft = numberMines;
    flagsTotal = numberMines;
    board = new Field[fieldSizeR][fieldSizeC];
    placeFields();
  }

  public boolean canPlantFlag() {
    return (flagsLeft > 0);
  }

  public int countAdjMines(int r, int c) {
    int count = 0;
    ArrayList<int[]> neighbors = Utilities.calculateNeighbors(r, c);
    for (int[] neighbor : neighbors) {
      int row = neighbor[0];
      int col = neighbor[1];
      if (board[row][col] != null && board[row][col].type == FieldTypes.MINE) {
        count++;
      }
    }
    return count;
  }

  public void gameOver() {
    gameOn = false;
  }

  public Field getField(int r, int c) {
    return board[r][c];
  }

  public int getFlagsLeft() {
    return flagsLeft;
  }

  public int[][] getMinesArray() {
    int[][] arr = new int[flagsTotal][2];
    int pointer = 0;
    for (int r = 0; r < 9; r++) {
      for (int c = 0; c < 9; c++) {
        if (board[r][c].type == FieldTypes.MINE) {
          int[] coordinates = new int[2];
          coordinates[0] = r;
          coordinates[1] = c;
          arr[pointer] = coordinates;
          pointer++;
        }
      }
    }

    return arr;
  }

  public boolean isFlag(int r, int c) {
    return board[r][c].flagged;
  }

  public boolean isGameOn() {
    return gameOn;
  }

  public boolean isWin() {
    return uncovered == 71 && flagsLeft == 0 && gameOn;
  }

  public void plantFlag(int r, int c) {
    board[r][c].setFlagged(true);
    flagsLeft--;
  }

  public void removeFlag(int r, int c) {
    board[r][c].setFlagged(false);
    flagsLeft++;
  }

  public void revealField(int r, int c) {
    setUncovered(r, c);
    Field[] neighbors = getNeighbors(r, c);
    if (board[r][c].getNeighborMines() == 0) {
      for (Field neighbor : neighbors) {
        if (!neighbor.isUncovered() && !neighbor.isFlaggedd()) {
          neighbor.setUncovered();
          revealField(neighbor.r, neighbor.c);
        }
      }
    }
  }

  public void setUncovered(int r, int c) {
    if (!board[r][c].isUncovered()) {
      board[r][c].setUncovered();
      uncovered++;
    }
  }

  //private

  private Field[] getNeighbors(int r, int c) {
    ArrayList<int[]> neighbors = Utilities.calculateNeighbors(r, c);
    System.out.println(neighbors.size());
    Field[] n = new Field[neighbors.size()];
    for (int i = 0; i < neighbors.size(); i++) {
      n[i] = board[neighbors.get(i)[0]][neighbors.get(i)[1]];
    }
    System.out.println(n.length);
    return n;
  }

  private void placeFields() {
    placeMines();
    for (int r = 0; r < fieldSizeR; r++) {
      for (int c = 0; c < fieldSizeC; c++) {
        int adj = countAdjMines(r, c);
        if (board[r][c] == null) {
          board[r][c] = new Field(FieldTypes.REGULAR, r, c);
        }
        board[r][c].setNeihborMines(adj);
      }
    }
  }

  private void placeMines() {
    int minesPlaced = 0;
    while (minesPlaced < numberMines) {
      int randX = (int) (Math.random() * 9);
      int randY = (int) (Math.random() * 9);
      if (board[randX][randY] == null) {
        board[randX][randY] = new Field(FieldTypes.MINE, randX, randY);
        minesPlaced++;
      }
    }
  }
}
