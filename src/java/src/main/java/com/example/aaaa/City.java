package com.example.aaaa;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class City extends Piece{
    Vertex v;
    Player p;
    Color c;

    Polygon poly;
    City(Vertex vt, Player pl){
        v = vt;
        p = pl;
        c = pl.color; //might be broken
        poly = new Polygon();
        double x = v.x;
        double y = v.y;
        poly.getPoints().setAll(
                x+20, y-25,
                x+10, y-30,
                x,y-25,
                x, y-10,
                x-10, y-15,
                x-20, y-10,
                x-20, y+10,
                x+20, y+10
        );
    }

    public ArrayList<Shape> draw(){

        poly.setFill(p.color);
        ArrayList<Shape> arrp = new ArrayList<>();
        arrp.add(poly);
        return arrp;
    }
    public Polygon getPolygon(){return poly;}
    public Color getColor(){return c;}
    public Vertex getV(){return v;}

    public Player getPlayer(){return p;}
}
