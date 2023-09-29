package com.example.aaaa;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.List;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Tile implements Drawable {
    private int num;
    private Resource resource;
    Pentagon p;

    ArrayList<Vertex> vs = new ArrayList<>();

    ArrayList<Player> adjPlayers = new ArrayList<>(); //might remove, trying to figure out resource dist



    Tile(int n, Resource r, double x, double y){
        this.num = n;
        this.resource = r;
        this.p = new Pentagon(70, x, y, this.resource);
        this.setVertex();
    }
    public ArrayList<Shape> draw(){
        //for GUI
        ArrayList<Shape> arrp = new ArrayList<>();
        //Polygon p = new Pentagon(70, 0.0, 0.0, resource);
        Circle c = new Circle(30, Color.BEIGE);
        c.setStroke(Color.BURLYWOOD);
        c.setStrokeWidth(2.0);
        arrp.add(p);
        arrp.add(c);
        return arrp;

    }
    public int getNum(){ return num; }
    public Resource getResource(){ return resource; }

    private void setVertex(){
        List<Double> pt = p.getPoints().stream().toList();
        List<Double> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();
        for(int i = 0; i<pt.size(); i+=2){
            xs.add(pt.get(i));
            ys.add(pt.get(i+1));
        }
        for(int i = 0; i<xs.size(); i++){
            Vertex vNew = new Vertex(xs.get(i), ys.get(i));
            vNew.addAdjTile(this);
            vs.add(vNew);

        }


        for(int i = 1; i<this.vs.size()-1; i++){
            this.vs.get(i).addAdjVert(this.vs.get(i-1));
            this.vs.get(i).addAdjVert(this.vs.get(i+1));
        }
        this.vs.get(0).addAdjVert(this.vs.get(1));
        this.vs.get(0).addAdjVert(this.vs.get(5));
        this.vs.get(5).addAdjVert(this.vs.get(0));
        this.vs.get(5).addAdjVert(this.vs.get(4));
    }

    public void mergeTiles(Tile t){
        for(int i = 0; i<this.vs.size(); i++){
            for(int j = 0; j<t.vs.size();j++){
                if(this.vs.get(i)!=t.vs.get(j)&&this.vs.get(i).equals(t.vs.get(j))){
                    this.vs.get(i).addAdjVert(t.vs.get(j).adjVerts);
                    t.vs.set(j, this.vs.get(i)); //changes it to the same vertex
                    this.vs.get(i).removeDuplicates();
                }
            }
        }
    }

    public void rollHelper(int roll){
        if(roll==this.num){
            for(int i = 0;i<adjPlayers.size();i++){
                Resource r = this.resource;
                adjPlayers.get(i).addCard(new ResourceCard(this.resource.name)); //if a player is adjacent twice, just add them to the adj twice
            }
        }
    }

    public void addPlayers(Player p){
        adjPlayers.add(p);
    }



}
