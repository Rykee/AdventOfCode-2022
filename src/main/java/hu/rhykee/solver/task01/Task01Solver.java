package hu.rhykee.solver.task01;

import hu.rhykee.solver.Challenge;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Task01Solver implements Challenge {
    @Override
    public void part1(List<String> lines) {
        String all = String.join("\n", lines);
        System.out.println(Arrays.stream(all.split("\n\n"))
                .mapToLong(s -> Arrays.stream(s.split("\n"))
                        .mapToLong(Long::parseLong)
                        .sum())
                .max()
                .getAsLong());
    }

    @Override
    public void part2(List<String> lines) {
        String all = String.join("\n", lines);
        System.out.println(Arrays.stream(all.split("\n\n"))
                .map(s -> Arrays.stream(s.split("\n"))
                        .mapToLong(Long::parseLong)
                        .sum())
                .sorted(Comparator.reverseOrder())
                .mapToLong(Long::longValue)
                .limit(3)
                .sum());
    }
}
