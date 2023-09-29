package com.example.aaaa;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.lang.Math;
public class Vertex implements Drawable{
    public ArrayList<Vertex> adjVerts = new ArrayList<>(); //where could a road go from here?
    public ArrayList<Tile> adjTiles = new ArrayList<>(); //what would a tile at this vertex get from here?
    public double x;
    public double y;
    boolean roadOccupied;
    Piece settled;
    RoadNode roadNode;
    Player player;


   // City city;

    Circle c = new Circle(5, Color.BLACK);

    Piece p;

    Vertex(double x, double y){
        this.x = x;
        this.y = y;
        roadOccupied = false;
        settled = null;
        roadNode = null;
        player = null;
    }

    public void addAdjVert(Vertex v){
        this.adjVerts.add(v);
    }

    public void addAdjVert(ArrayList<Vertex> vs){
        this.adjVerts.addAll(vs);
    }
    public boolean equals(Vertex v){
        double d1 = Math.abs(this.x - v.x);
        double d2 = Math.abs(this.y - v.y);
        if(d1<5 && d2<5) {
           // System.out.println("Equals with condition of: "+ d1+ d2);
            for(int i = 0; i<v.adjTiles.size();i++){
                Tile t = v.adjTiles.get(i);
                if(!this.adjTiles.contains(t)){
                    adjTiles.add(t);
                }
            }
            return true;
        }
        return false;
    }
    public void addAdjTile(Tile t){
        this.adjTiles.add(t);
    }
    public boolean isroadOccupied(){
        return roadOccupied;
    }
    public ArrayList<Shape> draw(){
        if(settled!=null){
            return settled.draw();
        }
        c.setOpacity(0.3333);
        c.setCenterX(x);
        c.setCenterY(y);
        ArrayList<Shape> arr = new ArrayList<>();
        arr.add(c);
        return arr;

    }

    public ArrayList<Vertex> removeDuplicates(){
        ArrayList<Vertex>ls1 = new ArrayList<>();
        for(int i=0;i<adjVerts.size();i++)
        { //geeks for greeks unique array list function: https://www.geeksforgeeks.org/how-to-get-unique-values-from-arraylist-using-java-8/
            Vertex x;
            if(!ls1.contains(adjVerts.get(i)))
            {
                x=adjVerts.get(i);
                ls1.add(x);
            }
        }
        adjVerts = ls1;
        return ls1;
    }

    public ArrayList<Vertex> jankyAdjacent(ArrayList<Vertex> v, double size){
        ArrayList<Vertex> n = new ArrayList<>();
        for(int i = 0; i<v.size(); i++){
            if(!this.equals(v.get(i))&&this.distance(v.get(i))<1.5*size){
                n.add(v.get(i));

            }
        }
        adjVerts = n;
        return n;
    }

    public void makeCity(Player person, int state){
        if(state==2){
            City tester = new City(this, new Player("", Color.BLACK));
        if(settled!=null&&settled.getClass()!=tester.getClass()&&settled.getPlayer()==person) {
            City city = new City(this, person);
            settled = city;
            for(int i = 0; i<adjTiles.size(); i++){
                adjTiles.get(i).addPlayers(person);
            }
            }
        }
    }
    private double distance(Vertex v){
        double xdist = (this.x-v.x);
        double ydist = (this.y-v.y);
        double ret = Math.sqrt(xdist*xdist + ydist*ydist);
        return ret;
    }

    public boolean addSettled(Settlement piece, int state){
        if(state==1) { //making sure from the board that we are allowed
            if (settled != null) {
                System.out.println("Cannot place here. Already settled.");
                System.out.println(settled.getClass() + "" + settled.getPlayer().name);
                return false;
            }
            for (int i = 0; i < adjVerts.size(); i++) {
                if (adjVerts.get(i).settled != null) {
                    System.out.println("Cannot place here. Too close.");
                    return false;
                }
            }
            if(piece.p==player) {
                settled = piece;
                Player player = piece.p;

                for (int i = 0; i < adjTiles.size(); i++) {
                    adjTiles.get(i).addPlayers(player);
                }
                return true;
            }
        }
        return false;
    }
    public void addRoadNode(RoadNode rn, Player p){
        roadOccupied = true;
        roadNode = rn;
        player = p;
        //player = rn.p;


    }





}
