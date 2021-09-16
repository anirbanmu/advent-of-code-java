package adventofcode.year2015.day6;

import adventofcode.BaseSolver;
import adventofcode.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

public class Solver extends BaseSolver<Long> {
    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("(turn\\son|turn\\soff|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)");

    private static OpCode stringToOpCode(String s) {
        if (s.equals("turn on")) {
            return OpCode.ON;
        }

        if (s.equals("turn off")) {
            return OpCode.OFF;
        }

        return OpCode.TOGGLE;
    }

    private static Instruction parseInstruction(String s) {
        final var matcher = INSTRUCTION_PATTERN.matcher(s);
        if (!matcher.matches()) {
            throw new RuntimeException("malformed day 6 instruction!");
        }

        final var opCode = stringToOpCode(matcher.group(1));
        final var rect = new Rect(
                new Point(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3))),
                new Point(Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)))
        );

        return new Instruction(opCode, rect);
    }

    @Override
    protected List<Answer> solve(BufferedReader input) throws IOException {
        final var bitGrid = new BitGrid(1000, 1000);
        final var brightnessGrid = new BrightnessGrid(1000, 1000);

        String line;
        while ((line = input.readLine()) != null) {
            final var instruction = parseInstruction(line);

            switch (instruction.opCode) {
                case ON:
                    for (long y = instruction.rect.start.y; y <= instruction.rect.end.y; ++y) {
                        for (long x = instruction.rect.start.x; x <= instruction.rect.end.x; ++x) {
                            bitGrid.set((int) x, (int) y);
                            brightnessGrid.increase((int) x, (int) y, 1);
                        }
                    }
                    break;
                case OFF:
                    for (long y = instruction.rect.start.y; y <= instruction.rect.end.y; ++y) {
                        for (long x = instruction.rect.start.x; x <= instruction.rect.end.x; ++x) {
                            bitGrid.reset((int) x, (int) y);
                            brightnessGrid.decrease((int) x, (int) y, 1);
                        }
                    }
                    break;
                case TOGGLE:
                    for (long y = instruction.rect.start.y; y <= instruction.rect.end.y; ++y) {
                        for (long x = instruction.rect.start.x; x <= instruction.rect.end.x; ++x) {
                            bitGrid.toggle((int) x, (int) y);
                            brightnessGrid.increase((int) x, (int) y, 2);
                        }
                    }
                    break;
            }
        }

        return new ArrayList<>(Arrays.asList(
                new Answer("Total lights lit", (long) bitGrid.cardinality()),
                new Answer("Total brightness", brightnessGrid.total())
        ));
    }

    private enum OpCode {
        ON, OFF, TOGGLE
    }

    private static final class Rect {
        public Point start, end;

        public Rect(Point start, Point end) {
            this.start = new Point(start.x, start.y);
            this.end = new Point(end.x, end.y);
        }
    }

    private static final class Instruction {
        public OpCode opCode;
        public Rect rect;

        public Instruction(OpCode opCode, Rect rect) {
            this.opCode = opCode;
            this.rect = new Rect(new Point(rect.start.x, rect.start.y), new Point(rect.end.x, rect.end.y));
        }
    }

    private static final class BitGrid {
        private int width, height, total;
        private boolean[] grid;

        public BitGrid(int width, int height) {
            this.width = width;
            this.height = height;
            this.total = width * height;
            this.grid = new boolean[total];
        }

        private int idx(int x, int y) {
            return (y * width) + x;
        }

        public BitGrid set(int x, int y) {
            grid[(idx(x, y))] = true;
            return this;
        }

        public BitGrid reset(int x, int y) {
            grid[idx(x, y)] = false;
            return this;
        }

        public BitGrid toggle(int x, int y) {
            final var i = idx(x, y);
            grid[i] = !grid[i];
            return this;
        }

        public int cardinality() {
            int trueCount = 0;
            for (int i = 0; i < total; ++i) {
                trueCount += grid[i] ? 1 : 0;
            }
            return trueCount;
        }
    }

    private static final class BrightnessGrid {
        private int width, height;
        private long[] brightness;

        public BrightnessGrid(int width, int height) {
            this.width = width;
            this.height = height;
            this.brightness = new long[width * height];
        }

        private int idx(int x, int y) {
            return (y * width) + x;
        }

        public BrightnessGrid increase(int x, int y, long change) {
            final var i = idx(x, y);
            brightness[i] = brightness[i] + change;
            return this;
        }

        public BrightnessGrid decrease(int x, int y, long change) {
            final var i = idx(x, y);
            final var current = brightness[i];
            brightness[i] = change > current ? 0 : current - change;
            return this;
        }

        public long total() {
            return LongStream.of(brightness).sum();
        }
    }
}
