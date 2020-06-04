package model;

import model.map.LabyrinthMemento;

import java.io.Serializable;

/** Saves the state of the game
 * */
public class GameMemento implements Serializable {
    /** The step speed
     * */
    private int speed;
    /** The labyrinth where the snake goes around
     * */
    private LabyrinthMemento labyrinth;
    /** Signals if the snake is alive. Used to determine end of game.
     * */
    private boolean snakeAlive;

    public GameMemento(Game game){
        speed = game.getSpeed();
        labyrinth = new LabyrinthMemento(game.getLabyrinth());
        snakeAlive = game.isSnakeAlive();
    }

    /** Getter methods.
     * */

    public int getSpeed() {
        return speed;
    }

    public LabyrinthMemento getLabyrinth() {
        return labyrinth;
    }

    public boolean isSnakeAlive() {
        return snakeAlive;
    }
}
