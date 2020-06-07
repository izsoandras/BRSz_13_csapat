package UI;

import UI.toplist.Entry;
import UI.toplist.TopList;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextInputControlSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Game;
import model.GameMemento;
import model.map.Labyrinth;
import model.map.things.Wall;
import model.network.Game_status;
import model.network.Server_TEST;
import model.network.network_Client;
import model.network.network_Server;
import model.util.Directions;
import model.util.LabyrinthType;

import javax.swing.*;
import java.io.*;

public class GameTimer {
    private static Game game;
    private static Labyrinth labyrinthAnother;
    private static Game_status Game_statusAnother, Game_statusLocal;
    private static int blocksize=10;
    private static boolean pause=false;
    private static AnimationTimer gameTimer;
    private Scene NameIn;
    private Stage gameStage = new Stage();

    private void constructNameIn(){
        Label lb = new Label("Your nick name:");

        TextField textName = new TextField();
        textName.setMaxWidth(100);
        textName.setStyle("-fx-background-color: SKYBLUE");

        Button btnSubmit =new Button("Submit");
        btnSubmit.setStyle("-fx-background-color: SKYBLUE");
        btnSubmit.setOnAction(e ->{
            TopList topList = new TopList();
            topList.insert(new Entry(textName.getText() ,game.getLabyrinth().getSnake().getPoints()));
            gameStage.close();
        });

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        root.getChildren().addAll(lb, textName, btnSubmit);
        NameIn = new Scene(root, 600, 600);
        BackgroundFill background_fill = new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        root.setBackground(background);
    }

