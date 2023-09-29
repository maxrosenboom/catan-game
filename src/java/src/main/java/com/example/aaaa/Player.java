package com.example.aaaa;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
public class Player {
    int victoryPoints;
    String name;
    Color color;
    ArrayList<ResourceCard> resources = new ArrayList<ResourceCard>();
    ArrayList<DevCard> devCards = new ArrayList<DevCard>();
    boolean longestRoad = false;
    //roads, cities, settlements
    boolean biggestArmy = false;
    int playerIndex;
    int army;
    ArrayList<RoadNode> road1 = new ArrayList<>(); //realistically there's only gonna be 2 roads
    ArrayList<RoadNode> road2 = new ArrayList<>();


    int[] resourceCount; //B = 0, O = 1, W = 2, G = 3, L = 4


    Player(String name, Color color) {
        this.name = name;
        this.color = color;
        this.army = 0;
        this.victoryPoints = 0;
        resourceCount = new int[]{0, 0, 0, 0, 0};
    }

    Player(String name, String color, int index) {
        playerIndex = index;
        this.name = name;
        //this.color = color;
        this.color = Color.GREEN; //will change later
        switch(color){
            case("Red"):
                this.color = Color.FIREBRICK;
                break;
            case("Blue"):
                this.color = Color.DODGERBLUE;
                break;
            case("Orange"):
                this.color = Color.DARKORANGE;
                break;
            case("Green"):
                this.color = Color.LIMEGREEN;
                break;
            case("Gray"):
                this.color = Color.GRAY;
                break;
            case("Brown"):
                this.color = Color.SADDLEBROWN;
                break;
        }
        this.army = 0;
        this.victoryPoints = 0;
    }

    public void setColor(Color col){
        color = col;
    }

    public String getName() {
        return this.name;
    }

    public int getIndex() {
        return this.playerIndex;
    }

    public void addCard(ResourceCard r){ resources.add(r); }

    public ArrayList<Shape> idleSide(int x, int y){
        ArrayList<Shape> shapes = new ArrayList<>();
        Rectangle reset = new Rectangle(200, 200);
        reset.setX(x+10);
        reset.setY(y);
        reset.setFill(Color.AZURE);
        shapes.add(reset);
        Rectangle banner = new Rectangle(200, 40);
        banner.setFill(color);
        Text nm = new Text(name);
        //a calculate res function will be used to get an array of nums needed

       /* Rectangle brick = new Rectangle(15, 15, Color.CHOCOLATE);
        Rectangle ore = new Rectangle(15, 15, Color.SLATEBLUE);
        Rectangle wool = new Rectangle(15, 15, Color.SPRINGGREEN);
        Rectangle lumber = new Rectangle(15, 15, Color.FORESTGREEN);
        Rectangle grain = new Rectangle(15, 15, Color.WHEAT);*/
        UIManager uiTemp = new UIManager();
        Rectangle[] res = uiTemp.resourceRectangles(15);
        for(int i = 0; i<res.length; i++){
            Text temp = new Text(""+getRes()[i]);
            res[i].setArcWidth(5);
            res[i].setArcHeight(5);
            if(i%2==0){
                res[i].setX(x);
                res[i].setY(y+50+15*i);
                temp.setX(x+25);
                temp.setY(y+62+15*i);
            }
            else{
                res[i].setX(x+100);
                res[i].setY(y+50+15*(i-1));
                temp.setX(x+125);
                temp.setY(y+62+15*(i-1));
            }
            shapes.add(res[i]);
            shapes.add(temp);
        }
        banner.setX(x);
        banner.setY(y);
        nm.setX(x+10);
        nm.setY(y+27);
        //stats.setX(x+15);
       // stats.setY(y+45);
        nm.setFill(Color.WHITE);
        nm.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        shapes.add(banner);
        //shapes.add(stats);
        shapes.add(nm);

        return shapes;
    }
    public ArrayList<Shape> displayHand(int x, int y){
        ArrayList<Shape> shapes = new ArrayList<>();
        for(int i = 0; i<resources.size();i++){
        //fill
        }
        return shapes;

    }

    
    public int[] getRes(){
        //[brick 0 , ore 1 , wool 2, lumber 3, grain 4]
        int[] res = {0,0,0,0,0};
        for(int i = 0; i<resources.size(); i++){
            Resource r = resources.get(i).r;
            switch(r.name){
                case("Brick"):
                    res[0]+=1;
                    break;

                case("Ore"):
                    res[1]+=1;
                    break;

                case("Wool"):
                    res[2]+=1;
                    break;

                case("Grain"):
                    res[3]+=1;
                    break;

                case("Lumber"):
                    res[4]+=1;
                    break;
                default:
                    System.out.println("Didn't hit");
            }
        }
        resourceCount = res;
        return res;
    }

    public void tradeRemove(int amount, String resource) {
        int numRemoved = 0;
        for (int i = 0; i < resources.size(); i++) {
            String type = "Resource Card: " + resource;
            if (resources.get(i).getName().equals(type)) {
                resources.remove(i);
                System.out.println("Removing " + resource + "...");
                numRemoved++;
            }
            if (numRemoved == amount) {
                return;
            }
        }
    }

    public void tradeAdd(int amount, String resource) {
        for (int i = 0; i <= amount; i++) {
            ResourceCard card = new ResourceCard(resource);
            resources.add(card);
        }
    }
}
