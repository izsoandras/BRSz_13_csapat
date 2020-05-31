package model.util;

import javafx.scene.control.skin.TextInputControlSkin;
import model.map.Labyrinth;
import model.map.things.snake.Snake;
import model.map.things.snake.SnakeBodyPart;

import java.util.LinkedList;
import java.util.List;

/**
 * */
public class SnakeMemento {
    private Point head;
    private List<Point> bodyParts;
    private Directions lastDir;

    private int points;

    public SnakeMemento(Snake s){
        head = s.getHead().getField().getKoord();
        points = s.getPoints();
        
        bodyParts = new LinkedList<>();
        for (SnakeBodyPart sbp :s.getBody()) {
            bodyParts.add(sbp.getField().getKoord());
        }
        lastDir = s.getDir();
    }

    public Point getHead() {
        return head;
    }

    public List<Point> getBodyParts() {
        return bodyParts;
    }

    public int getPoints() {
        return points;
    }

    public Directions getDir(){
        return lastDir;
    }
}
