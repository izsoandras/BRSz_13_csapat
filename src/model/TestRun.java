package model;

import model.map.Labyrinth;
import model.util.LabyrinthFactory;

public class TestRun {
    public static void main(String[] args) {
        System.out.println((-1+5)%5);
        Labyrinth labyrinth = LabyrinthFactory.createWallessLabyrinth();
    }
}
