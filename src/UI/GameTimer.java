package UI;

import UI.toplist.Entry;
import UI.toplist.TopList;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
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
    private static int blocksize=10;
    private static boolean pause=false;
    private static AnimationTimer gameTimer;
    private Scene NameIn;

    private void constructNameIn(){
        Label lb = new Label("Your nick name:");

        TextField textName = new TextField();

        Button btnSubmit =new Button("Submit");
        btnSubmit.setOnAction(e ->{
            //TODO: mentés és kilépés

            TopList topList = new TopList();
            topList.insert(new Entry(textName.getText() ,game.getLabyrinth().getSnake().getPoints()));

        });



        VBox root = new VBox();
        root.getChildren().addAll(lb, textName, btnSubmit);
        NameIn = new Scene(root, 600, 600);
        BackgroundFill background_fill = new BackgroundFill(Color.TAN, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(background_fill);
        root.setBackground(background);
    }

    public  void MultiGame(LabyrinthType lab, int speed, network_Server Test_Server, network_Client Test_Client, boolean isServer){
        System.out.println("Test : 1");
        Labyrinth labyrinth = new Labyrinth(lab);
        System.out.println("Test : 2");
        game = new Game(labyrinth, speed);
        System.out.println("Test : 3");
        labyrinthAnother = new Labyrinth(lab);
        System.out.println("Test : 4");

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
                }else {
                    pause = false;
                }
            }
        });

        //exit
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.ESCAPE) {
                Game_status temp = new Game_status();
                temp.Exited = true;
                if(isServer) {
                    Test_Server.UpdateLocallabyrinth(labyrinth, temp);
                }else{
                    Test_Client.UpdateLocallabyrinth(labyrinth,temp);
                }
                gameStage.close();
                gameTimer.stop();
            }
        });
    }


    public void SingleGame(LabyrinthType lab, int speed) {
        Labyrinth labyrinth = new Labyrinth(lab);   //labirintus létrehozása beállítások alapján
        game = new Game(labyrinth, speed);     //Játék indítátasa beállítások alapján
        constructNameIn();
        Game(game);

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
        constructNameIn();
        Game(game);
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

        Stage gameStage = new Stage();
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
                    gameStage.close();
                    gameTimer.stop();
                }else {
                    //kilépés
                    //gameStage.setScene(Menu);
                    //TODO: nevet bekerni
                    TopList topList = new TopList();
                    topList.insert(new Entry("teszt",game.getLabyrinth().getSnake().getPoints()));

                    gameStage.close();
                    gameTimer.stop();

                    return;
                }
            }
        });

    }


    public static void tickMulti(GraphicsContext gc1, GraphicsContext gc2, network_Server Test_Server, network_Client Test_Client, boolean isServer){
        if (!game.isSnakeAlive()) {
            gc1.setFill(Color.RED);
            gc1.setFont(new Font("", 50));
            gc1.fillText("GAME OVER", 300, 300);
            gc1.fillText("Score: "+ game.getLabyrinth().getSnake().getPoints(), 350, 370);
            return;
        }
        if(!pause) {
            game.step();
        }
        //Network
        if(isServer) {
            labyrinthAnother = Test_Server.Get_Opponent_labyrinth();
        }else{
            labyrinthAnother = Test_Client.Get_Opponent_labyrinth();
        }

        //megjelenítés
        //tábla törlés
        gc1.setFill(Color.WHITE);
        gc1.fillRect(0, 0, 900 , 600);
        gc2.setFill(Color.WHITE);
        gc2.fillRect(0, 0, 900 , 600);

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
            gc2.fillRect(game.getLabyrinth().getDanger().getField().getKoord().getX() * blocksize, labyrinthAnother.getDanger().getField().getKoord().getY() * blocksize, 10, 10);
        }
        //bónusz rajzolása
        if(game.getLabyrinth().getBonus()!=null) {
            gc1.setFill(Color.GREEN);
            gc1.fillOval(game.getLabyrinth().getBonus().getField().getKoord().getX() * blocksize, game.getLabyrinth().getBonus().getField().getKoord().getY() * blocksize, 10, 10);
        }
        if(game.getLabyrinth().getBonus()!=null) {
            gc2.setFill(Color.GREEN);
            gc2.fillOval(labyrinthAnother.getBonus().getField().getKoord().getX() * blocksize, labyrinthAnother.getBonus().getField().getKoord().getY() * blocksize, 10, 10);
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

