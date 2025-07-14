package com.example.function;

public class Himmelblau implements Function{

    @Override
    public double calculate(double x, double y) {
        return Math.pow(x * x + y - 11, 2) + Math.pow(x + y * y - 7, 2);
    }

    @Override
    public String toString() {
        return "Himmelblau";
    }
}
