package adventofcode.year2015.day1;

import adventofcode.BaseSolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class Solver extends BaseSolver<Integer> {
    private static final HashMap<Character, Integer> FLOOR_CHANGE_MAP = new HashMap<>() {{
        put('(', 1);
        put(')', -1);
    }};

    public Solver() {
        super();
    }

    public Solver(String inputFilePath) {
        super(inputFilePath);
    }

    @Override
    protected List<Answer> solve(FileReader inputReader) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(inputReader);

        int floor = 0;
        int i = 0;
        int basementReachingIndex = -1;
        int c;
        while ((c = bufferedReader.read()) != -1 && c != '\n') {
            ++i;
            floor += FLOOR_CHANGE_MAP.get((char) c);
            if (floor == -1 && basementReachingIndex == -1) {
                basementReachingIndex = i;
            }
        }

        return new ArrayList<>(Arrays.asList(
                new Answer("Santa floor", floor),
                new Answer("Santa basement reaching position", basementReachingIndex)
        ));
    }
}
