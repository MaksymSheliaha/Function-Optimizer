package com.example.method;

import com.example.function.Function;
import com.example.util.Result;
import com.example.util.Vector;

public abstract class Method {

    protected int calculations = 0;
    private Function function;

    public void setFunction(Function function){
        this.function = function;
    }

    public double func(double x, double y){
        calculations++;
        return function.calculate(x, y);
    }

    protected Vector gradient(double x, double y){
        double h = 1e-5;
        double dfdx = (func(x + h, y) - func(x - h, y)) / (2 * h);
        double dfdy = (func(x, y + h) - func(x, y - h)) / (2 * h);
        return new Vector(dfdx, dfdy);
    }


    public abstract Result solve(double x, double y);

}
