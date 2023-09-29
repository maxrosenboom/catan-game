package com.example.aaaa;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    
    ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<Card>();

        for (int i = 0; i < 14; i++) { //14 knight cards
            deck.add(new DevCard("Knight"));
        }
        for (int i = 0; i < 2; i++) { //2 of road building, year of plenty, and monopoly cards
            deck.add(new DevCard("Road Building"));
            deck.add(new DevCard("Year of Plenty"));
            deck.add(new DevCard("Monopoly"));
        }
        for (int i = 0; i < 5; i++) { //5 victory cards
            deck.add(new DevCard("Victory Point"));
        }
        
        //shuffle the deck, source: https://www.geeksforgeeks.org/shuffle-or-randomize-a-list-in-java/
        Collections.shuffle(deck); 
    }

    public Card drawCard() {
        Card c = deck.remove(0); //remove the first card from the deck
        return c;
    }

}
