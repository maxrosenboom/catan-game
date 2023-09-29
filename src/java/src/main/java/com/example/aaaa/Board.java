package com.example.aaaa;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.lang.reflect.Array;
import java.util.ArrayList;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Board{
//
// THE BOARD IS AN OBSERVER PATTERN --- ABLE TO OBSERVE THE GAME STATE THROUGHOUT WHEN THE PROGRAM RUNS
//

    private static double center = 600.0;
    private static double size = 70.0;
    private static Tile tiles[]; //will have 19 tiles
    private static double positionX[] = {
            center - 1.74*size, center, center + 1.74*size,
            center - 2.61*size, center - 0.87*size, center+0.87*size, center+2.61*size,
            center-3.45*size, center - 1.73*size, center, center + 1.73*size, center + 3.45*size,
            center - 2.61*size, center - 0.87*size, center+0.87*size, center+2.61*size,
            center - 1.74*size, center, center + 1.74*size,
    };

    private static double positionY[] = {
            size, size, size,
            2.5*size, 2.5* size, 2.5*size, 2.5*size,
            4*size, 4*size, 4*size, 4*size, 4*size,
            5.5*size, 5.5* size, 5.5*size, 5.5*size,
            7*size, 7*size, 7*size
    };

    public ArrayList<Vertex> vertices = new ArrayList<>();

    Dice dice;

    Board(){
        dice = new Dice();
       tiles = this.initTiles(); //leaves room for a random option
        //sketchy tho
        this.mergingBoard();
        this.removeDuplicates();
        for(int i = 0; i<vertices.size();i++){
            vertices.get(i).jankyAdjacent(vertices, size);
        }
    }
    public static Tile[] initTiles(){
        Tile tile0 = new Tile(10, new Ore(), positionX[0], positionY[0]);
        Tile tile1 = new Tile(2, new Wool(), positionX[1], positionY[1]);
        Tile tile2 = new Tile(9, new Lumber(), positionX[2], positionY[2]);
        Tile tile3 = new Tile(12, new Grain(), positionX[3], positionY[3]);
        Tile tile4 = new Tile(6, new Brick(), positionX[4], positionY[4]);
        Tile tile5 = new Tile(4, new Wool(), positionX[5], positionY[5]);
        Tile tile6 = new Tile(10, new Brick(), positionX[6], positionY[6]);
        Tile tile7 = new Tile(9, new Grain(), positionX[7], positionY[7]);
        Tile tile8 = new Tile(11, new Lumber(), positionX[8], positionY[8]);
        Tile tile9 = new Tile(0, new Grain(), positionX[9], positionY[9]); //GOTTA HANDLE ROBBER
        Tile tile10 = new Tile(3, new Lumber(), positionX[10], positionY[10]);
        Tile tile11 = new Tile(8, new Ore(), positionX[11], positionY[11]);
        Tile tile12 = new Tile(8, new Lumber(), positionX[12], positionY[12]);
        Tile tile13 = new Tile(3, new Ore(), positionX[13], positionY[13]);
        Tile tile14 = new Tile(4, new Grain(), positionX[14], positionY[14]);
        Tile tile15 = new Tile(5, new Wool(), positionX[15], positionY[15]);
        Tile tile16 = new Tile(5, new Brick(), positionX[16], positionY[16]);
        Tile tile17 = new Tile(6, new Grain(), positionX[17], positionY[17]);
        Tile tile18 = new Tile(11, new Wool(), positionX[18], positionY[18]);
        Tile[] arr = {tile0,tile1,tile2,tile3,tile4,tile5,tile6,tile7,tile8,tile9,tile10,tile11,tile12,tile13,tile14,tile15,tile16,tile17,tile18};
        return arr;
    }

    private void mergingBoard(){
        for(int i = 0; i< tiles.length;i++){
            for(int j = 0; j<tiles.length; j++){
                if(i!=j){
                    tiles[i].mergeTiles(tiles[j]);

                }
            }
            vertices.addAll(tiles[i].vs);
        }

    }

    public Tile getTileFromCoord(int i){
        return tiles[i];
    }

    public ArrayList<Shape> draw(PlayerHandler ph){
        //for GUI
        ArrayList<Shape> polys = new ArrayList<>();
        Polygon back = new Polygon();
        double bsize = 350;
        back.getPoints().setAll(
                1.5*bsize, 0.13*bsize,
                0.5*bsize, 0.13*bsize,
                0.0, 1.0*bsize,
                0.5*bsize, 1.87* bsize,
                1.5*bsize, 1.87*bsize,
                2.0*bsize, 1.0*bsize

        );
        back.setTranslateX(center/2 - 20);
        back.setTranslateY(25);
        back.setFill(Color.DEEPSKYBLUE);
        polys.add(back);
        for(int i = 0; i<tiles.length; i++){
            Shape t = tiles[i].draw().get(0);
            //t.setTranslateX(positionX[i]);
           //t.setTranslateY(positionY[i]);

            Shape c = tiles[i].draw().get(1);
            c.setTranslateX(positionX[i]+size/2.5);
            c.setTranslateY(positionY[i]+1.33*size);
            polys.add(t);
            polys.add(c);
        }
        for(int i = 0; i<ph.players.size(); i++){
            for(int j = 0;j<ph.players.get(i).road1.size();j++){
                polys.add(ph.players.get(i).road1.get(j).draw());
            }
            for(int j = 0;j<ph.players.get(i).road2.size();j++){
                polys.add(ph.players.get(i).road2.get(j).draw());
            }
        }
        for(int i = 0; i<vertices.size(); i++){
            Shape v = vertices.get(i).draw().get(0);
            polys.add(v);
        }
        //System.out.println(vertices.size());
        return polys;

    }

    public ArrayList<Text> drawNums(){
        ArrayList<Text> nums = new ArrayList<>();
        for(int i = 0; i< tiles.length; i++) {
            Text t = new Text(""+tiles[i].getNum());
            t.setFill(Color.SADDLEBROWN);
            t.setTranslateX(positionX[i]+0.2*size);
            t.setTranslateY(positionY[i]+1.4*size);
            t.resize(200.0, 10.0);
            t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            nums.add(t);
        }
        return nums;
    }
    private void removeDuplicates(){
        ArrayList<Vertex> newVs = new ArrayList<>();
        for(int i = 0; i<vertices.size(); i++){
            if(!newVs.contains(vertices.get(i))){
                newVs.add(vertices.get(i));
            }
        }
        vertices = newVs;
    }

    public ArrayList<Shape> roll(){
        int[] nums = dice.roll();
        int one = nums[0];
        int two = nums[1]; //separated just for checks
        int sum = one+two;
        for(int i = 0; i<tiles.length;i++){
            tiles[i].rollHelper(sum);
        }
        return dice.draw(one, two);

    }

    public void boardInit(PlayerHandler ph){
        Player p1 = ph.players.get(0);
        Player p2 = ph.players.get(1);
        Player p3 = ph.players.get(2);
        Player p4 = null;
        if(ph.players.size()==4){
            p4 = ph.players.get(3);
        }
        double[][] redPositions = {{center-1.74*size+2.0*size*Math.cos(0.524)-(1.0*size*Math.sin(0.524)) , size+1.0*size*Math.cos(0.524) + ( 2.0*size*Math.sin(0.524)) },{center-3.45*size+2.0*size*Math.cos(0.524)-(1.0*size*Math.sin(0.524)) , 4*size+1.0*size*Math.cos(0.524) + ( 2.0*size*Math.sin(0.524))}}; //bottom right, bottom right
        double[][] bluePositions = {{center-2.61*size+2.0*size*Math.cos(0.524)-(1.0*size*Math.sin(0.524)) ,5.5*size+1.0*size*Math.cos(0.524) + ( 2.0*size*Math.sin(0.524))},{center+0.87*size+2.0*size*Math.cos(0.524)-(1.0*size*Math.sin(0.524)) , 5.5*size+1.0*size*Math.cos(0.524) + ( 2.0*size*Math.sin(0.524))}};
        double[][] whitePositions = {{center-2.61*size+2.0*size*Math.cos(0.524)-(1.0*size*Math.sin(0.524)) ,2.5*size+1.0*size*Math.cos(0.524) + ( 2.0*size*Math.sin(0.524))},{center+1.73*size+2.0*size*Math.cos(0.524)-(1.0*size*Math.sin(0.524)) , 4*size+1.0*size*Math.cos(0.524) + ( 2.0*size*Math.sin(0.524))}};
        double[][] orangePositions = {{center-0.87*size+2.0*size*Math.cos(0.524)-(1.0*size*Math.sin(0.524)) , 5.5*size+1.0*size*Math.cos(0.524) + ( 2.0*size*Math.sin(0.524))},{center+1.74*size+1.5*size*Math.cos(0.524)-(1.87*size*Math.sin(0.524)) ,size+1.87*size*Math.cos(0.524) + ( 1.5*size*Math.sin(0.524))}};//2nd isn't at bottom right but BOTTOM
        Vertex redV1 = findVertex(redPositions[0][0], redPositions[0][1]);
        Vertex redV2 = findVertex(redPositions[1][0], redPositions[1][1]);
        Vertex blueV1 = findVertex(bluePositions[0][0], bluePositions[0][1]);
        Vertex blueV2 = findVertex(bluePositions[1][0], bluePositions[1][1]);
        Vertex whiteV1 = findVertex(whitePositions[0][0], whitePositions[0][1]);
        Vertex whiteV2 = findVertex(whitePositions[1][0], whitePositions[1][1]);
        Vertex orangeV1 = findVertex(orangePositions[0][0], orangePositions[0][1]);
        Vertex orangeV2 = findVertex(orangePositions[1][0], orangePositions[1][1]);

        redV1.player = p1;
        redV2.player = p1;
        blueV1.player = p2;
        blueV2.player = p2;
        whiteV1.player = p3;
        whiteV2.player = p3;
        orangeV1.player = p4;
        orangeV2.player = p4;

        redV1.addSettled(new Settlement(redV1, p1, p1.color), 1);
        redV2.addSettled(new Settlement(redV2, p1, p1.color), 1);
        blueV1.addSettled(new Settlement(blueV1, p2, p2.color), 1);
        blueV2.addSettled(new Settlement(blueV2, p2, p2.color), 1);
        whiteV1.addSettled(new Settlement(whiteV1, p3, p3.color), 1);
        whiteV2.addSettled(new Settlement(whiteV2, p3, p3.color), 1);
        if(p4!=null){
            orangeV1.addSettled(new Settlement(orangeV1, p4, p4.color), 1);
            orangeV2.addSettled(new Settlement(orangeV2, p4, p4.color), 1);
        }
        Vertex road1 = redV1.adjVerts.get(2);

        RoadNode r1 = new RoadNode(redV1, p1);
        p1.road1.add(r1);
        redV1.roadNode = r1;
        RoadNode rr1 = new RoadNode(road1, p1);
        rr1.valid();

        Vertex road2 = redV2.adjVerts.get(2);

        RoadNode r2 = new RoadNode(redV2, p1);
        p1.road2.add(r2);
        redV2.roadNode = r2;
        RoadNode rr2 = new RoadNode(road2, p1);
        rr2.valid();

        Vertex road3 = blueV1.adjVerts.get(2);

        RoadNode r3 = new RoadNode(blueV1, p2);
        p2.road1.add(r3);
        blueV1.roadNode = r3;
        RoadNode rr3 = new RoadNode(road3, p2);
        rr3.valid();

        Vertex road4 =blueV2.adjVerts.get(0);

        RoadNode r4 = new RoadNode(blueV2, p2);
        p2.road2.add(r4);
        blueV2.roadNode = r4;
        RoadNode rr4 = new RoadNode(road4, p2);
        rr4.valid();

        Vertex road5 = whiteV1.adjVerts.get(1);

        RoadNode r5 = new RoadNode(whiteV1, p3);
        p3.road1.add(r5);
        whiteV1.roadNode = r5;
        RoadNode rr5 = new RoadNode(road5, p3);
        rr5.valid();

        Vertex road6 =whiteV2.adjVerts.get(0);

        RoadNode r6 = new RoadNode(blueV2, p3);
        p3.road2.add(r6);
        whiteV2.roadNode = r6;
        RoadNode rr6 = new RoadNode(road6, p3);
        rr6.valid();

        if(p4!=null){
            Vertex road7 = orangeV1.adjVerts.get(2);

            RoadNode r7 = new RoadNode(orangeV1, p4);
            p4.road1.add(r7);
            orangeV1.roadNode = r7;
            RoadNode rr7 = new RoadNode(road7, p4);
            rr7.valid();

            Vertex road8 =orangeV2.adjVerts.get(0);

            RoadNode r8 = new RoadNode(orangeV2, p4);
            p4.road2.add(r8);
            orangeV2.roadNode = r8;
            RoadNode rr8 = new RoadNode(road8, p4);
            rr8.valid();
        }



    }
    public Vertex findVertex(double x, double y){
        Vertex v = null;
        for(int i = 0; i<vertices.size();i++){
            if(vertices.get(i).equals(new Vertex(x,y))){
                v = vertices.get(i);
                break;
            }
        }
        if(v==null){
            System.out.println("Null X:" + x + "Null y" + y);
        }
        return v;
    }
}

