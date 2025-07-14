package com.example.function;

public class Rosenbrock implements Function{
    @Override
    public double calculate(double x, double y) {
        return Math.pow(1 - x, 2) + 100 * Math.pow(y - x * x, 2);
    }

    @Override
    public String toString() {
        return "Rosenbrock";
    }
}
