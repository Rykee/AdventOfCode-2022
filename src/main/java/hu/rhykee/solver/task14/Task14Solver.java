package hu.rhykee.solver.task14;

import hu.rhykee.solver.Challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Task14Solver implements Challenge {

  @Override
  public void part1(List<String> lines) {
    AtomicInteger smallestX = new AtomicInteger(Integer.MAX_VALUE);
    AtomicInteger smallestY = new AtomicInteger(0);
    AtomicInteger biggestX = new AtomicInteger(Integer.MIN_VALUE);
    AtomicInteger biggestY = new AtomicInteger(Integer.MIN_VALUE);
    List<Line> allLines = lines.stream()
        .map(s -> {
          List<Coordinate> coordinates = Arrays.stream(s.split(" -> "))
              .map(coords -> {
                String[] parts = coords.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                if (x < smallestX.get()) {
                  smallestX.set(x);
                }
                if (x > biggestX.get()) {
                  biggestX.set(x);
                }
                if (y < smallestY.get()) {
                  smallestY.set(y);
                }
                if (y > biggestY.get()) {
                  biggestY.set(y);
                }
                return new Coordinate(x, y);
              })
              .toList();
          List<Line> lineList = new ArrayList<>();
          for (int i = 0; i < coordinates.size() - 1; i++) {
            lineList.add(new Line(coordinates.get(i), coordinates.get(i + 1)));
          }
          return lineList;
        })
        .flatMap(Collection::stream)
        .toList();
    allLines.forEach(line -> {
      line.setStartPoint(normalizeCoordinate(line.startPoint(), smallestX.get(), smallestY.get()));
      line.setEndPoint(normalizeCoordinate(line.endPoint(), smallestX.get(), smallestY.get()));
      Coordinate startCoord = line.startPoint();
      Coordinate endCoord = line.endPoint();
      if (startCoord.x() == endCoord.x() && startCoord.y() > endCoord.y()) {
        Coordinate temp = startCoord;
        line.setStartPoint(endCoord);
        line.setEndPoint(temp);
      } else if (startCoord.x() > endCoord.x()) {
        Coordinate temp = startCoord;
        line.setStartPoint(endCoord);
        line.setEndPoint(temp);
      }
    });
    Board board = new Board(biggestY.get() - smallestY.get() + 1, biggestX.get() - smallestX.get() + 1, allLines, 500 - smallestX.get());
    board.drawThatShit();
    int solve = board.solvePart1();
    board.drawThatShit();
    System.out.println(solve);
    Board boardPart2 = new Board(biggestY.get() - smallestY.get() + 1, biggestX.get() - smallestX.get() + 1, allLines, 500 - smallestX.get(), true);
   solve = boardPart2.solvePart2();
    boardPart2.drawThatShit();
    System.out.println(solve);
    System.out.println("something something");
  }

  private Coordinate normalizeCoordinate(Coordinate coordinate, int x, int y) {
    return new Coordinate(coordinate.x() - x, coordinate.y() - y);
  }

  @Override
  public void part2(List<String> lines) {

  }

}
