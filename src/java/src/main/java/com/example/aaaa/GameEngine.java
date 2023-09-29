package com.example.aaaa;

import java.util.ArrayList;
import java.util.Scanner;

public class GameEngine {

    static ArrayList<Player> players;
    static ArrayList<String> colors;
    
    public static void initializeGame() {
        players = new ArrayList<Player>();
        addColors();

        Scanner input = new Scanner(System.in);
        for (int i = 0; i < 4; i ++) {
            System.out.println("Player " + (i + 1) + " enter your name:");
            String name = input.nextLine();

            System.out.println("Hello " + name + "! What color would you like to be?");
            System.out.println("Available colors: ");
            String line = "";
            for (int j = 0; j < colors.size(); j++) {
                if (j == colors.size() - 1) {
                    line += colors.get(j);
                } else {
                    line += colors.get(j) + ", ";
                }   
            }
            System.out.println(line);
            String color;
            while (true) {
                color = input.nextLine();
                if (colors.contains(color)) {
                    System.out.println("You chose " + color + "!");
                    colors.remove(color);
                    break;
                } else {
                    System.out.println("Invalid color, try again.");
                }
            }
            Player player = new Player(name, color,0);
            players.add(player);
        }
    }

    public static void runGame() {
        while (true) {
            for (int i = 0; i < players.size(); i++) {
                processTurn(players.get(i));
            }
            checkWinner();
            break;
        }
    }

    public static void checkWinner() {
        for (Player p : players) {
            if (p.victoryPoints >= 10) {
                System.out.println(p.getName() + " won!");
                System.exit(0);
            }
        }
    }

    public static void processTurn(Player player) {
        System.out.println(player.getName() + "'s turn.");
        System.out.println("Victory points: " + player.victoryPoints);
        System.out.println("Current inventory contents:");
        String line = "";
        for (int i = 0; i < player.resources.size(); i++) {
            if (i == player.resources.size() - 1) {
                line += player.resources.get(i);
            } else {
                line += player.resources.get(i) + ", ";
            }
        }
        System.out.println("Resources: " + line);
        line = "";
        for (int i = 0; i < player.devCards.size(); i++) {
            if (i == player.devCards.size() - 1) {
                line += player.devCards.get(i);
            } else {
                line += player.devCards.get(i) + ", ";
            }
        }
        System.out.println("Development Cards: " + line);
        System.out.println("You must first roll the dice...");
        Dice d = new Dice();
        int[] roll = d.roll();
        int sum = roll[0] + roll[1];
        System.out.println("You rolled a " + sum + "!");

        //TODO: give players their resource cards

        if (sum == 7) { //Player rolled a 7 - move robber first
            System.out.println("You rolled a 7, you can move the robber.");
            //TODO
        } 

        Scanner input = new Scanner(System.in);
        int choice = 0;
        while (true) {
            System.out.println("What would you like to do?");
            System.out.println("1. Trade, 2. Build, 3. Play Development Card");
            choice = input.nextInt();
            if (choice < 1 || choice > 3) {
                System.out.println("Invalid choice, try again.");
            } else {
                break;
            }
        }

        if (choice == 1) {
            System.out.println("You chose to trade.");
            System.out.println("Who would you like to trade with?");
            String player_list = "";
            ArrayList<Player> choices = new ArrayList<Player>();
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i) != player) {
                    choices.add(players.get(i));
                }
            }
            for (int i = 0; i < choices.size(); i++) {
                if (i == choices.size() - 1) {
                    player_list += (i + ": " + choices.get(i).getName());
                } else {
                    player_list += (i + ": " + choices.get(i).getName() + ", ");
                }
            }
            System.out.println(player_list);

            int player_choice = input.nextInt();
            Player trader = choices.get(player_choice);
            System.out.println("You chose to trade with: " + trader.getName());
            System.out.println(trader.getName() + ", do you accept?");
            System.out.println("1. Yes, 2. No");
            int trader_choice = input.nextInt();
            if (trader_choice == 1) {
                String inventory1 = "";
                String inventory2 = "";
                for (int i = 0; i < player.resources.size(); i++) {
                    if (i == player.resources.size() - 1) {
                        inventory1 += player.resources.get(i);
                    } else {
                        inventory1 += player.resources.get(i) + ", ";
                    }
                }
                for (int i = 0; i < trader.resources.size(); i++) {
                    if (i == trader.resources.size() - 1) {
                        inventory2 += trader.resources.get(i);
                    } else {
                        inventory2 += trader.resources.get(i) + ", ";
                    }
                }
                System.out.println(player.getName() + "'s Inventory: " + inventory1);
                System.out.println(trader.getName() + "'s Inventory: " + inventory2);
                System.out.println(player.getName() + ", how much would you like to trade?");
                //TODO

            } else {
                System.out.println("Trader doesn't want to trade.");
            }  
        } else if (choice == 2) {
            System.out.println("You chose to build! What would you like to build?");
            System.out.println("Prices:");
            System.out.println("1. Road: 1 Lumber, 1 Brick");
            System.out.println("2. Settlement: 1 Lumber, 1 Brick, 1 Wool, 1 Grain");
            System.out.println("3. City: 2 Grain, 3 Ore");
            int player_choice = input.nextInt();
            System.out.println("You chose: " + player_choice);



            //TODO once map is complete
        } else if (choice == 3) {
            System.out.println("You chose to play a development card.");
            if (player.devCards.size() > 0) {
                String availableCards = "";
                for (int i = 0; i < player.devCards.size(); i++) {
                    if (i == player.devCards.size() - 1) {
                        availableCards += (i + ": " + player.devCards.get(i));
                    } else {
                        availableCards += (i + ": " + player.devCards.get(i) + ", ");
                    }
                }
                System.out.println("Available Cards: " + availableCards);
                System.out.println("Which one would you like to play?");
                int player_choice = input.nextInt();
                Card choiceCard = player.devCards.get(player_choice);

            } else {
                System.out.println("You don't have any development cards available.");
            }
        }

    }

    static void addColors() {
        colors = new ArrayList<String>();

        colors.add("Red");
        colors.add("Blue");
        colors.add("Yellow");
        colors.add("White");
        colors.add("Green");
        colors.add("Brown");
    }

}
