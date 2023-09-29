package com.example.aaaa;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.*;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class HelloApplication extends Application {
    PlayerHandler ph = new PlayerHandler();
    Board  b = new Board();
    final ArrayList<Vertex> verts = b.vertices;
    int state = 0;

    UIManager ui = new UIManager();
    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        setUp(stage);
    }
    public void initialBoard(Stage stage){
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 800, Color.AZURE);

        //drawing board
        b.boardInit(ph);

        ArrayList<Shape> draws = b.draw(ph);
        ArrayList<Text> nums = b.drawNums();

        //top banner
        final Rectangle banner = new Rectangle(1500, 50, ph.currentPlayer.color);
        final Text name = ui.textUI(ph.currentPlayer.name+"'s Turn", 550, 35, Color.WHITE);

        root.getChildren().addAll(draws);
        root.getChildren().addAll(nums);
        root.getChildren().add(banner);
        root.getChildren().add(name);





        //also temporary, ROLL BUTTON
        Group rollButton = ui.buttonUI("Roll", 70, 380, 45);

        rollButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Shape> rollDemo = b.roll();
                root.getChildren().addAll(rollDemo);

                for(int i = 0; i<4; i++){
                    //updating side values
                    ArrayList<Shape> update = ph.players.get(i).idleSide(1000, 75+150*(i)); //there will be a way to refactor this
                    root.getChildren().addAll(update);
                }
            }
        });

        //TRADE BUTTON
        Group tradeButton = ui.buttonUI("Trade", 70, 450, 40);
        tradeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                trade(stage);
            }
        });

        //BUILD BUTTON

        Group buildButton = ui.buttonUI("Build", 70, 520, 40);
        buildButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                buildInit(stage);
            }
        });

        //END TURN BUTTON
        Group nextButton = ui.buttonUI("End Turn", 70, 590, 20);
        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int n = ph.currentPlayer.getIndex();
                if (n == 3) {
                    ph.setCurrent(ph.players.get(0));
                } else {
                    ph.setCurrent(ph.players.get(n + 1));
                }
                initialBoard(stage);
            }
        });
        root.getChildren().addAll(tradeButton);
        root.getChildren().add(buildButton);
        root.getChildren().add(nextButton);



        //Vertex hell.
        for(int i = 0; i<verts.size(); i++){
            final Vertex vert = verts.get(i);
            final Circle v = verts.get(i).c;
            final Piece st = verts.get(i).settled;


            //On hover, color blue and color adjacent ones red
            v.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            for(int j = 0; j<vert.adjVerts.size(); j++){
                                vert.adjVerts.get(j).c.setFill(Color.BLUE);
                                vert.adjVerts.get(j).c.setOpacity(1.0);
                            }
                            v.setFill(Color.RED);
                            v.setOpacity(1.0);
                        } });


                v.addEventHandler(MouseEvent.MOUSE_CLICKED,
                        new EventHandler<MouseEvent>(){
                            @Override
                            public void handle (MouseEvent e){
                                if(state==3){
                                    RoadNode temp = new RoadNode(vert, ph.currentPlayer);
                                    if(!temp.valid()){
                                        System.out.println("Invalid");
                                    }
                                    else{
                                    state = 0;
                                    board(stage);
                                    }
                                }
                                else{
                                Settlement newSettlement = new Settlement(vert,ph.currentPlayer, ph.currentPlayer.color);
                                if(vert.addSettled(newSettlement, state)){
                                    root.getChildren().add(vert.settled.getPolygon());
                                    final Shape set = vert.settled.getPolygon();
                                    //System.out.println("What is going on");
                                    set.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                            new EventHandler<MouseEvent>(){
                                                @Override
                                                public void handle (MouseEvent e){
                                                    //System.out.println("Clicked hit");
                                                    if(state==3){
                                                        RoadNode temp = new RoadNode(vert, ph.currentPlayer);
                                                        while(!temp.valid()){
                                                            System.out.println("Invalid Placement");
                                                        }
                                                        state = 0;
                                                        board(stage);
                                                    }
                                                    vert.makeCity(ph.currentPlayer, state);
                                                    state = 0;
                                                    board(stage);


                                                }

                                            });
                                    state = 0;
                                    board(stage);
                                }

                            } }
                } );

            v.addEventHandler(MouseEvent.MOUSE_EXITED, //unhover for vertex
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            for(int j = 0; j<vert.adjVerts.size(); j++){
                                vert.adjVerts.get(j).c.setFill(Color.BLACK);
                                vert.adjVerts.get(j).c.setOpacity(0.5);
                            }
                            v.setFill(Color.BLACK);
                            v.setOpacity(0.33);
                        }
                    });
        }

        //sidebars
        for(int i = 0; i<ph.players.size();i++){ root.getChildren().addAll(ph.players.get(i).idleSide(1000, 75 + 150*i)); }
        root.getChildren().add(rollButton);
        stage.setTitle("Main Board");
        stage.setScene(scene);
        stage.show();
    }



    public void setUp(Stage stage){
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 800, Color.AZURE);
        Text t = ui.textUI("Select Players: ", 490, 150, Color.DARKSLATEGRAY);
        ArrayList<TextField> fields = new ArrayList<>();
        ArrayList<ComboBox> combos = new ArrayList<>();
        final int size = 4; //changeable
        for(int i = 0; i<size; i++){
            TextField tf = new TextField("Player " + (i+1));
            tf.setTranslateX(420);
            tf.setTranslateY(200 + 50*i);
            fields.add(tf);
            ComboBox combo = new ComboBox();
            //this will incorp game engine
            combo.getItems().add("Red");
            combo.getItems().add("Blue");
            combo.getItems().add("Orange");
            combo.getItems().add("Gray");
            combo.getItems().add("Green");
            combo.getItems().add("Brown");
            combo.setTranslateX(620);
            combo.setTranslateY(200+50*i);
            combos.add(combo);
        }
        Group r = ui.buttonUI("Start", 500, 400, 50, Color.DARKSLATEGRAY);
        r.addEventHandler(MouseEvent.MOUSE_CLICKED,

                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle (MouseEvent e){ //needs to become adjustable
                        ArrayList<Object> selections = new ArrayList<>();
                        boolean nulled = false;
                        for(int i = 0; i<size; i++){
                            if(combos.get(i).getValue()==null){
                                nulled = true;
                            }
                            selections.add(combos.get(i).getValue());
                        }
                        if(!nulled && unique(selections)){
                            //TODO make this dynamic

                        final Player p1 = new Player(fields.get(0).getText(), combos.get(0).getValue().toString(), 0);
                        final Player p2 = new Player(fields.get(1).getText(), combos.get(1).getValue().toString(), 1);
                        final Player p3 = new Player(fields.get(2).getText(), combos.get(2).getValue().toString(), 2);
                        final Player p4 = new Player(fields.get(3).getText(), combos.get(3).getValue().toString(), 3);
                        ph.addPlayer(p1);
                        ph.addPlayer(p2);
                        ph.addPlayer(p3);
                        ph.addPlayer(p4);
                        ph.setCurrent(p1);
                        initialBoard(stage);
                        }
                        else{
                            Rectangle warning = new Rectangle(300,  100, Color.CRIMSON );
                            Text warningText = ui.textUI("", 450, 540, Color.AZURE);
                            warning.setX(420);
                            warning.setY(500);
                            if(nulled){
                                warningText.setText("Please choose colors\nfor all four players");
                            }
                            else if(!unique(selections)){
                                warningText.setText("Please do not pick\nduplicate colors");
                            }
                            root.getChildren().add(warning);
                            root.getChildren().add(warningText);
                        }

                    }});

        root.getChildren().addAll(fields);
        root.getChildren().addAll(combos);
        root.getChildren().add(t);
        root.getChildren().add(r);
        stage.setScene(scene);
        stage.show();

    }
    public void refresh(Stage stage){ board(stage); }

    public void board(Stage stage){
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 800, Color.AZURE);

        //drawing board
        ArrayList<Shape> draws = b.draw(ph);
        ArrayList<Text> nums = b.drawNums();

        //top banner
        final Rectangle banner = new Rectangle(1500, 50, ph.currentPlayer.color);
        final Text name = ui.textUI(ph.currentPlayer.name+"'s Turn", 550, 35, Color.WHITE);

        root.getChildren().addAll(draws);
        root.getChildren().addAll(nums);
        root.getChildren().add(banner);
        root.getChildren().add(name);


        //also temporary, ROLL BUTTON
        Group rollButton = ui.buttonUI("Roll", 70, 380, 45);


        rollButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Shape> rollDemo = b.roll();
                root.getChildren().addAll(rollDemo);

                for(int i = 0; i<4; i++){
                    //updating side values
                    ArrayList<Shape> update = ph.players.get(i).idleSide(1000, 75+150*(i)); //there will be a way to refactor this
                    root.getChildren().addAll(update);
                }
            }
        });

        //TRADE BUTTON
        Group tradeButton = ui.buttonUI("Trade", 70, 450, 40);
        tradeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                trade(stage);
            }
        });

        //BUILD BUTTON

        Group buildButton = ui.buttonUI("Build", 70, 520, 40);
        buildButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                buildInit(stage);
            }
        });

        //END TURN BUTTON
        Group nextButton = ui.buttonUI("End Turn", 70, 590, 20);
        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int n = ph.currentPlayer.getIndex();
                if (n == 3) {
                    ph.setCurrent(ph.players.get(0));
                } else {
                    ph.setCurrent(ph.players.get(n + 1));
                }
                initialBoard(stage);
            }
        });
        root.getChildren().addAll(tradeButton);
        root.getChildren().add(buildButton);
        root.getChildren().add(nextButton);




        //sidebars
        for(int i = 0; i<ph.players.size();i++){ root.getChildren().addAll(ph.players.get(i).idleSide(1000, 75 + 150*i)); }
        root.getChildren().add(rollButton);
        stage.setTitle("Main Board");
        stage.setScene(scene);
        stage.show();
    }

    //i do not know where to put this
    private boolean unique(ArrayList<Object> objs){
        for(int i = 0; i<objs.size()-1; i++){
            for(int j = i+1; j<objs.size();j++){
                if(objs.get(i)==objs.get(j)){
                    return false;
                }
            }
        }
        return true;
    }

    //**TRADE PAGE**
    public void trade(Stage stage){
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 800, Color.AZURE);
        Player p = ph.currentPlayer;
        ArrayList<Player> ps = ph.players;
        ComboBox playerChoice = new ComboBox<>();
        playerChoice.setTranslateY(130);
        playerChoice.setTranslateX(620);
        for(int i = 0; i<ps.size();i++){ if(ps.get(i)!=p){ playerChoice.getItems().add(ps.get(i).name); } }
        Text pt0 = ui.textUI("Trade With: ", 450, 150, Color.DARKSLATEGRAY);
        Text pt1 = ui.textUI("You propose giving: ", 500, 300, Color.DARKSLATEGRAY);
        Text pt2 = ui.textUI("In exchange for: : ", 500, 500, Color.DARKSLATEGRAY);
        Text close = ui.close(stage);
        root.getChildren().add(close);

        close.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle (MouseEvent e){ board(stage); //doesn't work for some reason
            } });

        ArrayList<TextField> tf = new ArrayList<>();
        for(int i = 0; i<2; i++){
            Rectangle[] rect = ui.resourceRectangles(30);
            for(int j = 0; j<rect.length;j++){
                TextField t = new TextField();
                t.setMaxWidth(30);
                t.setTranslateY(340+190*i);
                t.setTranslateX(420+100*j);
                rect[j].setX(380+100*j);
                rect[j].setY(340+190*i);
                root.getChildren().add(rect[j]);
                tf.add(t);
            }
            //these inputs will be used for trades, but need to check that they're ints and legal to trade

        }
        Group submit = ui.buttonUI("Submit", 520, 650, 30, Color.DARKSLATEGRAY);
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Player target = ph.players.get(0);
                for (int i = 0; i < ph.players.size(); i++) {
                    if (ph.players.get(i).getName() == playerChoice.getValue().toString()) {
                        target = ph.players.get(i);
                        break;
                    }
                }
                int prop1 = ((tf.get(0).getText() == "") ? 0 : Integer.parseInt(tf.get(0).getText()));
                int prop2 = ((tf.get(1).getText() == "") ? 0 : Integer.parseInt(tf.get(1).getText()));
                int prop3 = ((tf.get(2).getText() == "") ? 0 : Integer.parseInt(tf.get(2).getText()));
                int prop4 = ((tf.get(3).getText() == "") ? 0 : Integer.parseInt(tf.get(3).getText()));
                int prop5 = ((tf.get(4).getText() == "") ? 0 : Integer.parseInt(tf.get(4).getText()));

                int targ1 = ((tf.get(5).getText() == "") ? 0 : Integer.parseInt(tf.get(5).getText()));
                int targ2 = ((tf.get(6).getText() == "") ? 0 : Integer.parseInt(tf.get(6).getText()));
                int targ3 = ((tf.get(7).getText() == "") ? 0 : Integer.parseInt(tf.get(7).getText()));
                int targ4 = ((tf.get(8).getText() == "") ? 0 : Integer.parseInt(tf.get(8).getText()));
                int targ5 = ((tf.get(9).getText() == "") ? 0 : Integer.parseInt(tf.get(9).getText()));

                if (prop1 <= ph.getCurrentPlayer().getRes()[0] &&
                    prop2 <= ph.getCurrentPlayer().getRes()[1] &&
                    prop3 <= ph.getCurrentPlayer().getRes()[2] &&
                    prop4 <= ph.getCurrentPlayer().getRes()[3] &&
                    prop5 <= ph.getCurrentPlayer().getRes()[4]) {
                    if (targ1 <= target.getRes()[0] &&
                        targ2 <= target.getRes()[1] &&
                        targ3 <= target.getRes()[2] &&
                        targ4 <= target.getRes()[3] &&
                        targ5 <= target.getRes()[4]) {

                            ph.getCurrentPlayer().tradeAdd(targ1, "Brick");
                            ph.getCurrentPlayer().tradeRemove(prop1, "Brick");
                            target.tradeAdd(prop1, "Brick");
                            target.tradeRemove(targ1, "Brick");

                            ph.getCurrentPlayer().tradeAdd(targ2, "Ore");
                            ph.getCurrentPlayer().tradeRemove(prop2, "Ore");
                            target.tradeAdd(prop2, "Ore");
                            target.tradeRemove(targ2, "Ore");

                            ph.getCurrentPlayer().tradeAdd(targ3, "Wool");
                            ph.getCurrentPlayer().tradeRemove(prop3, "Wool");
                            target.tradeAdd(prop3, "Wool");
                            target.tradeRemove(targ3, "Wool");

                            ph.getCurrentPlayer().tradeAdd(targ4, "Grain");
                            ph.getCurrentPlayer().tradeRemove(prop4, "Grain");
                            target.tradeAdd(prop4, "Grain");
                            target.tradeRemove(targ4, "Grain");

                            ph.getCurrentPlayer().tradeAdd(targ5, "Lumber");
                            ph.getCurrentPlayer().tradeRemove(prop5, "Lumber");
                            target.tradeAdd(prop5, "Lumber");
                            target.tradeRemove(targ5, "Lumber");

                            
                        }
                    }
                initialBoard(stage);
            }
        });
        root.getChildren().add(submit);
        root.getChildren().add(playerChoice);

        //sidebar, tempted to make a diff function to standardize the spacing
        for(int i = 0; i<ps.size();i++){root.getChildren().addAll(ps.get(i).idleSide(1000, 75+150*i));}

        root.getChildren().addAll(tf);
        root.getChildren().add(pt1);
        root.getChildren().add(pt2);
        root.getChildren().add(pt0);
        stage.setScene(scene);
        stage.show();


    }
    public void buildInit(Stage stage){
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 800, Color.AZURE);
        Player p = ph.currentPlayer;
        Text close = ui.close(stage);
        root.getChildren().add(close);

        close.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                    @Override
                    public void handle (MouseEvent e){ board(stage); //doesn't work for some reason
                    } });

        root.getChildren().addAll(p.idleSide(1000, 75));
        Group road = ui.buttonUI("Road", 450, 200, 40);
        Group settlement = ui.buttonUI("Settlement", 450 , 270, 10);
        Group city = ui.buttonUI("City", 450, 340, 50);
        Group card = ui.buttonUI("Dev Card", 450, 410, 15);
        Group[] buttons = {road, settlement, city, card};
        String[] str = {"Road", "Settlement", "City", "Dev Card"};
        road.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {  
                    ph.getCurrentPlayer().tradeRemove(1, "Brick");
                    ph.getCurrentPlayer().tradeRemove(1, "Lumber");
                    buildHelper("Road", stage); 
                } });
        settlement.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { 
                ph.getCurrentPlayer().tradeRemove(1, "Brick");
                ph.getCurrentPlayer().tradeRemove(1, "Lumber"); 
                ph.getCurrentPlayer().tradeRemove(1, "Grain");
                ph.getCurrentPlayer().tradeRemove(1, "Wool");
                buildHelper("Settlement", stage); 
            } });
        city.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {  
                ph.getCurrentPlayer().tradeRemove(2, "Grain");
                ph.getCurrentPlayer().tradeRemove(3, "Ore");
                buildHelper("City", stage);
             } });


        Rectangle[] rs = ui.resourceRectangles(30); //LB   LBGW   GGOOO   GWO BOWGL
        int[][] grid = {{4,0},{4,0,3,2},{3,3,1,1,1},{3,2,1}};
        for(int i = 0; i< grid.length; i++){
            for(int j = 0; j<grid[i].length;j++){
                Rectangle r = ui.resourceRectangles(30)[grid[i][j]];
                r.setX(650 + 40*j);
                r.setY(210 + 70*i);
                root.getChildren().add(r);
            }
        }
        root.getChildren().add(card);
        root.getChildren().add(road);
        root.getChildren().add(settlement);
        root.getChildren().add(city);

        stage.setScene(scene);
        stage.show();





    }

    public static void main(String args[]) {
            launch();
    }

    public void buildHelper(String str, Stage stage){
        if(str.equals("Road")){
            //TODO
            state = 3;
            board(stage);

        }
        else if(str.equals("Settlement")){
            state = 1;
            board(stage);
        }
        else if(str.equals("City")){

            state = 2;
            board(stage);

        }
    }


}
