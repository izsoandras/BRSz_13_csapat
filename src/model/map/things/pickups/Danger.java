package model.map.things.pickups;

import model.map.Labyrinth;
import model.map.things.snake.SnakeHead;

/** Represents the Dangers lying in the labyrinth. If the snake collects a danger it loses some points.
 * */
public class Danger extends Extra {
    /** The number of points the snake loses if it collects the danger.
     * */
    private static final int MINUS_POINTS = 10;

    /** The Danger cannot be created without a labyrinth
     * */
    public Danger(Labyrinth labyrinth) {
        super(labyrinth);
    }

    /** Performs a step of the Danger. After performing what every extra does during a step it removes itself from the
     * labyrinth if it's lifespan reached
     * */
    @Override
    public void step(){
        super.step();
        if(super.getLifeLength() <= 0)
            super.getLabyrinth().removeDanger();
    }

    /** If the Danger is hit by a SnakeHead, the Snake shall lose some points.
     * */
    @Override
    public void hitBy(SnakeHead sh){
        sh.removePoints(MINUS_POINTS);
        super.getLabyrinth().removeBonus();
        getField().removeThing();
    }
}
