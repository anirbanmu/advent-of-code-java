package adventofcode.year2015.day5;

import adventofcode.BaseSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public final class Solver extends BaseSolver<Long> {
    private static final HashSet<Character> VOWELS = new HashSet<>() {{
        addAll(Arrays.asList('a', 'e', 'i', 'o', 'u'));
    }};

    private static final HashSet<String> DISALLOWED_TWO_LENGTH_SUBSTRINGS = new HashSet<>() {{
        addAll(Arrays.asList("ab", "cd", "pq", "xy"));
    }};

    public Solver() {
        super();
    }

    public Solver(String inputFilePath) {
        super(inputFilePath);
    }

    private static Niceness isNice(String s) {
        final var part1 = new Object() {
            public long vowelCount;
            public boolean hasConsecutiveRepeatedCharacter, allTwoLengthSubstringsAllowed;

            {
                vowelCount = 0;
                hasConsecutiveRepeatedCharacter = false;
                allTwoLengthSubstringsAllowed = true;
            }
        };

        final var part2 = new Object() {
            public boolean hasNonOverlappingRepeatedTwoLengthSubstring, hasSkipCharacterRepeat;

            {
                hasNonOverlappingRepeatedTwoLengthSubstring = false;
                hasSkipCharacterRepeat = false;
            }
        };

        final var twoLengthSubstringPositions = new HashMap<String, Integer>();

        final var charCount = s.length();
        for (var i = 0; i < charCount; ++i) {
            final var current = s.charAt(i);

            // Track vowels
            if (VOWELS.contains(current)) {
                ++part1.vowelCount;
            }

            if (i < charCount - 1) {
                // Find 2 letter repeats
                final var next = s.charAt(i + 1);
                if (current == next) {
                    part1.hasConsecutiveRepeatedCharacter = true;
                }

                final var twoLengthSubstring = s.substring(i, i + 2);

                // Check for disallowed substring
                if (DISALLOWED_TWO_LENGTH_SUBSTRINGS.contains(twoLengthSubstring)) {
                    part1.allTwoLengthSubstringsAllowed = false;
                }

                // Check if we've seen this substring before, otherwise store its start position
                if (twoLengthSubstringPositions.containsKey(twoLengthSubstring)) {
                    // Check for non-overlapping two length substring repetition
                    if (twoLengthSubstringPositions.get(twoLengthSubstring) != i - 1) {
                        part2.hasNonOverlappingRepeatedTwoLengthSubstring = true;
                    }
                } else {
                    twoLengthSubstringPositions.put(twoLengthSubstring, i);
                }
            }

            if (i < charCount - 2) {
                // Check for character repeat separated by one character
                if (current == s.charAt(i + 2)) {
                    part2.hasSkipCharacterRepeat = true;
                }
            }
        }

        return new Niceness(
                part1.vowelCount >= 3 && part1.hasConsecutiveRepeatedCharacter && part1.allTwoLengthSubstringsAllowed,
                part2.hasNonOverlappingRepeatedTwoLengthSubstring && part2.hasSkipCharacterRepeat
        );
    }

    @Override
    protected List<Answer> solve(BufferedReader input) throws IOException {
        final var niceWordsCount = new Object() {
            public long part1, part2;

            {
                part1 = 0L;
                part2 = 0L;
            }
        };

        String word;
        while ((word = input.readLine()) != null) {
            final var niceness = isNice(word);
            niceWordsCount.part1 += niceness.part1 ? 1 : 0;
            niceWordsCount.part2 += niceness.part2 ? 1 : 0;
        }

        return new ArrayList<>(Arrays.asList(
                new Answer("Nice strings count", niceWordsCount.part1),
                new Answer("Nice strings count", niceWordsCount.part2)
        ));
    }

    private static final class Niceness {
        public boolean part1, part2;

        public Niceness(boolean part1, boolean part2) {
            this.part1 = part1;
            this.part2 = part2;
        }
    }
}
