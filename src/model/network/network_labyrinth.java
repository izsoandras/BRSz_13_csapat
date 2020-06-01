package model.network;
import model.map.LabyrinthMemento;

public class network_labyrinth implements java.io.Serializable {
    public LabyrinthMemento Labyrinth_data;
    public Game_status Status;

    public network_labyrinth(){
        Status = new Game_status();
    }

}
