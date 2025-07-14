package com.example.function;

public class ConvexFunction implements Function{
    @Override
    public double calculate(double x, double y) {
        return 3 * x * x + 4 * y * y + 2 * x * y - 8 * x + 6 * y + 10;
    }

    @Override
    public String toString() {
        return "Convex Function";
    }
}
