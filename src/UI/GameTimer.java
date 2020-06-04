package UI;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.skin.TextInputControlSkin;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Game;
import model.map.Labyrinth;
import model.util.Directions;
import model.util.LabyrinthType;

public class GameTimer {
    private static Game game;
    private static int blocksize=10;



    public void SingleGame(LabyrinthType lab, int speed, Stage primaryStage){
        Labyrinth labyrinth = new Labyrinth(lab);   //labirintus létrehozása beállítások alapján
        game = new Game(labyrinth, speed);     //Játék indítátasa beállítások alapján


        VBox root = new VBox();
        Canvas c = new Canvas(90*blocksize, 60*blocksize);
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

                if (now - lastTick > 1000000000 / game.getSpeed()) {
                    lastTick = now;
                    tick(gc);

                }
            }

        }.start();
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

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




    }
    public static void tick(GraphicsContext gc) {
        //játék logikája
        if (!game.isSnakeAlive()) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 100, 250);
            System.out.println("game over");
            return;
        }

        game.step();

        //tábla törlés
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 900 , 600);

        //food rajzolás
        gc.setFill(Color.RED);
        gc.fillOval(game.getLabyrinth().getFood().getField().getKoord().getX()* blocksize, game.getLabyrinth().getFood().getField().getKoord().getY() * blocksize, blocksize, blocksize);

        //veszély rajzolása
        gc.setFill(Color.YELLOW);
        gc.fillRect(game.getLabyrinth().getDanger().getField().getKoord().getX()*blocksize, game.getLabyrinth().getDanger().getField().getKoord().getY()*blocksize,10,10);

        //bónusz rajzolása
        gc.setFill(Color.GREEN);
        gc.fillOval(game.getLabyrinth().getBonus().getField().getKoord().getX()*blocksize, game.getLabyrinth().getBonus().getField().getKoord().getY()*blocksize,10,10);


        //kígyó kirajzolása
        gc.setFill(Color.GREEN);
        //feje
        gc.fillRect(game.getLabyrinth().getSnake().getHead().getField().getKoord().getX()* blocksize,game.getLabyrinth().getSnake().getHead().getField().getKoord().getY()* blocksize, 10, 10);
        //teste
        for (int i = 0; i < game.getLabyrinth().getSnake().getBody().size(); i++) {
            gc.fillRect(game.getLabyrinth().getSnake().getBody().get(i).getField().getKoord().getX()*blocksize, game.getLabyrinth().getSnake().getBody().get(i).getField().getKoord().getY() * blocksize, 10, 10);
        }

            //gc.fillOval(game.getLabyrinth().getDanger().getField().getKoord().getX()*blocksize, game.getLabyrinth().getDanger().getField().getKoord().getY()*blocksize, 10, 10);





        //    labirintus rajzolasa



    }
}
