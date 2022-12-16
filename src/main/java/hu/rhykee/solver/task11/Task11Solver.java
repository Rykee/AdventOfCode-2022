package hu.rhykee.solver.task11;

import hu.rhykee.solver.Challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Task11Solver implements Challenge {

  private static final Pattern TEST_PATTERN = Pattern.compile("\s+If (true|false): throw to monkey (?<target>\\d)");
  private static final Pattern DIVISIBLE_PATTERN = Pattern.compile("\s+Test: divisible by (?<number>\\d+)");

  @Override
  public void part1(List<String> lines) {
    String input = String.join("\n", lines);
    Map<Integer, Monkey> monkeys = Arrays.stream(input.split("\n\n"))
        .map(this::createMonkey)
        .peek(monkey -> monkey.setOperation(monkey.getOperation().andThen(itemValue -> itemValue / 3)))
        .collect(Collectors.toMap(Monkey::getId, Function.identity()));
    Matcher divisibles = DIVISIBLE_PATTERN.matcher(input);
    AtomicLong primeProduct = new AtomicLong(1L);
    while (divisibles.find()) {
      primeProduct.set(primeProduct.get() * Integer.parseInt(divisibles.group("number")));
    }
    for (int i = 0; i < 20; i++) {
      monkeys.values().forEach(monkey -> monkey.doRound(monkeys, worryLevel -> worryLevel/3L));
    }
    Long answer = monkeys.values().stream()
        .sorted((o1, o2) -> Long.compare(o2.getInspectionCount(), o1.getInspectionCount()))
        .limit(2)
        .map(Monkey::getInspectionCount)
        .reduce((monkey, monkey2) -> monkey * monkey2)
        .get();
    System.out.println(answer);
  }

  @Override
  public void part2(List<String> lines) {
    String input = String.join("\n", lines);
    Map<Integer, Monkey> monkeys = Arrays.stream(input.split("\n\n"))
        .map(this::createMonkey)
        .collect(Collectors.toMap(Monkey::getId, Function.identity()));
    Matcher divisibles = DIVISIBLE_PATTERN.matcher(input);
    AtomicLong primeProduct = new AtomicLong(1L);
    while (divisibles.find()) {
      primeProduct.set(primeProduct.get() * Integer.parseInt(divisibles.group("number")));
    }
    for (int i = 0; i < 10000; i++) {
      monkeys.values().forEach(monkey -> monkey.doRound(monkeys, itemValue -> itemValue % primeProduct.get()));
    }
    Long answer = monkeys.values().stream()
        .sorted((o1, o2) -> Long.compare(o2.getInspectionCount(), o1.getInspectionCount()))
        .limit(2)
        .map(Monkey::getInspectionCount)
        .reduce((monkey, monkey2) -> monkey * monkey2)
        .get();
    System.out.println(answer);
  }

  private Monkey createMonkey(String s) {
    String[] monkeyParts = s.split("\n");
    int monkeyId = Integer.parseInt(monkeyParts[0].substring(7, 8));
    List<Long> items = Arrays.stream(monkeyParts[1].substring(18).split(","))
        .map(String::trim)
        .map(Long::parseLong).toList();
    Function<Long, Long> operation = item -> {
      String operationPart = monkeyParts[2].substring(23);
      String operandString = operationPart.substring(2);
      long operand = operandString.equals("old") ? item : Integer.parseInt(operandString);
      return switch (operationPart.charAt(0)) {
        case '+' -> item + operand;
        case '*' -> item * operand;
        default -> throw new IllegalStateException("Unexpected value: " + operationPart.charAt(0));
      };
    };
    Predicate<Long> test = item -> item % Integer.parseInt(monkeyParts[3].substring(21)) == 0;
    Matcher matcher = TEST_PATTERN.matcher(monkeyParts[4]);
    matcher.find();
    int targetIfTrue = Integer.parseInt(matcher.group("target"));
    matcher = TEST_PATTERN.matcher(monkeyParts[5]);
    matcher.find();
    int targetIfFalse = Integer.parseInt(matcher.group("target"));
    return new Monkey(monkeyId, new ArrayList<>(items), operation, test, targetIfTrue, targetIfFalse);
  }

}
