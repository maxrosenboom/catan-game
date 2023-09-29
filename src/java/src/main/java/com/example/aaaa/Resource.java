package com.example.aaaa;

import javafx.scene.paint.Color;

// ABSTRACT FACTORY -- Here we have an overarching Resource class to build all of the types of resources

public abstract class Resource {
    String name;

    Color c;
    //will also need to tie to resource card creation somehow
    String getName(){
        return name;
    }
    Color getColor(){ return c; }
    abstract Card drawFromResource();
    Card createResource() { return null; }
}

class Brick extends Resource{
    Brick(){
        name = "Brick";
        c = Color.CHOCOLATE;
    }
    Card drawFromResource(){ return null; } //for generating a card when this resource is called later
}

class Grain extends Resource {
    Grain(){
        name = "Grain";
        c = Color.KHAKI;
    }
    Card drawFromResource(){ return null; } //for generating a card when this resource is called later
}

class Wool extends Resource {
    Wool(){
        name = "Wool";
        c = Color.SPRINGGREEN;
    }
    Card drawFromResource(){ return null; } //for generating a card when this resource is called later
}

class Ore extends Resource{
    Ore(){
        name = "Ore";
        c = Color.SLATEBLUE;
    }
    Card drawFromResource(){ return null; } //for generating a card when this resource is called later
}

class Lumber extends Resource{
    Lumber(){
        name = "Lumber";
        c = Color.FORESTGREEN; //hehe
    }
    Card drawFromResource(){ return null; } //for generating a card when this resource is called later
}
