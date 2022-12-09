package hu.rhykee.solver.task09;

import hu.rhykee.solver.Challenge;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;

public class Task09Solver implements Challenge {

  @Override
  public void part1(List<String> lines) {
    Set<Coordinate> visitedByTail = new HashSet<>();
    Coordinate head = new Coordinate(0, 0);
    Coordinate tail = new Coordinate(0, 0);
    visitedByTail.add(new Coordinate(0, 0));
    lines.stream()
        .map(move -> {
          String[] parts = move.split(" ");
          return new Move(Direction.fromLabel(parts[0]), Integer.parseInt(parts[1]));
        })
        .forEach(move -> {
          head.applyMove(move);
          while (!head.isAdjacent(tail)) {
            visitedByTail.add(followHead(head, tail));
          }
        });
    System.out.println(visitedByTail.size());
  }

  @Override
  public void part2(List<String> lines) {
    Set<Coordinate> visitedByTail = new HashSet<>();
    Coordinate head = new Coordinate(0, 0);
    List<Coordinate> tails = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      tails.add(new Coordinate(0, 0));
    }
    visitedByTail.add(new Coordinate(0, 0));
    lines.stream()
        .map(move -> {
          String[] parts = move.split(" ");
          return new Move(Direction.fromLabel(parts[0]), Integer.parseInt(parts[1]));
        })
        .forEach(move -> {
          head.applyMove(move);
          while (!head.isAdjacent(tails.get(0))) {
            visitedByTail.add(followHead(head, tails));
          }
        });
    System.out.println(visitedByTail.size());
  }

  private Coordinate followHead(Coordinate head, List<Coordinate> tails) {
    followHead(head, tails.get(0));
    for (int i = 1; i < 8; i++) {
      followHead(tails.get(i - 1), tails.get(i));
    }
    return followHead(tails.get(7), tails.get(8));
  }

  private Coordinate followHead(Coordinate head, Coordinate tail) {
    if (head.equals(tail) || head.isAdjacent(tail)) {
      return new Coordinate(tail.x, tail.y);
    }
    if (head.x == tail.x) {
      if (head.y > tail.y) {
        tail.y++;
      } else if (head.y < tail.y) {
        tail.y--;
      }
    } else if (head.y == tail.y) {
      if (head.x > tail.x) {
        tail.x++;
      } else {
        tail.x--;
      }
    } else {
      Coordinate moveVector = getMoveVector(head, tail);
      tail.applyVector(moveVector);
    }
    return new Coordinate(tail.x, tail.y);
  }

  private Coordinate getMoveVector(Coordinate head, Coordinate tail) {
    int deltaX = head.x - tail.x;
    int deltaY = head.y - tail.y;
    return new Coordinate(deltaX / Math.abs(deltaX), deltaY / Math.abs(deltaY));
  }

  private static enum Direction {
    UP("U"),
    DOWN("D"),
    RIGHT("R"),
    LEFT("L");

    final String label;

    Direction(String label) {
      this.label = label;
    }

    public static Direction fromLabel(String label) {
      return Arrays.stream(values())
          .filter(direction -> direction.label.equals(label))
          .findAny()
          .orElseThrow(InputMismatchException::new);
    }

  }

  private record Move(Direction direction, int amount) {

  }

  @Getter
  @EqualsAndHashCode
  private static class Coordinate {

    private static final List<Coordinate> ADJACENCY_VECTORS = createAdjacencyVectors();

    private static List<Coordinate> createAdjacencyVectors() {
      List<Coordinate> vectors = new ArrayList<>();
      for (int x = -1; x <= 1; x++) {
        for (int y = -1; y <= 1; y++) {
          vectors.add(new Coordinate(x, y));
        }
      }
      return vectors;
    }

    private int x;
    private int y;

    public Coordinate(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public void applyMove(Move move) {
      switch (move.direction) {
        case UP -> y += move.amount;
        case DOWN -> y -= move.amount;
        case RIGHT -> x += move.amount;
        case LEFT -> x -= move.amount;
      }
    }

    public Coordinate applyVectorNewCoordinate(Coordinate vector) {
      return new Coordinate(this.x + vector.x, this.y + vector.y);
    }

    public void applyVector(Coordinate vector) {
      this.x = this.x + vector.x;
      this.y = this.y + vector.y;
    }

    public boolean isAdjacent(Coordinate other) {
      for (Coordinate adjacencyVector : ADJACENCY_VECTORS) {
        if (applyVectorNewCoordinate(adjacencyVector).equals(other)) {
          return true;
        }
      }
      return false;
    }

  }

}
