package UI;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static model.GameParams.LABYRINTH_HEIGHT;
import static model.GameParams.LABYRINTH_WIDTH;

public class main_ui extends Application {
    private Stage mainWindow;
    private Scene Menu, Settings, Toplist, Game;

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
        btnMulti.setOnAction(e ->{mainWindow.setScene(Game);});     //multiplayer kezelés

        Button btnToplist =new Button("Toplista");
        btnToplist.setOnAction(e ->{mainWindow.setScene(Toplist);});

        VBox root = new VBox();
        root.getChildren().addAll(lb, btnGame, btnLoadGame, btnSettings, btnMulti, btnToplist);
        Menu=new Scene(root, 600, 600);
    }
    private void constructGame(){
        Label lb = new Label("Játék");
        Label Score = new Label("Score: ");      //játék állása kell

        Button btnBack = new Button("Back");
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

        VBox root = new VBox();
        root.getChildren().addAll(lb, btnBack);
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


    public static void tick(GraphicsContext gc) {
    //játék logikája
        gc.setFill(Color.RED);
        gc.setFont(new Font("", 50));
        gc.fillText("GAME OVER", 100, 250);
        return;
    }
}
