package com.example.aaaa;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

//
// FACTORY PATTERN --- Here we create all the resource cards to be used in the game
//

public class ResourceCard implements Card {
    int id;
    String name;
    Resource r;

    ResourceCard(String type) {
        this.name = "Resource Card: " + type;
        switch (type) {
            case "Grain":
                this.id = 6;
                r = new Grain();
                break;
            case "Wool":
                this.id = 7;
                r = new Wool();
                break;
            case "Lumber":
                this.id = 8;
                r = new Lumber();
                break;
            case "Brick":
                this.id = 9;
                r = new Brick();
                break;
            case "Ore":
                this.id = 10;
                r = new Ore();
                break;
            default:
                System.out.println("Hit null");
                this.id = -1;
                r = null;
                break;
        }
    }

    public int getTypeId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Resource getResource(){ return this.r;}

    public Rectangle draw(){
        Rectangle base = new Rectangle(30, 50);
        base.setFill(r.getColor());
        return base;
    }




}