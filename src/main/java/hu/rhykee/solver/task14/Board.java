package hu.rhykee.solver.task14;

import java.util.Arrays;
import java.util.List;

import static hu.rhykee.solver.task14.Board.Direction.DOWN;
import static hu.rhykee.solver.task14.Board.Direction.LEFT;
import static hu.rhykee.solver.task14.Board.Direction.RIGHT;

public class Board {

  private char[][] chars;
  private final Coordinate sandStartingLocation;

  public Board(int rowSize, int columnSize, List<Line> lines, int sandLocation) {
    chars = new char[rowSize][columnSize];
    sandStartingLocation = new Coordinate(sandLocation, 0);
    initBoard();
    lines.forEach(line -> {
      int startX = line.startPoint().x();
      int endX = line.endPoint().x();
      int startY = line.startPoint().y();
      int endY = line.endPoint().y();
      if (startX == endX) {
        for (int i = startY; i <= endY; i++) {
          chars[i][startX] = '#';
        }
      } else {
        for (int i = startX; i <= endX; i++) {
          chars[startY][i] = '#';
        }
      }
    });
    chars[0][sandLocation] = '+';
  }

  public Board(int rowSize, int columnSize, List<Line> lines, int sandLocation, boolean b) {
    int deltaX = rowSize * 4;
    chars = new char[rowSize + 2][columnSize + deltaX];
    sandStartingLocation = new Coordinate(sandLocation + deltaX / 2, 0);
    initBoard();
    Arrays.fill(chars[rowSize + 1], '#');
    lines.forEach(line -> {
      int startX = line.startPoint().x() + deltaX / 2;
      int endX = line.endPoint().x() + deltaX / 2;
      int startY = line.startPoint().y();
      int endY = line.endPoint().y();
      if (startX == endX) {
        for (int i = startY; i <= endY; i++) {
          chars[i][startX] = '#';
        }
      } else {
        for (int i = startX; i <= endX; i++) {
          chars[startY][i] = '#';
        }
      }
    });
    chars[0][sandLocation + deltaX / 2] = '+';
  }

  private void initBoard() {
    for (char[] line : chars) {
      Arrays.fill(line, '.');
    }
  }

  public int solvePart1() {
    int sandCurrentX = sandStartingLocation.x();
    int sandCurrentY = sandStartingLocation.y();
    int count = 0;
    while (true) {
      //down
      if (notInBound(sandCurrentX, sandCurrentY, DOWN)) {
        break;
      }
      if (chars[sandCurrentY + 1][sandCurrentX] == '.') {
        sandCurrentY++;
        continue;
      }
      //left-down
      if (notInBound(sandCurrentX, sandCurrentY, LEFT)) {
        break;
      }
      if (chars[sandCurrentY + 1][sandCurrentX - 1] == '.') {
        sandCurrentY++;
        sandCurrentX--;
        continue;
      }

      //right-down
      if (notInBound(sandCurrentX, sandCurrentY, RIGHT)) {
        break;
      }
      if (chars[sandCurrentY + 1][sandCurrentX + 1] == '.') {
        sandCurrentY++;
        sandCurrentX++;
        continue;
      }
      chars[sandCurrentY][sandCurrentX] = 'o';
      count++;
      sandCurrentX = sandStartingLocation.x();
      sandCurrentY = sandStartingLocation.y();
    }
    return count;
  }

  public int solvePart2() {
    int sandCurrentX = sandStartingLocation.x();
    int sandCurrentY = sandStartingLocation.y();
    int count = 0;
    while (true) {
      //down
      if (chars[sandCurrentY][sandCurrentX] == 'o') {
        break;
      }
      if (chars[sandCurrentY + 1][sandCurrentX] == '.') {
        sandCurrentY++;
        continue;
      }
      //left-down
      if (chars[sandCurrentY + 1][sandCurrentX - 1] == '.') {
        sandCurrentY++;
        sandCurrentX--;
        continue;
      }

      //right-down
      if (chars[sandCurrentY + 1][sandCurrentX + 1] == '.') {
        sandCurrentY++;
        sandCurrentX++;
        continue;
      }
      chars[sandCurrentY][sandCurrentX] = 'o';
      count++;
      sandCurrentX = sandStartingLocation.x();
      sandCurrentY = sandStartingLocation.y();
    }
    return count;
  }

  private boolean notInBound(int sandCurrentX, int sandCurrentY, Direction direction) {
    return switch (direction) {
      case DOWN -> sandCurrentY + 1 > chars.length - 1;
      case LEFT -> sandCurrentY + 1 > chars.length - 1 || sandCurrentX - 1 < 0;
      case RIGHT -> sandCurrentY + 1 > chars.length - 1 || sandCurrentX + 1 > chars[sandCurrentY + 1].length - 1;
    };
  }

  public void drawThatShit() {
    for (int i = 0; i < chars.length; i++) {
      char[] line = chars[i];
      System.out.print(i + 1);
      for (char c : line) {
        System.out.print(c);
      }
      System.out.println();
    }
  }

  public static enum Direction {
    DOWN,
    LEFT,
    RIGHT
  }

}
