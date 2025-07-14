package com.example.controller;

import com.example.Display;
import com.example.function.*;
import com.example.method.Gradient;
import com.example.method.Method;
import com.example.method.NAG;
import com.example.util.Bordered;
import com.example.util.Result;
import com.example.util.Vector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {


    @FXML
    public ComboBox<String> methodBox;

    @FXML
    public ComboBox<Function> functionBox;

    @FXML
    public TextArea resultsTextArea;

    @FXML
    public Label parameterLabel;

    @FXML
    public HBox alfaBox;

    @FXML
    public TextField alfaField;

    @FXML
    public HBox muBox;

    @FXML
    public TextField muField;

    @FXML
    public HBox epsBox;

    @FXML
    public TextField epsField;

    @FXML
    public TextField xMin;

    @FXML
    public TextField xMax;

    @FXML
    public TextField yMin;

    @FXML
    public TextField yMax;

    @FXML
    public TextField iterField;

    @FXML
    public HBox startPointBox;

    @FXML
    public TextField yField;

    @FXML
    public TextField xField;

    @FXML
    public HBox iterBox;

    private Function function;

    private Method method;

    private Display display;

    private Result result =  null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setVisible(false, false, false, false, false, false);


        methodBox.getItems().addAll("Gradient", "NAG");
        functionBox.getItems().addAll(new ConvexFunction(), new Himmelblau(), new Rastrigin(), new Rosenbrock());

        methodBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            switch (newVal){
                case "Gradient": {
                    setVisible(true, true, false, true, true, true);
                    break;
                }
                case "NAG": {
                    setVisible(true, true, true, true, true, true);
                    break;
                }
            }
            displayText("Select: " + newVal);
        }
        );

        functionBox.valueProperty().addListener((obs, oldVal, newVal) ->{
            function = newVal;
            displayText("Select: " + newVal);
        });

        display = new Display();
        double xMin = Double.valueOf(this.xMin.getText());
        double xMax = Double.valueOf(this.xMax.getText());
        double yMin = Double.valueOf(this.yMin.getText());
        double yMax = Double.valueOf(this.yMax.getText());
        Bordered.setBorders(xMin, xMax, yMin, yMax);
    }

    private void setVisible(boolean parameterLabel, boolean alfa, boolean mu, boolean eps, boolean iter, boolean start){
        this.parameterLabel.setVisible(parameterLabel);
        this.alfaBox.setVisible(alfa);
        this.muBox.setVisible(mu);
        this.epsBox.setVisible(eps);
        this.iterBox.setVisible(iter);
        this.startPointBox.setVisible(start);
    }

    private void displayText(String text){
        resultsTextArea.setText(text);
    }

    private boolean checkBorders(){
        try{
            double xMin = Double.parseDouble(this.xMin.getText());
            double xMax = Double.parseDouble(this.xMax.getText());
            double yMin = Double.parseDouble(this.yMin.getText());
            double yMax = Double.parseDouble(this.yMax.getText());
            Bordered.setBorders(xMin, xMax, yMin, yMax);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean setMethod(){
        try {
            switch (methodBox.getValue()){
                case "NAG":{
                    double alfa = Double.parseDouble(alfaField.getText());
                    double mu = Double.parseDouble(muField.getText());
                    double eps = Double.parseDouble(epsField.getText());
                    int iter = Integer.parseInt(iterField.getText());
                    method = new NAG(alfa, mu, eps, iter);
                    break;
                }
                case "Gradient":{
                    double alfa = Double.parseDouble(alfaField.getText());
                    double eps = Double.parseDouble(epsField.getText());
                    int iter = Integer.parseInt(iterField.getText());
                    method = new Gradient(alfa, iter, eps);
                    break;
                }
                default: return false;
            }
            method.setFunction(function);
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    private Vector getStartPoint() {
        try{
            double x = Double.parseDouble(xField.getText());
            double y = Double.parseDouble(yField.getText());
            return new Vector(x,y);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    @FXML
    public void showContours(ActionEvent event) {
        if(function == null){
            displayText("Function is not selected");
            return;
        }

        if(!checkBorders()){
            displayText("Invalid value of borders");
            return;
        }
        display.drawContourPlot(function);
    }

    @FXML
    public void runMethod(ActionEvent event) {
        Vector start = getStartPoint();

        if(function == null){
            displayText("Function is not selected");
            return;
        }

        if(start == null){
            displayText("Invalid value of start point");
            return;
        }

        if(!setMethod()){
            displayText("Invalid method parameters");
            return;
        }

        result = method.solve(start.x, start.y);

        displayText(result.getText());

    }


    @FXML
    public void showWay(ActionEvent event) {
        if(result == null){
            displayText("Result is not found yet");
            return;
        }

        showContours(event);

        display.drawWay(result.getWay(), Color.BLUE);
    }

    @FXML
    public void showGraph(ActionEvent event) {
        if(result == null){
            displayText("Result is not found yet");
            return;
        }

        display.addGraph(result.getWay(), function, method.toString());
        display.showGraph();
    }
}
