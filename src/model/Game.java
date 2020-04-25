package model;

import model.map.Labyrinth;
import model.util.Steppable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Game implements Steppable{
    private Labyrinth labyrinth;

    public void startGame(Labyrinth labyrinth){
        this.labyrinth = labyrinth;
        // TODO: set up timer to call labyrinth.step
    }

    public void pauseGame(){
        // TODO: pause timer
    }

    public void endGame(){
        // TODO: hogyan kell megallnia a jateknak??
    }

    public void saveGame () throws IOException {//labirintusra kell mutex?
        FileOutputStream fileOut = new FileOutputStream("game.state");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(labyrinth);
        out.close();
        fileOut.close();
    }

    @Override
    public void step() {
        labyrinth.step();
    }
}
