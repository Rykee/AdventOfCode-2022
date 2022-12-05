package hu.rhykee.solver.task04;

import hu.rhykee.solver.Challenge;

import java.util.List;

public class Task04Solver implements Challenge {
  @Override
  public void part1(List<String> lines) {
    long overlaps = lines.stream()
        .filter(s -> {
          String[] parts = s.split(",");
          Range range = new Range(parts[0]);
          Range otherRange = new Range(parts[1]);
          return range.isFullyOverlapping(otherRange);
        })
        .count();
    System.out.println(overlaps);
  }

  @Override
  public void part2(List<String> lines) {
    long overlaps = lines.stream()
        .filter(s -> {
          String[] parts = s.split(",");
          Range range = new Range(parts[0]);
          Range otherRange = new Range(parts[1]);
          return range.isAnyOverlap(otherRange);
        })
        .count();
    System.out.println(overlaps);
  }

  private static class Range {
    private final int left;
    private final int right;

    public Range(String input) {
      String[] parts = input.split("-");
      left = Integer.parseInt(parts[0]);
      right = Integer.parseInt(parts[1]);
    }

    public boolean isFullyOverlapping(Range otherRange) {
      return (left <= otherRange.left && right >= otherRange.right)
          || otherRange.left <= left && otherRange.right >= right;
    }

    public boolean isAnyOverlap(Range otherRange){
      return otherRange.left<=left && otherRange.right>=left
          || otherRange.left>=left && otherRange.left <=right;
    }

  }

}
