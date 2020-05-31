package model.util;

import javafx.scene.control.skin.TextInputControlSkin;
import model.map.Labyrinth;
import model.map.things.snake.Snake;
import model.map.things.snake.SnakeBodyPart;

import java.util.LinkedList;
import java.util.List;

/** Saves the state of the snake.
 * */
public class SnakeMemento {
    /** The position of the head
     * */
    private Point head;
    /** The list of the positions of the SnakeBodyParts
     * */
    private List<Point> bodyParts;
    /** The last direction in which the snake was going
     * */
    private Directions lastDir;

    /** The points collected by the snake
     * */
    private int points;

    /** Saves the state of the snake.
     * */
    public SnakeMemento(Snake s){
        head = s.getHead().getField().getKoord();
        points = s.getPoints();
        
        bodyParts = new LinkedList<>();
        for (SnakeBodyPart sbp :s.getBody()) {
            bodyParts.add(sbp.getField().getKoord());
        }
        lastDir = s.getDir();
    }


    /** Getter methods.
     * */

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
