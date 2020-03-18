package logic;

import java.util.ArrayList;

public class Utilities {
  public static ArrayList<int[]> calculateNeighbors(int r, int c) {
    ArrayList<int[]> neighbors = new ArrayList<int[]>();
    if (r > 0) {
      neighbors.add(Utilities.getCoordinatesArray(r-1, c));
      if (c > 0) {
        neighbors.add(Utilities.getCoordinatesArray(r-1, c-1));
      }
      if (c < 8) {
        neighbors.add(Utilities.getCoordinatesArray(r-1, c+1));
      }
    }
    if (r < 8) {
      neighbors.add(Utilities.getCoordinatesArray(r+1, c));
      if (c > 0) {
        neighbors.add(Utilities.getCoordinatesArray(r+1, c-1));
      }
      if (c < 8) {
        neighbors.add(Utilities.getCoordinatesArray(r+1, c+1));
      }
    }
    if (c > 0) {
      neighbors.add(Utilities.getCoordinatesArray(r, c-1));
    }
    if (c < 8) {
      neighbors.add(Utilities.getCoordinatesArray(r, c+1));
    }
    return neighbors;
  }

  public static int[] getCoordinatesArray(int r, int c) {
    int[] coordinates = new int[2];
    coordinates[0] = r;
    coordinates[1] = c;
    return coordinates;
  }
}