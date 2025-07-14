package com.example;

import java.util.List;

import com.example.function.Function;
import com.example.util.Bordered;
import com.example.util.Vector;

import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.stage.Stage;

public class Display extends Bordered{

    private final int WIDTH = 800;
    private final int HEIGHT = 800;
    private final int GRID_SIZE = 700;
    private final int CONTOUR_LINES = 20;

    private GraphicsContext gc;
    private LineChart<Number, Number> lineChart;
    private Stage stage;

    public Display() {
        stage = new Stage();
        stage.setTitle("Contour Plot");
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(new StackPane(canvas));
        stage.setScene(scene);
        lineChart = initializeGrapf();
    }

    public void drawContourPlot(Function function) {
        gc.clearRect(0,0, WIDTH, HEIGHT);
        if(function == null) {
            return;
        }
        double dx = (X_MAX - X_MIN) / GRID_SIZE;
        double dy = (Y_MAX - Y_MIN) / GRID_SIZE;
        double[][] values = new double[GRID_SIZE + 1][GRID_SIZE + 1];

        for (int i = 0; i <= GRID_SIZE; i++) {
            for (int j = 0; j <= GRID_SIZE; j++) {
                double x = X_MIN + i * dx;
                double y = Y_MIN + j * dy;
                values[i][GRID_SIZE-j] = function.calculate(x,y);
            }
        }

        double min = findMin(values);
        double max = findMax(values);
        double delta = max - min;

        for (int k = 0; k < CONTOUR_LINES; k++) {
            double level = min + Math.pow(delta,(double)k/(CONTOUR_LINES-1));
            gc.setStroke(Color.hsb(240 - 240 * k / (CONTOUR_LINES - 1), 1.0, 1.0));
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    if ((values[i][j] - level) * (values[i + 1][j] - level) < 0 ||
                            (values[i][j] - level) * (values[i][j + 1] - level) < 0) {
                        double x = (i / (double) GRID_SIZE) * WIDTH;
                        double y = (j / (double) GRID_SIZE) * HEIGHT;
                        gc.strokeOval(x, y, 1, 1);
                    }
                }
            }
        }
        drawCoordinateSystem();
        stage.show();
    }

    private double findMax(double [][] values){
        double max = values[0][0];
        for (double[] row : values) {
            for (double val : row) {
                if(val>max){
                    max = val;
                }
            }
        }
        return max;
    }
    
    private double findMin(double [][] values){
        double min = values[0][0];
        for (double[] row : values) {
            for (double val : row) {
                if(val<min){
                    min = val;
                }
            }
        }
        return min;
    }

    private void drawCoordinateSystem() {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        double xCenter = WIDTH / 2.0;
        double yCenter = HEIGHT / 2.0;

        gc.strokeLine(0, yCenter, WIDTH, yCenter); 
        gc.strokeLine(xCenter, 0, xCenter, HEIGHT); 

        gc.setFill(Color.BLACK);
        double xMid = (X_MAX + X_MIN)/2;
        double yMid = (Y_MAX + Y_MIN)/2;
        gc.fillText(String.format("%.2f", xMid), xCenter + 5, yCenter + 10);
        gc.fillText(String.format("%.2f", yMid), xCenter - 20, yCenter - 5);
        gc.fillText(Double.toString(X_MAX), WIDTH - 10, yCenter - 5);
        gc.fillText(Double.toString(X_MIN), 10, yCenter - 5);
        gc.fillText(Double.toString(Y_MAX), xCenter + 5, 10);
        gc.fillText(Double.toString(Y_MIN), xCenter + 5, HEIGHT-10);
    }

    public void drawWay(List<Vector> way, Color color) {

        gc.setLineWidth(2);
        double xCenter = WIDTH / 2.0;
        double yCenter = HEIGHT / 2.0;
        for (int i = 0; i < way.size() - 1; i++) {
            int index = i;
            PauseTransition pause = new PauseTransition(Duration.seconds(10.0/way.size()*i)); 
            pause.setOnFinished(event->{
            gc.setStroke(color);
            double x1 = way.get(index).x;
            double y1 = way.get(index).y;
            double x2 = way.get(index + 1).x;
            double y2 = way.get(index + 1).y;
            gc.strokeLine(xCenter + x1 * WIDTH /(X_MAX-X_MIN), yCenter - y1 * HEIGHT / (Y_MAX-Y_MIN),
                    xCenter + x2 * WIDTH /(X_MAX-X_MIN), yCenter - y2 * HEIGHT /(Y_MAX-Y_MIN));
            });
            pause.play();
        }

        double x1 = way.get(way.size()-1).x;
        double y1 = way.get(way.size()-1).y;
        drawCross(xCenter + x1 * WIDTH /(X_MAX-X_MIN), yCenter - y1 * HEIGHT / (Y_MAX-Y_MIN), Color.BLACK);
    }

    private void drawCross(double x, double y, Color color) {
        gc.setStroke(color);
        gc.setLineWidth(2);

        gc.strokeLine(x - 3, y - 3, x + 3, y + 3);
        gc.strokeLine(x - 3, y + 3, x + 3, y - 3);
    }

    LineChart<Number, Number> initializeGrapf(){

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Iter");

        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Value");

        
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Progress of the algorithm");
        lineChart.setCreateSymbols(false);


        return lineChart;
    }

    public void addGraph(List<Vector> path, Function function, String name){
        NumberAxis yAxis = (NumberAxis)lineChart.getYAxis();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(name);
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        double val;
        int i = 0;
        for (var point: path) {
            val = function.calculate(point.x, point.y);
            series.getData().add(new XYChart.Data<>(i, val));
            i++;
            if(max < val) max = val;
            if(min > val) min = val;
        }

        double padding = (max - min) * 0.1;
        if (padding == 0) padding = 1; 
        min = min - padding;
        max = max + padding;
        if(!yAxis.isAutoRanging()){
            min = Math.min(min, yAxis.getLowerBound());
            max = Math.max(max, yAxis.getUpperBound());
        }
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(min);
        yAxis.setUpperBound(max);
        yAxis.setTickUnit((yAxis.getUpperBound() - yAxis.getLowerBound()) / 20);
        lineChart.getData().add(series);
    }

    public void showGraph() {
        Stage gStage = new Stage();
        Scene gScene = new Scene(lineChart, 800, 600);
        gStage.setScene(gScene);
        gStage.show();
    }

}
