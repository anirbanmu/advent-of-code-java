package adventofcode;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.Instant;
import java.util.TreeMap;

public final class Main {
    private static final TreeMap<Integer, TreeMap<Integer, Class<?>>> SOLVERS = new TreeMap<>() {{
        put(2015, new TreeMap<>() {{
            put(1, adventofcode.year2015.day1.Solver.class);
            put(2, adventofcode.year2015.day2.Solver.class);
            put(3, adventofcode.year2015.day3.Solver.class);
            put(4, adventofcode.year2015.day4.Solver.class);
        }});
    }};

    public static void main(String[] args) {
        SOLVERS.forEach((k, v) -> {
            System.out.printf("[%d]%n", k);
            v.forEach((day, clazz) -> {
                try {
                    final var constructor = clazz.getConstructor();
                    final var instance = (BaseSolver<?>) constructor.newInstance();

                    final var measured = measure(instance::getAnswers);

                    System.out.printf("  [Day %d] %dms%n", day, measured.elapsed.toMillis());
                    int i = 0;
                    for (final var a : measured.result) {
                        System.out.printf("    [Part %d] %s: %s%n", i + 1, a.descriptor, a.value);
                        ++i;
                    }
                } catch (IOException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                        InvocationTargetException e) {
                    System.out.printf("Hit exception solving %s: %s%n", clazz.getCanonicalName(), e);
                }
            });
        });
    }

    private static <T> ResultWithDuration<T> measure(GetAnswersCallable<T> lambda) throws IOException {
        final var start = Instant.now();
        final var answers = lambda.call();
        final var elapsed = Duration.between(start, Instant.now());

        return new ResultWithDuration<>(answers, elapsed);
    }

    private interface GetAnswersCallable<T> {
        T call() throws IOException;
    }

    private static final class ResultWithDuration<T> {
        public T result;
        public Duration elapsed;

        ResultWithDuration(T result, Duration elapsed) {
            this.result = result;
            this.elapsed = elapsed;
        }
    }
}
