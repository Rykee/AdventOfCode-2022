package hu.rhykee.solver.task08;

import hu.rhykee.solver.Challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task08Solver implements Challenge {

  @Override
  public void part1(List<String> lines) {
    List<List<Integer>> trees = new ArrayList<>();
    lines.forEach(line -> {
      List<Integer> treeRow = new ArrayList<>();
      Arrays.stream(line.split(""))
          .forEach(tree -> treeRow.add(Integer.parseInt(tree)));
      trees.add(treeRow);
    });
    long visibleTrees = trees.size() * 2L + trees.get(0).size() * 2L - 4;
    for (int i = 1; i < trees.size() - 1; i++) {
      for (int j = 1; j < trees.get(i).size() - 1; j++) {
        if (checkLeft(i, j, trees) || checkRight(i, j, trees) || checkUp(i, j, trees) || checkDown(i, j, trees)) {
          visibleTrees++;
        }
      }
    }
    System.out.println(visibleTrees);
  }

  private boolean checkLeft(int i, int j, List<List<Integer>> trees) {
    int currentHeight = trees.get(i).get(j);
    for (int k = j - 1; k >= 0; k--) {
      if (!(trees.get(i).get(k) < currentHeight)) {
        return false;
      }
    }
    return true;
  }

  private boolean checkRight(int i, int j, List<List<Integer>> trees) {
    int currentHeight = trees.get(i).get(j);
    for (int k = j + 1; k < trees.get(i).size(); k++) {
      if (!(trees.get(i).get(k) < currentHeight)) {
        return false;
      }
    }
    return true;
  }

  private boolean checkUp(int i, int j, List<List<Integer>> trees) {
    int currentHeight = trees.get(i).get(j);
    for (int k = i - 1; k >= 0; k--) {
      if (!(trees.get(k).get(j) < currentHeight)) {
        return false;
      }
    }
    return true;
  }

  private boolean checkDown(int i, int j, List<List<Integer>> trees) {
    int currentHeight = trees.get(i).get(j);
    for (int k = i + 1; k < trees.size(); k++) {
      if (!(trees.get(k).get(j) < currentHeight)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void part2(List<String> lines) {
    List<List<Integer>> trees = new ArrayList<>();
    lines.forEach(line -> {
      List<Integer> treeRow = new ArrayList<>();
      Arrays.stream(line.split(""))
          .forEach(tree -> treeRow.add(Integer.parseInt(tree)));
      trees.add(treeRow);
    });
    int highestScenicScore = 0;
    for (int i = 1; i < trees.size() - 1; i++) {
      for (int j = 1; j < trees.get(i).size() - 1; j++) {
        int scenicScore = getScenicScore(i, j, trees);
        if (scenicScore > highestScenicScore) {
          highestScenicScore = scenicScore;
        }
      }
    }
    System.out.println(highestScenicScore);
  }

  private int getScenicScore(int i, int j, List<List<Integer>> trees) {
    int left = 0, right = 0, up = 0, down = 0;
    Integer currentHeight = trees.get(i).get(j);
    for (int k = j + 1; k < trees.get(i).size(); k++) {
      right++;
      if (trees.get(i).get(k) >= currentHeight) {
        break;
      }
    }

    for (int k = j-1; k >=0 ; k--) {
      left++;
      if (trees.get(i).get(k) >= currentHeight) {
        break;
      }
    }

    for (int k = i-1; k >=0 ; k--) {
      up++;
      if (trees.get(k).get(j) >= currentHeight) {
        break;
      }
    }

    for (int k = i + 1; k < trees.size(); k++) {
      down++;
      if (trees.get(k).get(j) >= currentHeight) {
        break;
      }
    }
    return left * right * up * down;
  }

}
