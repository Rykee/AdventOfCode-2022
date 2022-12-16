package hu.rhykee.solver.task10;

import hu.rhykee.solver.Challenge;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task10Solver implements Challenge {

  private static final Pattern ADDX = Pattern.compile("addx (?<amount>-?\\d+)");

  @Override
  public void part1(List<String> lines) {
    AtomicInteger cycle = new AtomicInteger(0);
    AtomicInteger x = new AtomicInteger(1);
    AtomicInteger sum = new AtomicInteger(0);
    lines.forEach(s -> {
      if (s.equals("noop")) {
        cycle.addAndGet(1);
        if (cycle.get() == 20 || (cycle.get() - 20) % 40 == 0) {
          sum.addAndGet(cycle.get() * x.get());
        }
        return;
      }
      Matcher matcher = ADDX.matcher(s);
      if (matcher.matches()) {
        int cycleStart = cycle.get() + 1;
        int amount = Integer.parseInt(matcher.group("amount"));
        if (cycleStart == 19 || cycleStart == 20) {
          sum.addAndGet(20 * x.get());
        } else {
          int remainder = (cycleStart - 20) % 40;
          if (remainder == 0) {
            sum.addAndGet(cycleStart * x.get());
          } else if (remainder == 39) {
            sum.addAndGet((cycleStart + 1) * x.get());
          }
        }
        x.addAndGet(amount);
        cycle.addAndGet(2);
      }
    });
    System.out.println(sum);
  }

  @Override
  public void part2(List<String> lines) {
    AtomicInteger cycle = new AtomicInteger(0);
    AtomicInteger x = new AtomicInteger(1);
    lines.forEach(s -> {
      if (s.equals("noop")) {
        cycle.addAndGet(1);
        writePixel(cycle, x);
        return;
      }
      Matcher matcher = ADDX.matcher(s);
      if (matcher.matches()) {
        cycle.addAndGet(1);
        writePixel(cycle, x);
        cycle.addAndGet(1);
        writePixel(cycle, x);
        int amount = Integer.parseInt(matcher.group("amount"));
        x.addAndGet(amount);
      }
    });
  }

  private void writePixel(AtomicInteger cycle, AtomicInteger x) {
    int currentPosition = (cycle.get() - 1) % 40;
    if (currentPosition >= x.get() - 1 && currentPosition <= x.get() + 1) {
      System.out.print("\uD83C\uDF81");
    } else {
      System.out.print("\uD83C\uDF84");
    }
    if (currentPosition == 39) {
      System.out.println();
    }
  }

}
