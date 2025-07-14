package com.example.util;

public class Vector {
    public double x;
    public double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void normalize(){
        if(this.x == 0 && this.y == 0) return;        
        double norm = Math.sqrt(this.x*this.x+this.y*this.y);
        this.x /= norm;
        this.y /= norm;
    }

    public double norm(){
        return Math.sqrt(this.x*this.x+this.y*this.y);
    }
}