    public  void MultiGame(LabyrinthType lab, int speed, network_Server Test_Server, network_Client Test_Client, boolean isServer){
        Game_statusLocal= new Game_status();
        Labyrinth labyrinth = new Labyrinth(lab);
        game = new Game(labyrinth, speed);
        labyrinthAnother = new Labyrinth(lab);

        HBox root = new HBox();
        Canvas c1 = new Canvas(90 * blocksize, 60 * blocksize);
        Canvas c2 = new Canvas(90 * blocksize, 60 * blocksize);
        GraphicsContext gc1 = c1.getGraphicsContext2D();
        GraphicsContext gc2 = c2.getGraphicsContext2D();
        root.getChildren().addAll(c1, c2);

        gameTimer=new AnimationTimer() {
            long lastTick = 0;


            public void handle(long now) {
                if (lastTick == 0) {
                    lastTick = now;
                    tickMulti(gc1, gc2, Test_Server,Test_Client,isServer);
                    return;
                }

                if (now - lastTick > 1000000000 / game.getSpeed()) {
                    lastTick = now;
                    tickMulti(gc1,gc2,Test_Server, Test_Client, isServer);

                }
            }

        };
        if(game.isSnakeAlive()){
            gameTimer.start();
        }

        Stage gameStage = new Stage();
        Scene scene = new Scene(root, 1800, 600);
        gameStage.setScene(scene);
        gameStage.setTitle("Multi Game");
        gameStage.show();

        //controll
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.UP) {
                game.getLabyrinth().getSnake().getHead().setDirection(Directions.UP);
            }
            if (key.getCode() == KeyCode.LEFT) {
                game.getLabyrinth().getSnake().getHead().setDirection(Directions.LEFT);
            }
            if (key.getCode() == KeyCode.DOWN) {
                game.getLabyrinth().getSnake().getHead().setDirection(Directions.DOWN);
            }
            if (key.getCode() == KeyCode.RIGHT) {
                game.getLabyrinth().getSnake().getHead().setDirection(Directions.RIGHT);
            }

        });
        //pause
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.P) {
                if(!pause){
                    pause=true;
                    Game_statusLocal.Paused=true;
                }else {
                    pause = false;
                    Game_statusLocal.Paused=false;
                }
            }
        });

        //exit
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.ESCAPE) {
                Game_statusLocal.Exited = true;
                if(isServer) {
                    Test_Server.UpdateLocallabyrinth(labyrinth, Game_statusLocal);
                }else{
                    Test_Client.UpdateLocallabyrinth(labyrinth,Game_statusLocal);
                }
                gameStage.close();
                gameTimer.stop();
            }
        });
    }


    public void SingleGame(LabyrinthType lab, int speed) {
        Labyrinth labyrinth = new Labyrinth(lab);   //labirintus létrehozása beállítások alapján
        game = new Game(labyrinth, speed);     //Játék indítátasa beállítások alapján
        Game(game);
        constructNameIn();
    }


    public void LoadGame() {
        //játék betöltése
        GameMemento gm=null;
        try {
            FileInputStream fileIn = new FileInputStream("game.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gm = (GameMemento) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Game Loaded");
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Game class not found");
            c.printStackTrace();
            return;
        }
        game = new Game(gm);
        Game(game);
        constructNameIn();
    }



    private void Game(Game game){
        VBox root = new VBox();
        Canvas c = new Canvas(90 * blocksize, 60 * blocksize);
        GraphicsContext gc = c.getGraphicsContext2D();
        root.getChildren().add(c);

        gameTimer=new AnimationTimer() {
            long lastTick = 0;


            public void handle(long now) {
                if (lastTick == 0) {
                    lastTick = now;
                    tick(gc);
                    return;
                }

                if (now - lastTick > 1000000000 / game.getSpeed()) {
                    lastTick = now;
                    tick(gc);

                }
            }

        };
        if(game.isSnakeAlive()){
            gameTimer.start();
        }


        Scene scene = new Scene(root, 900, 600);
        gameStage.setScene(scene);
        gameStage.setTitle("Single Game");
        gameStage.show();

        //controll
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.UP) {
                game.getLabyrinth().getSnake().getHead().setDirection(Directions.UP);
            }
            if (key.getCode() == KeyCode.LEFT) {
                game.getLabyrinth().getSnake().getHead().setDirection(Directions.LEFT);
            }
            if (key.getCode() == KeyCode.DOWN) {
                game.getLabyrinth().getSnake().getHead().setDirection(Directions.DOWN);
            }
            if (key.getCode() == KeyCode.RIGHT) {
                game.getLabyrinth().getSnake().getHead().setDirection(Directions.RIGHT);
            }

        });
        //pause
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.P) {
                if(!pause){
                    pause=true;
                }else {
                    pause = false;
                }
            }
        });

        //exit
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.ESCAPE) {
                if(game.isSnakeAlive()){
                    //mentés és kilépés

                    try {
                        GameMemento m= new GameMemento(game);
                        FileOutputStream fileOut = new FileOutputStream("game.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(m);
                        out.close();
                        fileOut.close();
                        System.out.println("Game Saved");
                    }catch (IOException i){
                        i.printStackTrace();
                    }
                    gameTimer.stop();
                    gameStage.close();
                }else {
                    //név mentése és kilépés
                    gameStage.setScene(NameIn);
                    gameTimer.stop();
                    if(game.getLabyrinth().getSnake().getPoints()==0)
                        gameStage.close();
                    return;
                }
            }
        });

    }


    public static void tickMulti(GraphicsContext gc1, GraphicsContext gc2, network_Server Test_Server, network_Client Test_Client, boolean isServer){
        //Network
        if(isServer) {
            labyrinthAnother = Test_Server.Get_Opponent_labyrinth();
            Game_statusAnother=Test_Server.Get_Opponent_Status();
            Test_Server.UpdateLocallabyrinth(game.getLabyrinth(),Game_statusLocal);
        }else{
            labyrinthAnother = Test_Client.Get_Opponent_labyrinth();
            Game_statusAnother=Test_Client.Get_Opponent_Status();
            Test_Client.UpdateLocallabyrinth(game.getLabyrinth(),Game_statusLocal);
        }
        //tábla törlés
        gc1.setFill(Color.WHITE);
        gc1.fillRect(0, 0, 900 , 600);
        gc2.setFill(Color.WHITE);
        gc2.fillRect(0, 0, 900 , 600);

        if (!game.isSnakeAlive()) {
            gc1.setFill(Color.RED);
            gc1.setFont(new Font("", 50));
            gc1.fillText("GAME OVER", 300, 300);
            gc1.fillText("Score: "+ game.getLabyrinth().getSnake().getPoints(), 350, 370);

        }
        if(!pause && game.isSnakeAlive()) {
            game.step();
        }

        //megjelenítés
        //kaja kirajzolása
        gc1.setFill(Color.RED);
        gc1.fillOval(game.getLabyrinth().getFood().getField().getKoord().getX()* blocksize, game.getLabyrinth().getFood().getField().getKoord().getY() * blocksize, blocksize, blocksize);
        gc2.setFill(Color.RED);
        gc2.fillOval(labyrinthAnother.getFood().getField().getKoord().getX()* blocksize, labyrinthAnother.getFood().getField().getKoord().getY() * blocksize, blocksize, blocksize);

        //veszély rajzolása
        if(game.getLabyrinth().getDanger()!=null) {
            gc1.setFill(Color.YELLOW);
            gc1.fillRect(game.getLabyrinth().getDanger().getField().getKoord().getX() * blocksize, game.getLabyrinth().getDanger().getField().getKoord().getY() * blocksize, 10, 10);
        }
        if(labyrinthAnother.getDanger()!=null) {
            gc2.setFill(Color.YELLOW);
            gc2.fillRect(labyrinthAnother.getDanger().getField().getKoord().getX() * blocksize, labyrinthAnother.getDanger().getField().getKoord().getY() * blocksize, 10, 10);
        }
        //bónusz rajzolása
        if(game.getLabyrinth().getBonus()!=null) {
            gc1.setFill(Color.GREEN);
            gc1.fillOval(game.getLabyrinth().getBonus().getField().getKoord().getX() * blocksize, game.getLabyrinth().getBonus().getField().getKoord().getY() * blocksize, 10, 10);
        }
        if(labyrinthAnother.getBonus()!=null) {
            gc2.setFill(Color.GREEN);
            gc2.fillOval(labyrinthAnother.getBonus().getField().getKoord().getX() * blocksize, labyrinthAnother.getBonus().getField().getKoord().getY() * blocksize, 10, 10);
        }
        //fal kirajzolása
        if(game.getLabyrinth().getWalls()!=null){
            gc1.setFill(Color.BLACK);
            for(int i=0;i<game.getLabyrinth().getWalls().size();i++){
                gc1.fillRect(game.getLabyrinth().getWalls().get(i).getField().getKoord().getX()*blocksize, game.getLabyrinth().getWalls().get(i).getField().getKoord().getY()*blocksize, 10, 10);
            }
        }
        if(labyrinthAnother.getWalls()!=null){
            gc2.setFill(Color.BLACK);
            for(int i=0;i<labyrinthAnother.getWalls().size();i++){
                gc2.fillRect(labyrinthAnother.getWalls().get(i).getField().getKoord().getX()*blocksize, labyrinthAnother.getWalls().get(i).getField().getKoord().getY()*blocksize, 10, 10);
            }
        }
        //score
        if(game.getLabyrinth().getType()==LabyrinthType.WALLED) {
            gc1.setFill(Color.WHITE);
        }else{
            gc1.setFill(Color.BLACK);
        }
        gc1.fillText("Score: "+ game.getLabyrinth().getSnake().getPoints(), 10 ,10);
        if(labyrinthAnother.getType()==LabyrinthType.WALLED) {
            gc2.setFill(Color.WHITE);
        }else{
            gc2.setFill(Color.BLACK);
        }
        gc2.fillText("Score: "+ labyrinthAnother.getSnake().getPoints(), 10 ,10);

        //kígyó kirajzolása
        gc1.setFill(Color.GREEN);
        //feje
        gc1.fillRect(game.getLabyrinth().getSnake().getHead().getField().getKoord().getX()* blocksize,game.getLabyrinth().getSnake().getHead().getField().getKoord().getY()* blocksize, 10, 10);
        //teste
        for (int i = 0; i < game.getLabyrinth().getSnake().getBody().size(); i++) {
            gc1.fillRect(game.getLabyrinth().getSnake().getBody().get(i).getField().getKoord().getX()*blocksize, game.getLabyrinth().getSnake().getBody().get(i).getField().getKoord().getY() * blocksize, 10, 10);
        }
        gc2.setFill(Color.GREEN);
        //feje
        gc2.fillRect(labyrinthAnother.getSnake().getHead().getField().getKoord().getX()* blocksize,labyrinthAnother.getSnake().getHead().getField().getKoord().getY()* blocksize, 10, 10);
        //teste
        for (int i = 0; i < labyrinthAnother.getSnake().getBody().size(); i++) {
            gc2.fillRect(labyrinthAnother.getSnake().getBody().get(i).getField().getKoord().getX()*blocksize, labyrinthAnother.getSnake().getBody().get(i).getField().getKoord().getY() * blocksize, 10, 10);
        }
    }

    public static void tick(GraphicsContext gc) {

        if (!game.isSnakeAlive()) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 300, 300);
            gc.fillText("Score: "+ game.getLabyrinth().getSnake().getPoints(), 350, 370);
            return;
        }
        if(!pause) {
            game.step();
        }

        //tábla törlés
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 900 , 600);

        //food rajzolás
        gc.setFill(Color.RED);
        gc.fillOval(game.getLabyrinth().getFood().getField().getKoord().getX()* blocksize, game.getLabyrinth().getFood().getField().getKoord().getY() * blocksize, blocksize, blocksize);

        //veszély rajzolása
        if(game.getLabyrinth().getDanger()!=null) {
            gc.setFill(Color.YELLOW);
            gc.fillRect(game.getLabyrinth().getDanger().getField().getKoord().getX() * blocksize, game.getLabyrinth().getDanger().getField().getKoord().getY() * blocksize, 10, 10);
        }

        //bónusz rajzolása
        if(game.getLabyrinth().getBonus()!=null) {
            gc.setFill(Color.GREEN);
            gc.fillOval(game.getLabyrinth().getBonus().getField().getKoord().getX() * blocksize, game.getLabyrinth().getBonus().getField().getKoord().getY() * blocksize, 10, 10);
        }

        //fal kirajzolása
        if(game.getLabyrinth().getWalls()!=null){
            gc.setFill(Color.BLACK);
            for(int i=0;i<game.getLabyrinth().getWalls().size();i++){
                gc.fillRect(game.getLabyrinth().getWalls().get(i).getField().getKoord().getX()*blocksize, game.getLabyrinth().getWalls().get(i).getField().getKoord().getY()*blocksize, 10, 10);
            }
        }

        //score
        if(game.getLabyrinth().getType()==LabyrinthType.WALLED) {
            gc.setFill(Color.WHITE);
        }else{
            gc.setFill(Color.BLACK);
        }
        gc.fillText("Score: "+ game.getLabyrinth().getSnake().getPoints(), 10 ,10);

        //kígyó kirajzolása
        gc.setFill(Color.GREEN);
        //feje
        gc.fillRect(game.getLabyrinth().getSnake().getHead().getField().getKoord().getX()* blocksize,game.getLabyrinth().getSnake().getHead().getField().getKoord().getY()* blocksize, 10, 10);
        //teste
        for (int i = 0; i < game.getLabyrinth().getSnake().getBody().size(); i++) {
            gc.fillRect(game.getLabyrinth().getSnake().getBody().get(i).getField().getKoord().getX()*blocksize, game.getLabyrinth().getSnake().getBody().get(i).getField().getKoord().getY() * blocksize, 10, 10);
        }
    }
}

