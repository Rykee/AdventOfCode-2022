package hu.rhykee.solver.task05;

import hu.rhykee.solver.Challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task05Solver implements Challenge {

  private static final Pattern BOX_PATTERN = Pattern.compile("\\[(?<box>[A-Z]?)");
  private static final Pattern MOVE_PATTERN = Pattern.compile("move (?<amount>\\d+) from (?<from>\\d+) to (?<to>\\d+)");

  @Override
  public void part1(List<String> lines) {
    Map<Integer, Stack<Character>> piles = new HashMap<>();
    for (int i = 1; i <= 9; i++) {
      piles.put(i, new Stack<>());
    }

    lines.stream()
        .limit(8)
        .forEach(s -> {
          String normalized = s.replace("    ", "[]").replaceAll("\s*", ""); //[][P][][][][][][Q][][T]
          Matcher matcher = BOX_PATTERN.matcher(normalized);
          int pileIndex = 1;
          while (matcher.find()){
            String box = matcher.group("box");
            if(!box.isBlank()){
              piles.get(pileIndex).push(box.charAt(0));
            }
            pileIndex++;
          }
        });
    for (int i = 1; i <= 9; i++) {
      Collections.reverse(piles.get(i));
    }
    lines.stream().skip(10)
        .forEach(s -> {
          Matcher matcher = MOVE_PATTERN.matcher(s);
          matcher.find();
          int amount = Integer.parseInt(matcher.group("amount"));
          int from = Integer.parseInt(matcher.group("from"));
          int to = Integer.parseInt(matcher.group("to"));
          for (int i = 0; i < amount; i++) {
            Character box = piles.get(from).pop();
            piles.get(to).push(box);
          }
        });
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <=9 ; i++) {
      sb.append(piles.get(i).pop());
    }
    System.out.println(sb);
  }

  @Override
  public void part2(List<String> lines) {
    Map<Integer, Stack<Character>> piles = new HashMap<>();
    for (int i = 1; i <= 9; i++) {
      piles.put(i, new Stack<>());
    }

    lines.stream()
        .limit(8)
        .forEach(s -> {
          String normalized = s.replace("    ", "[]").replaceAll("\s*", ""); //[][P][][][][][][Q][][T]
          Matcher matcher = BOX_PATTERN.matcher(normalized);
          int pileIndex = 1;
          while (matcher.find()){
            String box = matcher.group("box");
            if(!box.isBlank()){
              piles.get(pileIndex).push(box.charAt(0));
            }
            pileIndex++;
          }
        });
    for (int i = 1; i <= 9; i++) {
      Collections.reverse(piles.get(i));
    }
    lines.stream().skip(10)
            .forEach(s -> {
              Matcher matcher = MOVE_PATTERN.matcher(s);
              matcher.find();
              int amount = Integer.parseInt(matcher.group("amount"));
              int from = Integer.parseInt(matcher.group("from"));
              int to = Integer.parseInt(matcher.group("to"));
              Stack<Character> stackedBoxes = piles.get(from);
              List<Character> removedBoxes = new ArrayList<>();
              for (int i = 0; i < amount; i++) {
                removedBoxes.add(stackedBoxes.pop());
              }
              Collections.reverse(removedBoxes);
              piles.get(to).addAll(removedBoxes);
            });
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <=9 ; i++) {
      sb.append(piles.get(i).pop());
    }
    System.out.println(sb);
  }

}
