package model.map;

import model.util.LabyrinthType;
import model.util.Point;
import model.util.SnakeMemento;

import java.io.Serializable;

/** Stores the state of the labyrinth in a way it can be restored
 * */
public class LabyrinthMemento implements java.io.Serializable{
    /** The saved state of the snake
     * */
    private SnakeMemento snakeMemento;
    /** The position of the food.
     * */
    private Point food;
    /** The position of the bonus. Can be null.
     * */
    private Point bonus;
    /** The position of the danger. Can be null.
     * */
    private Point danger;

    /** The layout of the labyrinth
     * */
    private LabyrinthType type;
    /** Time estimated since the last Extras appeared
     * */
    private int timeSinceLastExtra;

    /** Saves the state of a labyrinth.
     * @param labyrinth The labyrinth which's state should be saved.
     * */
    public LabyrinthMemento(Labyrinth labyrinth) {
        this.snakeMemento = new SnakeMemento(labyrinth.getSnake());
        this.food = labyrinth.getFood().getField().getKoord();
        if(labyrinth.getBonus() != null){
            this.bonus = labyrinth.getBonus().getField().getKoord();
        }else{
            this.bonus = null;
        }
        if(labyrinth.getDanger() != null){
            this.danger = labyrinth.getDanger().getField().getKoord();
        }else{
            this.danger = null;
        }
        this.type = labyrinth.getType();
        this.timeSinceLastExtra = labyrinth.getTimeSinceLastExtra();
    }

    /** Getter methods
     * */

    public Point getFood() {
        return food;
    }

    public Point getBonus() {
        return bonus;
    }

    public Point getDanger() {
        return danger;
    }

    public LabyrinthType getType() {
        return type;
    }

    public int getTimeSinceLastExtra() {
        return timeSinceLastExtra;
    }

    public SnakeMemento getSnakeMemento() {
        return snakeMemento;
    }
}
