package logic;

public class Field {
  public final FieldTypes type;
  public int r;
  public int c;
  int neighborMines;
  boolean uncovered;
  boolean flagged;
  
  public Field(FieldTypes t, int r, int c) {
    this.type = t;
    this.r = r;
    this.c = c;
    this.neighborMines = 0;
    this.flagged = false;
    this.uncovered = false;
  }

  public int getNeighborMines() {
    return neighborMines;
  }

  public boolean isFlaggedd() {
    return flagged;
  }

  public boolean isUncovered() {
    return uncovered;
  }
  
  public void setFlagged(boolean val) {
    flagged = val;
  }

  public void setNeihborMines(int num) {
    neighborMines = num;
  }

  public void setUncovered() {
    uncovered = true;
  }
}
