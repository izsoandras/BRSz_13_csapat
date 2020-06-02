package UI;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static model.GameParams.LABYRINTH_HEIGHT;
import static model.GameParams.LABYRINTH_WIDTH;

public class main_ui extends Application {
    private Stage mainWindow;
    private Scene Menu, Settings, Toplist, Game, Multi1, MultiIP, MultiWait, MultiGame, NameIn;

    static int block_size = 10; //10pixel egy blokk mérete
    int width=LABYRINTH_WIDTH;       //blokkok száma
    int height=LABYRINTH_HEIGHT;

    @Override
    public void start(Stage ps) {
        mainWindow = ps;

        constructMenu();
        constructSettings();
        constructToplist();
        constructGame();
        constructMulti1();
        constructMultiIP();
        constructMultiWait();
        constructMultiGame();
        constructNameIn();

        mainWindow.setScene(Menu);
        mainWindow.setTitle("Snake Game");
        mainWindow.show();
    }
    private void constructMenu(){
        Label lb = new Label("Menü");
        Button btnGame =new Button("Single Game");
        btnGame.setOnAction(e ->{mainWindow.setScene(Game);});

        Button btnLoadGame =new Button("Load Game");
        btnLoadGame.setOnAction(e ->{mainWindow.setScene(Game);});  //ide még kell egy játék betöltés is

        Button btnSettings =new Button("Settings");
        btnSettings.setOnAction(e ->{mainWindow.setScene(Settings);});

        Button btnMulti =new Button("Multi player");
        btnMulti.setOnAction(e ->{mainWindow.setScene(Multi1);});     //multiplayer kezelés

        Button btnToplist =new Button("Toplista");
        btnToplist.setOnAction(e ->{mainWindow.setScene(Toplist);});

        VBox root1= new VBox();
        root1.getChildren().add(lb);
        root1.setAlignment(Pos.TOP_CENTER);
        VBox root = new VBox();
        root.setSpacing(60);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(root1, btnGame, btnLoadGame, btnSettings, btnMulti, btnToplist);
        Menu=new Scene(root, 600, 600);
    }
    private void constructGame(){
        Label lb = new Label("Játék");
        Label Score = new Label("Score: ");      //játék állása kell

        Button btnBack = new Button("Back");    //játékot el kell menteni
        btnBack.setOnAction(e->{mainWindow.setScene(Menu);});


        VBox root = new VBox();
        root.getChildren().addAll(lb, Score, btnBack);
        Game = new Scene(root, 600,600);
        Canvas c = new Canvas(LABYRINTH_WIDTH*block_size, LABYRINTH_HEIGHT*block_size);
        GraphicsContext gc = c.getGraphicsContext2D();
        root.getChildren().add(c);

        new AnimationTimer() {
            long lastTick = 0;

            public void handle(long now) {
                if (lastTick == 0) {
                    lastTick = now;
                    tick(gc);
                    return;
                }

                if (now - lastTick > 1000000000 / 5) {  //speed=5
                    lastTick = now;
                    tick(gc);
                }
            }

        }.start();


    }
    private void constructSettings(){
        Label lb = new Label("Beállítások");

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e->{mainWindow.setScene(Menu);});

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Kezdő", "Haladó", "Profi");
        choiceBox.setValue("Kezdő");

        ChoiceBox<String> choiceBox2 = new ChoiceBox<>();
        choiceBox2.getItems().addAll("Falak nélkül", "Körbe falak", "Akadályokkal");
        choiceBox2.setValue("Falak nélkül");

        Button btnSave = new Button("Save");
        btnSave.setOnAction(e->getChoice(choiceBox, choiceBox2));

