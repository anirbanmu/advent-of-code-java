package adventofcode.year2015.day2;

import adventofcode.BaseSolver;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Solver extends BaseSolver<Long> {

    @Override
    protected List<Answer> solve(FileReader input) throws IOException {
        long surfaceArea = 0;
        long ribbonLength = 0;

        final Scanner sc = new Scanner(input).useDelimiter("\\s|x");
        while (sc.hasNextLong()) {
            final long l = sc.nextLong();
            final long w = sc.nextLong();
            final long h = sc.nextLong();

            final long lw = l * w;
            final long wh = w * h;
            final long hl = h * l;

            // total surface area + min area of a surface
            surfaceArea += 2 * lw + 2 * wh + 2 * hl + Math.min(Math.min(lw, wh), hl);

            // volume + minumum perimeter
            ribbonLength += lw * h + Math.min(Math.min(2 * l + 2 * w, 2 * w + 2 * h), 2 * h + 2 * l);
        }

        return new ArrayList<>(Arrays.asList(
                new Answer("Total area needed", surfaceArea),
                new Answer("Total ribbon needed", ribbonLength)
        ));
    }
}
