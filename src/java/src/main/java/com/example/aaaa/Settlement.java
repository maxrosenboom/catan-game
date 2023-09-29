package com.example.aaaa;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
public class Settlement extends Piece implements Drawable {
    Vertex v;
    Player p;
    Color c;
    Polygon poly;
    Settlement(Vertex vt, Player pl, Color col){
        v = vt;
        p = pl;
        c = col;
        poly = new Polygon();
        double x = v.x;
        double y = v.y;
        poly.getPoints().setAll(
                x+10, y-10,
                x, y-15,
                x-10, y-10,
                x-10, y+10,
                x+10, y+10
        );

    }

    public Polygon getPolygon(){return poly;}

    public ArrayList<Shape> draw(){
        //Polygon trial = new Polygon();
        //trial.getPoints().setAll(poly.getPoints());
        //trial.setFill(c);
        ArrayList<Shape> arrp = new ArrayList<>();
        poly.setFill(p.color);
        arrp.add(poly);
        return arrp;
    }
    public ArrayList<Vertex> getAdj(){
        return v.adjVerts;
    }
    public Color getColor(){return c;}
    public Vertex getV(){return v;}

    public Player getPlayer(){return p;}





}
