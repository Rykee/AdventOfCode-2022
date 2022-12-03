package hu.rhykee.solver.task03;

import hu.rhykee.solver.Challenge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task03Solver implements Challenge {

  @Override
  public void part1(List<String> lines) {
    long sum = lines.stream()
        .map(Rucksack::new)
        .mapToLong(Rucksack::getInvalidCharValue)
        .sum();
    System.out.println(sum);
  }

  @Override
  public void part2(List<String> lines) {
    List<Rucksack> rucksacks = lines.stream()
        .map(Rucksack::new)
        .toList();

    long sum = 0;

    for (int i = 0; i < rucksacks.size() - 2; i += 3) {
      sum += rucksacks.get(i).compareRucksacks(rucksacks.get(i + 1), rucksacks.get(i + 2));
    }
    System.out.println(sum);
  }

  private static class Rucksack {

    Set<Character> left;
    Set<Character> right;
    Set<Character> all;

    public Rucksack(String line) {
      left = convertToSet(line.substring(0, line.length() / 2).toCharArray());
      right = convertToSet(line.substring(line.length() / 2).toCharArray());
      all = convertToSet(line.toCharArray());
    }

    public long getInvalidCharValue() {
      Set<Character> wrongChar = new HashSet<>(left);
      wrongChar.retainAll(right);
      return wrongChar.stream()
          .mapToLong(this::calculateValue)
          .findAny()
          .getAsLong();
    }

    private int calculateValue(Character character) {
      int value = Character.getNumericValue(character) - 9;
      return Character.isUpperCase(character) ? 26 + value : value;
    }

    public long compareRucksacks(Rucksack other, Rucksack other2) {
      Set<Character> badge = new HashSet<>(all);
      badge.retainAll(other.all);
      badge.retainAll(other2.all);
      return badge.stream()
          .mapToLong(this::calculateValue)
          .findAny()
          .getAsLong();
    }

    private Set<Character> convertToSet(char[] chars) {
      Set<Character> characters = new HashSet<>();
      for (char character : chars) {
        characters.add(character);
      }
      return characters;
    }

  }

}
