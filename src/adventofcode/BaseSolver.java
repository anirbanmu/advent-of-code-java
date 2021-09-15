package adventofcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public abstract class BaseSolver<T> {
    private String inputFilePath;

    public BaseSolver() {
        this.inputFilePath = this.getClass().getResource("input.txt").getFile();
    }

    public BaseSolver(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    /**
     * Solve all parts for this day's problems.
     *
     * @return list of answers (each part) for this day's problem.
     * @throws IOException if something goes wrong when reading input file.
     */
    public final List<Answer> getAnswers() throws IOException {
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(inputFilePath)))) {
            return solve(bufferedReader);
        }
    }

    /**
     * Solve all parts for this day's problems & print them out.
     *
     * @return list of answers (each part) for this day's problem.
     * @throws IOException if something goes wrong when reading input file.
     */
    public final List<Answer> printAnswers() throws IOException {
        final List<Answer> answers = getAnswers();
        int i = 0;
        for (Answer a : answers) {
            System.out.printf("    [Part %d] %s: %s%n", i + 1, a.descriptor, a.value);
            ++i;
        }
        return answers;
    }

    /**
     * Solve all parts for this day's problems. Must be implemented by child classes.
     *
     * @param input the input file with the input data.
     * @return list of answers (each part) for this day's problem.
     * @throws IOException if something goes wrong when reading input file.
     */
    protected abstract List<Answer> solve(BufferedReader input) throws IOException;

    protected final class Answer {
        public String descriptor;
        public T value;

        public Answer(String descriptor, T value) {
            this.descriptor = descriptor;
            this.value = value;
        }
    }
}
