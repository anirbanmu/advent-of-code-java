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

    final public List<Answer> getAnswers() throws IOException {
        final FileReader fileReader = new FileReader(new File(inputFilePath));
        return solve(fileReader);
    }

    final public List<Answer> printAnswers() throws IOException {
        final List<Answer> answers = getAnswers();
        int i = 0;
        for (Answer a : answers) {
            System.out.printf("    [Part %d] %s: %s%n", i + 1, a.getDescriptor(), a.getValue());
            ++i;
        }
        return answers;
    }

    abstract protected List<Answer> solve(FileReader input) throws IOException;

    protected class Answer {
        private String descriptor;
        private T value;

        public Answer(String descriptor, T value) {
            this.descriptor = descriptor;
            this.value = value;
        }

        public String getDescriptor() {
            return descriptor;
        }

        public T getValue() {
            return value;
        }
    }
}
