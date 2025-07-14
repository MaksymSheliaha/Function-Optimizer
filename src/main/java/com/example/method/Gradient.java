package com.example.method;

import java.util.ArrayList;
import java.util.List;

import com.example.util.Result;
import com.example.util.Vector;

public class Gradient extends Method{

    private double alfa;
    private double eps;
    private int maxIterations;

    public Gradient(double alfa, int maxIterations, double eps) {
        this.alfa = alfa;
        this.maxIterations = maxIterations;
        this.eps = eps;
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
            grad = gradient(x, y);

            v.x = -alfa * grad.x;
            v.y = -alfa * grad.y;

            x+=v.x;
            y+=v.y;
            /*
            if(x < X_MIN){
                x = X_MIN;
            }
            else if(x >X_MAX){
                x = X_MAX;
            }
            if(y < Y_MIN){
                y = Y_MIN;
            }
            else if(y > Y_MAX){
                y = Y_MAX;
            }
            */
            i++;
            way.add(new Vector(x, y));
            if(grad.norm() < eps){
                break;
            }

        }

        long end = System.nanoTime();

        String text = String.format("\nGradient method completed in %.3f ms", (double) (end - start) / 1_000_000) +
                "\nIterations: " + i +
                "\nCalculations: " + calculations +
                String.format("\nFinal point: (%.3f; %.3f )", x, y) +
                String.format("\nFunction value: %.3f", func(x, y)) +
                String.format("\nFinal gradient: (%.3f; %.3f )", grad.x, grad.y);

        return new Result(way, text);
    }

    @Override
    public String toString() {
        return "Gradient";
    }
}
