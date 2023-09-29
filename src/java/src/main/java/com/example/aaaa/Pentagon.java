package com.example.aaaa;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import java.lang.Math;

public class Pentagon extends Polygon{

    Pentagon(int size, double x, double y, Resource r){

        this.getPoints().setAll(
                1.5*size*Math.cos(0.524)-(0.13*size*Math.sin(0.524)) + x, 0.13*size*Math.cos(0.524) + ( 1.5*size*Math.sin(0.524)) + y,
                0.5*size*Math.cos(0.524)-(0.13*size*Math.sin(0.524)) + x, 0.13*size*Math.cos(0.524) + ( 0.5*size*Math.sin(0.524)) + y,
                0.0*size*Math.cos(0.524)-(1*size*Math.sin(0.524)) + x, 1*size*Math.cos(0.524) + ( 0*size*Math.sin(0.524)) + y,
                0.5*size*Math.cos(0.524)-(1.87*size*Math.sin(0.524)) + x, 1.87*size*Math.cos(0.524) + ( 0.5*size*Math.sin(0.524)) + y,
                1.5*size*Math.cos(0.524)-(1.87*size*Math.sin(0.524)) + x, 1.87*size*Math.cos(0.524) + ( 1.5*size*Math.sin(0.524)) + y, //guessing this is bottom right?
                2.0*size*Math.cos(0.524)-(1.0*size*Math.sin(0.524)) + x, 1.0*size*Math.cos(0.524) + ( 2.0*size*Math.sin(0.524)) + y

        );
        this.setFill(r.getColor());
        this.setStroke(Color.BLACK);
    }
    void move(double x, double y){
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
}
