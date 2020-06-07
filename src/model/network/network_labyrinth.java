package model.network;
import model.map.Labyrinth;
import model.map.LabyrinthMemento;
import model.util.LabyrinthType;

public class network_labyrinth implements java.io.Serializable {
    public LabyrinthMemento Labyrinth_data;
    public Game_status Status;

    public network_labyrinth(){
        Labyrinth temp_lab = new Labyrinth(LabyrinthType.WALLESS);
        Labyrinth_data = new LabyrinthMemento( temp_lab );
        Status = new Game_status();
    }

}
