package hu.rhykee.solver.task02;

import hu.rhykee.solver.Challenge;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hu.rhykee.solver.task02.Task02Solver.RockPaperScissors.PAPER;
import static hu.rhykee.solver.task02.Task02Solver.RockPaperScissors.ROCK;
import static hu.rhykee.solver.task02.Task02Solver.RockPaperScissors.SCISSORS;

public class Task02Solver implements Challenge {

    public static final Map<RockPaperScissors, RockPaperScissors> winAgainst = new HashMap<>();
    public static final Map<RockPaperScissors, RockPaperScissors> loseAgainst = new HashMap<>();

    static {
        winAgainst.put(ROCK, SCISSORS);
        winAgainst.put(PAPER, ROCK);
        winAgainst.put(SCISSORS, PAPER);
        loseAgainst.put(ROCK, PAPER);
        loseAgainst.put(PAPER, SCISSORS);
        loseAgainst.put(SCISSORS, ROCK);
    }

    @Override
    public void part1(List<String> lines) {
        long score = lines.stream()
                .mapToLong(s -> {
                    String[] parts = s.split(" ");
                    XYZ xyz = XYZ.valueOf(parts[1]);
                    return xyz.getScore() + xyz.getMatchScore(parts[0]);
                })
                .sum();
        System.out.println(score);
    }

    @Override
    public void part2(List<String> lines) {
        long sum = lines.stream()
                .mapToLong(s -> {
                    String[] parts = s.split(" ");
                    RockPaperScissors chosen = RockPaperScissors.fromLabel(parts[0]);
                    Outcome outcome = Outcome.fromLabel(parts[1]);
                    return outcome.getScore(chosen);
                })
                .sum();
        System.out.println(sum);
    }

    private enum XYZ {
        X(1),
        Y(2),
        Z(3);

        final int score;

        XYZ(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public int getMatchScore(String otherPlayerChoice) {
            return switch (otherPlayerChoice) {
                case "A" -> switch (this) {
                    case X -> 3;
                    case Y -> 6;
                    case Z -> 0;
                };
                case "B" -> switch (this) {
                    case X -> 0;
                    case Y -> 3;
                    case Z -> 6;
                };
                case "C" -> switch (this) {
                    case X -> 6;
                    case Y -> 0;
                    case Z -> 3;
                };
                default -> 0;
            };
        }
    }

    private enum Outcome {
        LOSE("X"),
        DRAW("Y"),
        WIN("Z");

        final String label;

        Outcome(String label) {
            this.label = label;
        }

        public static Outcome fromLabel(String label) {
            return Arrays.stream(values())
                    .filter(outcome -> outcome.getLabel().equalsIgnoreCase(label))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException());
        }

        public String getLabel() {
            return label;
        }

        public int getScore(RockPaperScissors chosen) {
            return switch (this) {
                case LOSE -> winAgainst.get(chosen).getScore();
                case DRAW -> chosen.getScore() + 3;
                case WIN -> loseAgainst.get(chosen).getScore() + 6;
            };
        }
    }

    public enum RockPaperScissors {
        ROCK("A", 1),
        PAPER("B", 2),
        SCISSORS("C", 3);


        final String label;
        final int score;

        RockPaperScissors(String label, int score) {
            this.label = label;
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public String getLabel() {
            return label;
        }

        public static RockPaperScissors fromLabel(String label) {
            return Arrays.stream(values())
                    .filter(outcome -> outcome.getLabel().equalsIgnoreCase(label))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException());
        }
    }

}


