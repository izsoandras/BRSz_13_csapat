package UI;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.skin.TextInputControlSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Game;
import model.GameMemento;
import model.map.Labyrinth;
import model.map.things.Wall;
import model.util.Directions;
import model.util.LabyrinthType;

import javax.swing.*;
import java.io.*;

public class GameTimer {
    private static Game game;
    private static int blocksize=10;
    private static boolean pause=false;
    private static AnimationTimer gameTimer;



    public void SingleGame(LabyrinthType lab, int speed) {
        Labyrinth labyrinth = new Labyrinth(lab);   //labirintus létrehozása beállítások alapján
        game = new Game(labyrinth, speed);     //Játék indítátasa beállítások alapján


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
                    System.out.println("escape alive");
                    gameStage.close();
                    gameTimer.stop();
                }else {
                    //kilépés
                    System.out.println("escape dead");
                    gameStage.close();
                    gameTimer.stop();
                    return;
                }
            }
        });
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
            System.out.println("Játék betöltve");
        } catch (IOException i) {
            System.out.println("hibára fut");
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Game class not found");
            c.printStackTrace();
            return;
        }
        game = new Game(gm);

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
            if (key.getCode() == KeyCode.P && !pause) {
                pause=true;
            }else{
                pause=false;
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
                    System.out.println("escape alive");
                    gameStage.close();
                    gameTimer.stop();
                }else {
                    //kilépés
                    System.out.println("escape dead");
                    gameStage.close();
                    gameTimer.stop();
                    return;
                }
            }
        });
    }

    public static void tick(GraphicsContext gc) {

        if (!game.isSnakeAlive()) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 300, 300);
            gc.fillText("Score: "+ game.getLabyrinth().getSnake().getPoints(), 350, 370);
            System.out.println("game over");
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
