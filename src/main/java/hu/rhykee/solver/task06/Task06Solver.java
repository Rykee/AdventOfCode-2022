package hu.rhykee.solver.task06;

import hu.rhykee.solver.Challenge;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Task06Solver implements Challenge {

  @Override
  public void part1(List<String> lines) {
    Queue<Character> startSequence = new LinkedList<>();
    int charIndex = 0;
    String sequence = lines.get(0);
    while (startSequence.size() != 4) {
      char c = sequence.charAt(charIndex);
      while (startSequence.contains(c)) {
        startSequence.poll();
      }
      startSequence.add(c);
      charIndex++;
    }
    System.out.println(charIndex);
  }

  @Override
  public void part2(List<String> lines) {
    Queue<Character> startSequence = new LinkedList<>();
    int charIndex = 0;
    String sequence = lines.get(0);
    while (startSequence.size() != 14) {
      char c = sequence.charAt(charIndex);
      while (startSequence.contains(c)) {
        startSequence.poll();
      }
      startSequence.add(c);
      charIndex++;
    }
    System.out.println(charIndex);
  }

}
