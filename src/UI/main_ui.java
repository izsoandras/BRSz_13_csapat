package UI;

import UI.toplist.Entry;
import UI.toplist.TopList;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.network.Game_status;
import model.network.Server_TEST;
import model.network.network_Client;
import model.network.network_Server;
import model.util.LabyrinthType;


import javax.imageio.IIOException;
import java.io.*;
import java.util.List;

import static model.GameParams.LABYRINTH_HEIGHT;
import static model.GameParams.LABYRINTH_WIDTH;

public class main_ui extends Application {
    private Stage mainWindow;
    private Scene Menu, Settings, Toplist, Game, Multi1, MultiIP, MultiWaitServer, MultiWaitClient, MultiGame, NameIn,MultiWaitGuest;
    private int speed=5;
    private LabyrinthType lab = LabyrinthType.WALLESS;
    private String IP="192.168.0.27";

    static int block_size = 10; //10pixel egy blokk mérete
    GameTimer gt= new GameTimer();
    Game_status server_status= new Game_status();
    network_Server Test_Server= new network_Server();
    Thread threadNetwork;
    Game_status client_status = new Game_status();
    network_Client Test_Client = new network_Client(IP);
    Thread threadClient;

    @Override
    public void start(Stage ps) {
        mainWindow = ps;

        constructMenu();
        constructSettings();
        constructToplist();
        constructMulti1();
        constructMultiWaitGuest();
        constructMultiIP();
        constructMultiWaitServer();
        constructMultiWaitClient();
        constructMultiGame();
        constructNameIn();

        mainWindow.setScene(Menu);
        mainWindow.setTitle("Snake Game");
        mainWindow.show();
    }


    private void constructMenu(){
        Button btnSingle = new Button("Single Player");
        btnSingle.setOnAction(e->{
            mainWindow.setScene(Menu);
            gt.SingleGame(lab,speed);
        });
        btnSingle.setStyle("-fx-background-color: SKYBLUE");

        Button btnLoad = new Button("Load Game");
        btnLoad.setOnAction(e->{
            mainWindow.setScene(Menu);
            gt.LoadGame();
        });
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

        TopList topList = new TopList();
        //TODO: kepernyore kiirni
        for(Entry e : topList.getEntries()){
            System.out.println(e.getName()+": "+e.getPoints());
        }
    }
    private void constructMulti1(){
        Label lb = new Label("Multiplayer");
        Button btnHost =new Button("Host Game");
        btnHost.setOnAction(e ->{
            //szerver indítása
            threadNetwork = new Thread(Test_Server);
            threadNetwork.start();

            while(!Test_Server.isRunning()){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            mainWindow.setScene(MultiWaitGuest);
        });
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

    private void constructMultiWaitGuest(){
        Label lb = new Label("Waiting for the Guest");

        Button btnConnect =new Button("Start Connection");
        btnConnect.setOnAction(e ->{

            while(!Test_Server.isConnected()){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException exx) {
                    exx.printStackTrace();
                }
            }
            Test_Server.UpdateServerParameters(lab, speed);     //labirint thype és int
            mainWindow.setScene(MultiWaitServer);
        });
        btnConnect.setStyle("-fx-background-color: SKYBLUE");


        VBox vb = new VBox();
        vb.getChildren().addAll(lb, btnConnect);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(50);
        BorderPane root = new BorderPane();
        root.setCenter(vb);
        MultiWaitGuest = new Scene(root, 600,600);
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

        TextField textHostIP = new TextField();
        textHostIP.setMaxWidth(120);
        textHostIP.setStyle("-fx-background-color: SKYBLUE");

        Button btnConnect =new Button("Connect");
        btnConnect.setStyle("-fx-background-color: SKYBLUE");
        btnConnect.setOnAction(e ->{
            IP=textHostIP.getText();
            //Client start
            threadClient = new Thread(Test_Client);
            Test_Client.set_clientIP(IP);
            System.out.println("Connection IP: "+ IP);
            threadClient.start();

            while(!Test_Client.isConnected() && !Test_Client.isServerInvalid() ){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException exxx) {
                    exxx.printStackTrace();
                }
            }

            if(Test_Client.isConnected()) {
                mainWindow.setScene(MultiWaitClient);
            }else{
                //threadClient stop
                System.out.println("server invalid: " + Test_Client.isServerInvalid());
                try {
                    threadClient.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

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
    private void constructMultiWaitServer(){
        Label lb = new Label("Multiplayer");
        Button btnBack =new Button("Back");
        btnBack.setStyle("-fx-background-color: SALMON");
        btnBack.setOnAction(e ->{mainWindow.setScene(Menu);});
        Label lb2 = new Label("If you ready press the button");

        Button btnReady =new Button("Ready");
        btnReady.setStyle("-fx-background-color: SKYBLUE");
        btnReady.setOnAction(e ->{
            Test_Server.setLocalReady();

            while(!Test_Server.getOpponentReady()){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException eex) {
                    eex.printStackTrace();
                }
            }

            mainWindow.setScene(Menu);
            gt.MultiGame(lab, speed, Test_Server, Test_Client, true);
        });

        VBox vb = new VBox();
        vb.getChildren().addAll(lb2, btnReady);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);
        BorderPane root = new BorderPane();
        root.setTop(btnBack);
        root.setCenter(vb);

        MultiWaitServer = new Scene(root, 600, 600);
        BackgroundFill background_fill = new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        root.setBackground(background);
    }

    private void constructMultiWaitClient(){
        Label lb = new Label("Multiplayer");
        Button btnBack =new Button("Back");
        btnBack.setStyle("-fx-background-color: SALMON");
        btnBack.setOnAction(e ->{mainWindow.setScene(Menu);});
        Label lb2 = new Label("If you ready press the button");

        Button btnReady =new Button("Ready");
        btnReady.setStyle("-fx-background-color: SKYBLUE");
        btnReady.setOnAction(e ->{
            Test_Client.setLocalReady();

            while(!Test_Client.getOpponentReady()){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException eex) {
                    eex.printStackTrace();
                }
            }

            mainWindow.setScene(Menu);
            gt.MultiGame(Test_Client.getServerLabType(), Test_Client.getGameSpeed(), Test_Server, Test_Client, false);
        });

        VBox vb = new VBox();
        vb.getChildren().addAll(lb2, btnReady);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);
        BorderPane root = new BorderPane();
        root.setTop(btnBack);
        root.setCenter(vb);

        MultiWaitClient = new Scene(root, 600, 600);
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
