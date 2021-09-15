package adventofcode;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

abstract public class BaseSolver<T> {
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
    final public List<Answer> getAnswers() throws IOException {
        final FileReader fileReader = new FileReader(new File(inputFilePath));
        return solve(fileReader);
    }

    /**
     * Solve all parts for this day's problems & print them out.
     *
     * @return list of answers (each part) for this day's problem.
     * @throws IOException if something goes wrong when reading input file.
     */
    final public List<Answer> printAnswers() throws IOException {
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
    abstract protected List<Answer> solve(FileReader input) throws IOException;

    final protected class Answer {
        private String descriptor;
        private T value;

        public Answer(String descriptor, T value) {
            this.descriptor = descriptor;
            this.value = value;
        }
    }
}
