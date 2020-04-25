package model.map.things.pickups;

import model.map.Labyrinth;
import model.map.things.Thing;
import model.util.Directions;
import model.util.Steppable;

import java.util.Random;

public abstract class Extra extends Thing implements Steppable {
    private int timeCounter = 0;
    private int lifeLength = 10;
    private static final int STEP_PRESCALER = 2;
    private Labyrinth labyrinth;
    private Random rng = new Random();

    public Extra(Labyrinth labyrinth){
        this.labyrinth = labyrinth;
    }

    @Override
    public void step() {
        timeCounter++;
        lifeLength--;
        if(timeCounter >= STEP_PRESCALER) {
            Directions[] dirs = Directions.values();
            int dirIdx = rng.nextInt(dirs.length);

            boolean accepted = false;
            int offset = 0;
            while(!accepted && offset < Directions.values().length){
                accepted = getField().getNeighbor(dirs[dirIdx]).accept(this);
                offset++;
            }
        }
    }

    protected int getLifeLength(){
        return lifeLength;
    }

    public Labyrinth getLabyrinth() {
        return labyrinth;
    }
}
