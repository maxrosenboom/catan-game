package com.example.aaaa;

//
// FACTORY PATTERN --- Here we create all the dev cards that are used throughout the game.
//

public class DevCard implements Card {
    int id;
    String name;

    DevCard(String type) {
        this.name = "Development Card: " + type + " Card";
        switch (type) {
            case "Knight": 
                this.id = 1;
                break;
            case "Road Building":
                this.id = 2;
                break;
            case "Year of Plenty":
                this.id = 3;
                break;
            case "Monopoly":
                this.id = 4;
                break;
            case "Victory Point":
                this.id = 5;
                break;
            default:
                this.id = -1;
                break;
        }
    }

    public int getTypeId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

}
