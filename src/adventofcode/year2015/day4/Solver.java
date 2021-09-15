package adventofcode.year2015.day4;

import adventofcode.BaseSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Solver extends BaseSolver<Long> {
    public Solver() {
        super();
    }

    public Solver(String inputFilePath) {
        super(inputFilePath);
    }

    @Override
    protected List<Answer> solve(BufferedReader input) throws IOException {
        final var str = input.readLine();

        final var answers = new Object() {
            public long five_zeroes, six_zeroes;

            {
                five_zeroes = -1;
                six_zeroes = -1;
            }
        };

        try {
            final var md5Digest = MessageDigest.getInstance("MD5");
            for (var i = 0L; answers.five_zeroes == -1 || answers.six_zeroes == -1; ++i) {
                final var md5Sum = md5Digest.digest((str + i).getBytes());

                if (md5Sum[0] == 0 && md5Sum[1] == 0) {
                    // Five zeroes
                    if (answers.five_zeroes == -1 && (md5Sum[2] & 0xF0) == 0) {
                        answers.five_zeroes = i;
                    }

                    // Six zeroes
                    if (answers.six_zeroes == -1 && md5Sum[2] == 0) {
                        answers.six_zeroes = i;
                    }
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 doesn't exist? Should never happen.");
        }

        return new ArrayList<>(Arrays.asList(
                new Answer("Lowest number to produce 5 zeroes MD5", answers.five_zeroes),
                new Answer("Lowest number to produce 6 zeroes MD5", answers.six_zeroes)
        ));
    }
}
