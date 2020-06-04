package model;

import model.map.Labyrinth;
import model.util.Steppable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/** The representation of a game session
 * */
public class Game implements Steppable{
    /** The labyrinth that contains the game elements
     * */
    private Labyrinth labyrinth;

    /** The speed of the game
     * */
    private int speed;

    private boolean snakeAlive;

    /** Begin the game by enabling the timer
     * @param labyrinth the start state of the game elements
     * */
    public Game(Labyrinth labyrinth, int startSpeed){
        this.labyrinth = labyrinth;
        this.speed = startSpeed;
        snakeAlive = true;
        labyrinth.getSnake().setGame(this);
    }

    /** Restore a saved game state
     * @param memento the saved game state
     * */
    public Game(GameMemento memento){
        this.speed = memento.getSpeed();
        this.labyrinth = new Labyrinth(memento.getLabyrinth());
        this.snakeAlive = memento.isSnakeAlive();

        labyrinth.getSnake().setGame(this);
    }

    /** Clean up the session after snake dies
     * */
    public void endGame(){
        snakeAlive = false;
    }

    public void increaseSpeed(){
        speed = (int)(speed * 1.2);
    }

    /** Write to persistent storage the current state of the game
     * */
//    public void saveGame () throws IOException {//labirintusra kell mutex?
//        FileOutputStream fileOut = new FileOutputStream("game.state");
//        ObjectOutputStream out = new ObjectOutputStream(fileOut);
//        out.writeObject(labyrinth);
//        out.close();
//        fileOut.close();
//    }

    /** Perform a step of the game
     * */
    @Override
    public void step() {
        labyrinth.step();
    }


    /** Getter methods.
     * */

    public int getSpeed() {
        return speed;
    }

    public boolean isSnakeAlive() {
        return snakeAlive;
    }

    public Labyrinth getLabyrinth() {
        return labyrinth;
    }
}
