package com.example.aaaa;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

import java.util.Random;
public class Dice  {
    Random r;
    //Board b;
    Dice(){
        r = new Random();
        //this.b = b;
    }
    public int[] roll(){
        int i = r.nextInt(6) + 1;
        int j = r.nextInt(6) + 1;
        int[] arr = {i,j}; //separate instead of sum to draw them
        return arr;
    }

    public ArrayList<Shape> draw(int i, int j) {
        Rectangle d1 = new Rectangle(80, 80);
        Rectangle d2 = new Rectangle(80, 80);
        d1.setX(100);
        d1.setY(100);
        d2.setX(200);
        d2.setY(100);
        d1.setArcHeight(20);
        d1.setArcWidth(20);
        d2.setArcHeight(20);
        d2.setArcWidth(20);
        d1.setFill(Color.BEIGE);
        d2.setFill(Color.BEIGE);
        Text one = new Text("" + i);
        Text two = new Text("" + j);
        one.setX(125);
        one.setY(155);
        two.setX(225);
        two.setY(155);
        one.setFill(Color.SADDLEBROWN);
        two.setFill(Color.SADDLEBROWN);
        d1.setStroke(Color.PERU);
        d2.setStroke(Color.PERU);
        one.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        two.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
        ArrayList<Shape> s = new ArrayList<>();
        s.add(d1);
        s.add(d2);
        s.add(one);
        s.add(two);
        return s;
    }
}
