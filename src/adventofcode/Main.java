package adventofcode;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.TreeMap;

public class Main {
    private static final TreeMap<Integer, TreeMap<Integer, Class<?>>> SOLVERS = new TreeMap<>() {{
        put(2015, new TreeMap<>() {{
            put(1, adventofcode.year2015.day1.Solver.class);
            put(2, adventofcode.year2015.day2.Solver.class);
            put(3, adventofcode.year2015.day3.Solver.class);
        }});
    }};

    public static void main(String[] args) {
        SOLVERS.forEach((k, v) -> {
            System.out.printf("[%d]%n", k);
            v.forEach((day, clazz) -> {
                try {
                    final Constructor<?> constructor = clazz.getConstructor();
                    final BaseSolver<?> instance = (BaseSolver<?>) constructor.newInstance();
                    System.out.printf("  [Day %d]%n", day);
                    instance.printAnswers();
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                        IllegalAccessException | IOException e) {
                    System.out.printf("Hit exception solving %s: %s%n", clazz.getCanonicalName(), e);
                }
            });
        });
    }
}
