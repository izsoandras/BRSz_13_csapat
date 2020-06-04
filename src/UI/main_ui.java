package UI;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Game;
import model.GameMemento;
import model.map.Labyrinth;
import model.util.LabyrinthType;
import UI.GameTimer;

import static model.GameParams.LABYRINTH_HEIGHT;
import static model.GameParams.LABYRINTH_WIDTH;

public class main_ui extends Application {
    private Stage mainWindow;
    private Scene Menu, Settings, Toplist, Game, Multi1, MultiIP, MultiWait, MultiGame, NameIn;
    private int speed=5;
    private LabyrinthType lab = LabyrinthType.WALLESS;

    static int block_size = 10; //10pixel egy blokk mérete
    int width=LABYRINTH_WIDTH;       //blokkok száma
    int height=LABYRINTH_HEIGHT;
    GameTimer gt= new GameTimer();

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
        Button btnSingle = new Button("Single Player");
        btnSingle.setOnAction(e->{
            mainWindow.setScene(Game);
            gt.SingleGame(lab, speed, mainWindow);
        });
        btnSingle.setStyle("-fx-background-color: SKYBLUE");

        Button btnLoad = new Button("Load Game");
        btnLoad.setOnAction(e->{mainWindow.setScene(Game);});
        btnLoad.setStyle("-fx-background-color: SKYBLUE");

        Button btnSettings = new Button("Settings");
        btnSettings.setOnAction(e->{mainWindow.setScene(Settings);});
        btnSettings.setStyle("-fx-background-color: SKYBLUE");

        Button btnMulti = new Button("Multi Player");
        btnMulti.setOnAction(e->{mainWindow.setScene(Multi1);});
        btnMulti.setStyle("-fx-background-color: SKYBLUE");

        Button btnToplist = new Button("Toplist");
        btnToplist.setOnAction(e->{mainWindow.setScene(Toplist);});
        btnToplist.setStyle("-fx-background-color: SKYBLUE");

