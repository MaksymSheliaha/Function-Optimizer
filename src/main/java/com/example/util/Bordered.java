package com.example.util;

public abstract class Bordered {
    protected static double X_MIN = -4;
    protected static double X_MAX = 4;
    protected static double Y_MIN = -4;
    protected static double Y_MAX = 4;

    public static void setBorders(double x_min, double x_max, double y_min, double y_max) {
        X_MIN = x_min;
        X_MAX = x_max;
        Y_MIN = y_min;
        Y_MAX = y_max;
    }

    public static double getXMin() {
        return X_MIN;
    }
    public static double getXMax() {
        return X_MAX;
    }
    public static double getYMin() {
        return Y_MIN;
    }
    public static double getYMax() {
        return Y_MAX;
    }
}
