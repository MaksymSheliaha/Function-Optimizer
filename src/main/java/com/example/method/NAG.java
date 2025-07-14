package com.example.method;

import java.util.ArrayList;
import java.util.List;

import com.example.util.Result;
import com.example.util.Vector;

public class NAG extends Method {
    private double alfa;
    private double mu;
    private double eps;
    private int maxIterations;

    public NAG(double alfa, double mu, double eps, int maxIterations) {
        this.alfa = alfa;
        this.mu = mu;
        this.eps = eps;
        this.maxIterations = maxIterations;
    }

    @Override
    public Result solve(double x, double y) {
        calculations = 0;
        long start = System.nanoTime();
        List<Vector> way = new ArrayList<>();
        way.add(new Vector(x, y));
        Vector v = new Vector(0, 0);
        Vector grad = null;
        int i = 0;
        while(i < maxIterations){
            grad = gradient(x + mu*v.x, y + mu*v.y);

            v.x = mu*v.x - alfa * grad.x;
            v.y = mu*v.y - alfa * grad.y;

            x+=v.x;
            y+=v.y;
            i++;
            way.add(new Vector(x, y));

            if(grad.norm() < eps){
                break;
            }

        }

        long end = System.nanoTime();

        String text = String.format("\nNAG method completed in %.3f ms", (double) (end - start) / 1_000_000) +
                "\nIterations: " + i +
                "\nCalculations: " + calculations +
                String.format("\nFinal point: (%.3f; %.3f )", x, y) +
                String.format("\nFunction value: %.3f", func(x, y)) +
                String.format("\nFinal gradient: (%.3f; %.3f )", grad.x, grad.y);


        Result result = new Result(way, text);
        return result;
    }

    @Override
    public String toString() {
        return "NAG";
    }
}