        VBox root = new VBox();
        root.getChildren().addAll(lb, btnBack, choiceBox, choiceBox2, btnSave);
        Settings = new Scene(root, 600,600);
    }
    private void constructToplist(){
        Label lb = new Label("Toplista");

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e->{mainWindow.setScene(Menu);});

        VBox root = new VBox();
        root.getChildren().addAll(lb, btnBack);
        Toplist = new Scene(root, 600,600);
    }
    private void constructMulti1(){
        Label lb = new Label("Multiplayer");
        Button btnHost =new Button("Host Game");
        btnHost.setOnAction(e ->{mainWindow.setScene(MultiWait);});

        Button btnGuest =new Button("Guest Game");
        btnGuest.setOnAction(e ->{mainWindow.setScene(MultiIP);});

        Button btnBack =new Button("Back");
        btnBack.setOnAction(e ->{mainWindow.setScene(Menu);});

        VBox root = new VBox();
        root.getChildren().addAll(lb, btnBack, btnHost, btnGuest);
        Multi1 = new Scene(root, 600, 600);
    }
    private void constructMultiIP(){
        Label lb = new Label("Multiplayer");
        Button btnBack =new Button("Back");
        btnBack.setOnAction(e ->{mainWindow.setScene(Menu);});
        Label lb2 = new Label("Host IP címe:");

        Button btnConnect =new Button("Connect");
        btnConnect.setOnAction(e ->{mainWindow.setScene(MultiWait);});

        TextField textHostIP = new TextField();
        textHostIP.setOnKeyPressed(e ->{if(e.getCode()== KeyCode.ENTER){System.out.println(textHostIP.getText());}});

        VBox root = new VBox();
        root.getChildren().addAll(lb, btnBack, lb2, textHostIP, btnConnect);
        MultiIP = new Scene(root, 600, 600);
    }
    private void constructMultiWait(){
        Label lb = new Label("Multiplayer");
        Button btnBack =new Button("Back");
        btnBack.setOnAction(e ->{mainWindow.setScene(Menu);});
        Label lb2 = new Label("If you ready press the button");

        Button btnReady =new Button("Ready");
        btnReady.setOnAction(e ->{mainWindow.setScene(MultiGame);});

        VBox root = new VBox();
        root.getChildren().addAll(lb, btnBack, lb2, btnReady);
        MultiWait = new Scene(root, 600, 600);
    }
    private void constructMultiGame(){
        Label lb = new Label("Multi Game");
        Label Score = new Label("Score: ");      //játék állása kell


        Button btnBack = new Button("Back");    //játékot el kell menteni
        btnBack.setOnAction(e->{mainWindow.setScene(Menu);});


        VBox root = new VBox();
        root.getChildren().addAll(lb, Score, btnBack);
        MultiGame = new Scene(root, 600,600);
        Canvas c = new Canvas(LABYRINTH_WIDTH*block_size, LABYRINTH_HEIGHT*block_size);
        GraphicsContext gc = c.getGraphicsContext2D();
        root.getChildren().add(c);

        new AnimationTimer() {
            long lastTick = 0;

            public void handle(long now) {
                if (lastTick == 0) {
                    lastTick = now;
                    tick(gc);
                    return;
                }

                if (now - lastTick > 1000000000 / 5) {  //speed=5
                    lastTick = now;
                    tick(gc);
                }
            }

        }.start();

    }

    private void constructNameIn(){
        Label lb = new Label("Top 10 name");
        Button btnSubmit =new Button("Submit");
        btnSubmit.setOnAction(e ->{mainWindow.setScene(Menu);});

        TextField textName = new TextField();
        textName.setOnKeyPressed(e ->{if(e.getCode()== KeyCode.ENTER){System.out.println(textName.getText());}});

        VBox root = new VBox();
        root.getChildren().addAll(lb, textName, btnSubmit);
        NameIn = new Scene(root, 600, 600);
    }

    public static void tick(GraphicsContext gc) {
        //játék logikája
        gc.setFill(Color.RED);
        gc.setFont(new Font("", 50));
        gc.fillText("GAME OVER", 100, 250);
        return;

    }
    private void getChoice(ChoiceBox<String> choiceBox, ChoiceBox<String> choiceBox2){
        String level = choiceBox.getValue();
        String walls = choiceBox2.getValue();
        System.out.println(level);
        System.out.println(walls);
    }

}