        VBox root = new VBox();
        root.setSpacing(50);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(btnSingle, btnLoad, btnSettings, btnMulti, btnToplist);
        Menu = new Scene(root, 600,600);
        BackgroundFill background_fill = new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        root.setBackground(background);
    }

    private void constructGame(){

        Button btnBack = new Button("Back");    //játékot el kell menteni
        btnBack.setStyle("-fx-background-color: SALMON");
        btnBack.setOnAction(e->{
            /*GameMemento m = game.getMemento();
            m.serialize();*/
            mainWindow.setScene(Menu);
        });
        System.out.println(LABYRINTH_WIDTH);
        System.out.println(LABYRINTH_HEIGHT);
        Canvas c = new Canvas(LABYRINTH_WIDTH*block_size, LABYRINTH_HEIGHT*block_size);
        GraphicsContext gc = c.getGraphicsContext2D();

        BorderPane root = new BorderPane();
        root.setTop(btnBack);
        root.setBottom(c);
        Game = new Scene(root, 900,650);
        BackgroundFill background_fill = new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        root.setBackground(background);
    }
    private void constructSettings(){
        Label lb = new Label("Beállítások");

        Button btnBack = new Button("Back");
        btnBack.setOnAction(e->{mainWindow.setScene(Menu);});
        btnBack.setStyle("-fx-background-color: SALMON");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Kezdő", "Haladó", "Profi");
        choiceBox.setValue("Kezdő");
        choiceBox.setStyle("-fx-background-color: SKYBLUE");

        ChoiceBox<String> choiceBox2 = new ChoiceBox<>();
        choiceBox2.getItems().addAll("Falak nélkül", "Körbe falak", "Akadályokkal");
        choiceBox2.setValue("Falak nélkül");
        choiceBox2.setStyle("-fx-background-color: SKYBLUE");

        Button btnSave = new Button("Save");
        btnSave.setOnAction(e->getChoice(choiceBox, choiceBox2));
        btnSave.setStyle("-fx-background-color: SKYBLUE");

        VBox vb = new VBox();
        vb.getChildren().addAll(choiceBox, choiceBox2, btnSave);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(50);

        BorderPane root = new BorderPane();
        root.setTop(btnBack);
        root.setCenter(vb);
        Settings = new Scene(root, 600,600);
        BackgroundFill background_fill = new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        root.setBackground(background);
    }
    private void constructToplist(){
        Label lb = new Label("Toplista");

        Button btnBack = new Button("Back");
        btnBack.setStyle("-fx-background-color: SALMON");
        btnBack.setOnAction(e->{mainWindow.setScene(Menu);});

        BorderPane root = new BorderPane();
        root.setTop(btnBack);
        root.setCenter(lb);
        Toplist = new Scene(root, 600,600);
        BackgroundFill background_fill = new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        root.setBackground(background);
    }
    private void constructMulti1(){
        Label lb = new Label("Multiplayer");
        Button btnHost =new Button("Host Game");
        btnHost.setOnAction(e ->{mainWindow.setScene(MultiWait);});
        btnHost.setStyle("-fx-background-color: SKYBLUE");

        Button btnGuest =new Button("Guest Game");
        btnGuest.setOnAction(e ->{mainWindow.setScene(MultiIP);});
        btnGuest.setStyle("-fx-background-color: SKYBLUE");

        Button btnBack =new Button("Back");
        btnBack.setOnAction(e ->{mainWindow.setScene(Menu);});
        btnBack.setStyle("-fx-background-color: SALMON");

        VBox vb = new VBox();
        vb.getChildren().addAll(btnHost, btnGuest);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(50);
        BorderPane root = new BorderPane();
        root.setTop(btnBack);
        root.setCenter(vb);
        Multi1 = new Scene(root, 600, 600);
        BackgroundFill background_fill = new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        root.setBackground(background);
    }
    private void constructMultiIP(){
        Label lb = new Label("Multiplayer");
        Button btnBack =new Button("Back");
        btnBack.setOnAction(e ->{mainWindow.setScene(Menu);});
        btnBack.setStyle("-fx-background-color: SALMON");

        Label lb2 = new Label("Host IP címe:");

        Button btnConnect =new Button("Connect");
        btnConnect.setStyle("-fx-background-color: SKYBLUE");
        btnConnect.setOnAction(e ->{mainWindow.setScene(MultiWait);});

        TextField textHostIP = new TextField();
        textHostIP.setOnKeyPressed(e ->{if(e.getCode()== KeyCode.ENTER){System.out.println(textHostIP.getText());}});
        textHostIP.setMaxWidth(120);
        textHostIP.setStyle("-fx-background-color: SKYBLUE");

        VBox vb = new VBox();
        vb.getChildren().addAll(lb2, textHostIP,btnConnect);
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);
        BorderPane root = new BorderPane();
        root.setTop(btnBack);
        root.setCenter(vb);


        MultiIP = new Scene(root, 600, 600);
        BackgroundFill background_fill = new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        root.setBackground(background);
    }
    private void constructMultiWait(){
        Label lb = new Label("Multiplayer");
        Button btnBack =new Button("Back");
        btnBack.setStyle("-fx-background-color: SALMON");
        btnBack.setOnAction(e ->{mainWindow.setScene(Menu);});
        Label lb2 = new Label("If you ready press the button");

        Button btnReady =new Button("Ready");
        btnReady.setStyle("-fx-background-color: SKYBLUE");
        btnReady.setOnAction(e ->{mainWindow.setScene(MultiGame);});

        VBox vb = new VBox();
        vb.getChildren().addAll(lb2, btnReady);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);
        BorderPane root = new BorderPane();
        root.setTop(btnBack);
        root.setCenter(vb);

        MultiWait = new Scene(root, 600, 600);
        BackgroundFill background_fill = new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        root.setBackground(background);
    }
    private void constructMultiGame(){      //újra kell írni
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
/*  Ezt még ki kell találni
        new AnimationTimer() {
            long lastTick = 0;

            public void handle(long now) {
                if (lastTick == 0) {
                    lastTick = now;
                    tick(gc);
                    return;
                }

                if (now - lastTick > 1000000000 / 5 ) {  //speed=5  game.getSpeed()
                    lastTick = now;
                    tick(gc);
                }
            }

        }.start();
*/
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
        BackgroundFill background_fill = new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        root.setBackground(background);
    }

    private void getChoice(ChoiceBox<String> choiceBox, ChoiceBox<String> choiceBox2){

        switch (choiceBox.getValue()) {
            case "Kezdő":
                speed = 5;
                break;
            case "Haladó":
                speed = 8;
                break;
            case "Profi":
                speed = 11;
                break;
        }
        switch (choiceBox2.getValue()) {
            case "Falak nélkül":
                lab = LabyrinthType.WALLESS;
                break;
            case "Körbe falak":
                lab = LabyrinthType.WALLED;
                break;
            case "Akadályokkal":
                lab = LabyrinthType.EXTRA;
                break;
        }
        System.out.println(lab);
        System.out.println(speed);
    }
}
