package com.example.function;

public class Rastrigin implements Function{

    @Override
    public double calculate(double x, double y) {
        return 20 + x * x + y * y - 10 * (Math.cos(2 * Math.PI * x) + Math.cos(2 * Math.PI * y));
    }

    @Override
    public String toString() {
        return "Rastrigin";
    }
}
