package hu.rhykee.solver.task01;

import hu.rhykee.solver.Challenge;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Task01Solver implements Challenge {
    @Override
    public void part1(List<String> lines) {
        String all = String.join("\n", lines);
        Optional<Long> max = Arrays.stream(all.split("\n\n"))
                .map(s -> Arrays.stream(s.split("\n"))
                        .map(Long::parseLong)
                        .reduce(Long::sum)
                        .get())
                .max(Long::compareTo);
        System.out.println(max.get());

    }

    @Override
    public void part2(List<String> lines) {
        String all = String.join("\n", lines);
        List<Long> calories = Arrays.stream(all.split("\n\n"))
                .map(s -> Arrays.stream(s.split("\n"))
                        .map(Long::parseLong)
                        .reduce(Long::sum)
                        .get())
                .sorted(Long::compareTo)
                .toList();
        int lastIndex = calories.size() - 1;
        System.out.println(calories.get(lastIndex)+calories.get(lastIndex-1)+calories.get(lastIndex-2));
    }
}
