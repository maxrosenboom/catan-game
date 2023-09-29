package com.example.aaaa;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PlayerHandler {
    ArrayList<Player> players;
    Player currentPlayer;

    PlayerHandler(){
        players = new ArrayList<>();
        currentPlayer = new Player("Bro", Color.BLACK);
    }
    public void addPlayer(Player p){
        players.add(p);
    }

    public void setCurrent(Player p){
        currentPlayer = p;
    }

    public Player getCurrentPlayer(){return currentPlayer;}

}
