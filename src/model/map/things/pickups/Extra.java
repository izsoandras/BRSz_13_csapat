package model.map.things.pickups;

import model.map.Labyrinth;
import model.map.things.Thing;
import model.util.Directions;
import model.util.Steppable;

import java.util.Random;

/**  The extra brackets the common functionality of the Dangers and the Bonuses
 * */
public abstract class Extra extends Thing implements Steppable {
    /** Time passed since the last step
     * */
    private int timeCounter = 0;
    /** The number of steps till the Extra can be collected by the snake
     * */
    private int lifeLength = 1000;
    /** The Extra is slower than the snake. This is the prescaler of that speed
     * */
    private static final int STEP_PRESCALER = 10;
    /** The labyrinth in which the Extra can be found
     * */
    private Labyrinth labyrinth;
    /** The random generator used for generating the stepping direction
     * */
    private Random rng = new Random();

    public Extra(Labyrinth labyrinth){
        this.labyrinth = labyrinth;
    }

    /** Performs a step of the Extra. An Extra steps in a random direction after every prescaler number of steps.
     * If it can't it tries to step in an other direction
     * */
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
                getField().removeThing();
                accepted = getField().getNeighbor(dirs[dirIdx]).accept(this);
                    offset++;
            }
            if(!accepted)
                getField().accept(this);
            timeCounter = 0;
        }
    }

    /** Getter methods
     * */

    protected int getLifeLength(){
        return lifeLength;
    }

    public Labyrinth getLabyrinth() {
        return labyrinth;
    }
}
