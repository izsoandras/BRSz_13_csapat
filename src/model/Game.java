package model;

import model.map.Labyrinth;
import model.util.Steppable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/** The representation of a game session
 * */
public class Game implements Steppable{
    /** The labyrinth that contains the game elements
     * */
    private Labyrinth labyrinth;

    // TODO: hogyan adja vissza a játék eredményét?
    /** Begin the game by enabling the timer
     * @param labyrinth the start state of the game elements
     * */
    public void startGame(Labyrinth labyrinth){
        this.labyrinth = labyrinth;
        // TODO: set up timer to call labyrinth.step
    }

    /** Pause the timer when player is in Esc menu
     * */
    public void pauseGame(){
        // TODO: pause timer
    }

    /** Clean up the session after snake dies
     * */
    public void endGame(){
        // TODO: hogyan kell megallnia a jateknak??
    }

    /** Write to persistent storage the current state of the game
     * */
    public void saveGame () throws IOException {//labirintusra kell mutex?
        FileOutputStream fileOut = new FileOutputStream("game.state");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(labyrinth);
        out.close();
        fileOut.close();
    }

    /** Perform a step of the game
     * */
    @Override
    public void step() {
        labyrinth.step();
    }
}
