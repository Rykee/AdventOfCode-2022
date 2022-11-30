package hu.rhykee;


import hu.rhykee.solver.Challenge;
import hu.rhykee.util.ReflectionUtils;

import java.util.List;

import static hu.rhykee.util.HttpUtils.getInput;

public class Main {

    public static void main(String[] args) {
        String cookie = args[0];
        for (int i = 1; i <= 18; i++) {
            Challenge solverByDay = ReflectionUtils.getSolverByDay(i);
            List<String> input = getInput(i, cookie);
            solverByDay.part1(input);
            solverByDay.part2(input);
        }

    }

}
