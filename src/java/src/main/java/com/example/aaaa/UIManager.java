package com.example.aaaa;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.stage.Stage;

public class UIManager {
    static Resource[] res = {new Brick(), new Ore(), new Wool(), new Grain(), new Lumber()};
    public Group buttonUI(String label, int x, int y, int xbump, Color c){
        Group button = new Group();
        Rectangle baseButton = new Rectangle(150, 50, c);
        baseButton.setX(x);
        baseButton.setY(y);
        Text buttonLabel = textUI(label, x+xbump, y+30, Color.AZURE);
        button.getChildren().add(baseButton);
        button.getChildren().add(buttonLabel);
        return button;
    }

    public Group buttonUI(String label, int x, int y, int xbump){
        Group button = new Group();
        Rectangle baseButton = new Rectangle(150, 50, Color.TAN);
        baseButton.setX(x);
        baseButton.setY(y);
        Text buttonLabel = textUI(label, x+xbump, y+30, Color.AZURE);
        button.getChildren().add(baseButton);
        button.getChildren().add(buttonLabel);
        return button;
    }

    public Text textUI(String str, int x, int y, Color c){
        Text t = new Text(str);
        t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        t.setX(x);
        t.setY(y);
        t.setFill(c);
        return t;
    }

    public Text close(Stage stage){
        Text t = textUI("X", 75, 75, Color.DARKSLATEGRAY);
        return t;
    }

    public Rectangle[] resourceRectangles(int size){
        Rectangle brick = new Rectangle(size, size, res[0].c);
        Rectangle ore = new Rectangle(size, size, res[1].c);
        Rectangle wool = new Rectangle(size, size, res[2].c);
        Rectangle grain = new Rectangle(size, size, res[3].c);
        Rectangle lumber = new Rectangle(size,size, res[4].c);
        Rectangle[] rectangles = {brick, ore, wool, grain, lumber};
        for(int i = 0; i<rectangles.length; i++){
            rectangles[i].setArcHeight(10);
            rectangles[i].setArcWidth(10);
        }
        return  rectangles;
    }
}
