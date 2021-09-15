package adventofcode.year2015.day3;

import adventofcode.BaseSolver;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.function.UnaryOperator;

public final class Solver extends BaseSolver<Integer> {
    static final HashMap<Character, UnaryOperator<Point>> ACTIONS = new HashMap<>() {{
        put('>', (p) -> new Point(p.getX() + 1, p.getY()));
        put('<', (p) -> new Point(p.getX() - 1, p.getY()));
        put('^', (p) -> new Point(p.getX(), p.getY() + 1));
        put('v', (p) -> new Point(p.getX(), p.getY() - 1));
    }};

    public Solver() {
        super();
    }

    public Solver(String inputFilePath) {
        super(inputFilePath);
    }

    @Override
    protected List<Answer> solve(BufferedReader bufferedReader) throws IOException {
        final var housesWithPresents = new Object() {
            public HashSet<Point> part1, part2;

            {
                part1 = new HashSet<>();
                part2 = new HashSet<>();
            }
        };

        final var currentPosition = new Object() {
            public Point part1Santa, part2Santa, part2Robo;

            {
                part1Santa = new Point(0L, 0L);
                part2Santa = new Point(0L, 0L);
                part2Robo = new Point(0L, 0L);
            }
        };

        var roboTurn = false;
        int c;
        while ((c = bufferedReader.read()) != -1 && c != '\n') {
            var action = ACTIONS.get((char) c);
            currentPosition.part1Santa = action.apply(currentPosition.part1Santa);
            housesWithPresents.part1.add(currentPosition.part1Santa);

            if (roboTurn) {
                currentPosition.part2Robo = action.apply(currentPosition.part2Robo);
                housesWithPresents.part2.add(currentPosition.part2Robo);
            } else {
                currentPosition.part2Santa = action.apply(currentPosition.part2Santa);
                housesWithPresents.part2.add(currentPosition.part2Santa);
            }
            roboTurn = !roboTurn;
        }

        return new ArrayList<>(Arrays.asList(
                new Answer("Houses delivered with just Santa", housesWithPresents.part1.size()),
                new Answer("Houses delivered with Santa & Robo", housesWithPresents.part2.size())
        ));
    }

    static final class Point extends Pair<Long, Long> {
        public Point(Long key, Long value) {
            super(key, value);
        }

        public long getX() {
            return getKey();
        }

        public long getY() {
            return getValue();
        }
    }
}
