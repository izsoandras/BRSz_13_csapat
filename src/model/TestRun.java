package model;

import model.map.Labyrinth;
import model.util.LabyrinthType;

public class TestRun {
    public static void main(String[] args) {
        System.out.println((-1+5)%5);
        Labyrinth labyrinth = new Labyrinth(LabyrinthType.WALLESS);
        labyrinth = new Labyrinth(LabyrinthType.WALLED);
        labyrinth = new Labyrinth(LabyrinthType.EXTRA);
    }
}
