package UI;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Game;
import model.map.Labyrinth;
import model.util.LabyrinthType;

public class GameTimer {
    private static Game game;


    public void SingleGame(LabyrinthType lab, int speed){
        Labyrinth labyrinth = new Labyrinth(lab);   //labirintus létrehozása beállítások alapján
        Game game = new Game(labyrinth, speed);     //Játék indítátasa beállítások alapján

        new AnimationTimer() {
            long lastTick = 0;

            public void handle(long now) {
                if (lastTick == 0) {
                    lastTick = now;
                    tick();
                    return;
                }

                if (now - lastTick > 1000000000 / 5) {
                    lastTick = now;
                    tick();

                }
            }

        }.start();
    }
    public static void tick() {
        //játék logikája
        game.step();
        System.out.println("game");


        //    labirintus rajzolasa



    }
}
