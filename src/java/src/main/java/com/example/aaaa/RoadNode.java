package com.example.aaaa;

import javafx.scene.Group;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class RoadNode {
    //
    Vertex vCurr;
   // RoadNode vPrev;
   // boolean free;
    Vertex vPrev;
    Line l;
    Player p;

    RoadNode(Vertex vCurr, Player p){
        this.vCurr = vCurr;
        this.p = p;
        ArrayList<Vertex> vadj = vCurr.adjVerts;
        for(int i = 0; i< vadj.size(); i++){
            //System.out.println(vadj.get(i).x+" "+vadj.get(i).y);
            if(vadj.get(i).player!=null){
                //System.out.println(vadj.get(i).player.name);
            }
            if(vadj.get(i).player==p){
                //System.out.println("So it DOES HIT");
                vPrev = vadj.get(i);
                break;
            }
        }







    }
    public boolean valid(){
        if(vPrev==null){ //there's no adjacent roads in the proper color
            return false;
        }
        if(vCurr.player!=null){ //there's another player on the tile already
            return false;
        }
        vCurr.addRoadNode(this, p);
        boolean placed;
        if(p.road1.contains(vPrev.roadNode)&&p.road2.contains(this)){//the case that you join two roads
            p.road1.addAll(p.road2);
            p.road2 = null;
            return true;
        }
        if(p.road2.contains(vPrev.roadNode)&&p.road1.contains(this)){//same as above
            p.road1.addAll(p.road2);
            p.road2 = null;
            return true;
        }
        if(p.road1.contains(vPrev.roadNode)){
            p.road1.add(this);
            return true;
        }
        if(p.road2.contains(vPrev.roadNode)){
            p.road2.add(this);
            return true;
        }
        System.out.println("Invalid Placement");
        return false;
        //TODO TOMORROW KATE: INIT STARTING BOARD, MANUALLY ADD FIRST ROAD, FROM THERE BUILDING SHOULD BE THE SAME FINGERS CROSSED.
    }
    public Line draw(){
        if(vPrev!=null){
        Line l = new Line(vPrev.x, vPrev.y, vCurr.x, vCurr.y);
        l.setStroke(vCurr.player.color);
        l.setStrokeWidth(10);

        return l; }
        else{
            Line l = new Line(vCurr.x, vCurr.y, vCurr.x+0.00001, vCurr.y+0.00001);
            return l;
        }
    }


}
