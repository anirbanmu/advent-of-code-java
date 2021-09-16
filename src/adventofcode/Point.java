package adventofcode;

import java.util.Objects;

public final class Point {
    public long x, y;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (this.getClass() != o.getClass()) {
            return false;
        }

        final var other = (Point) o;
        return x == other.x && y == other.y;
    }
}
