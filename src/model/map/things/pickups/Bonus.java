package model.map.things.pickups;

import model.map.Labyrinth;
import model.map.things.snake.SnakeHead;

/** Represents the Dangers lying in the labyrinth. If the snake collects a danger it earns some points without growing.
 * */
public class Bonus extends Extra {
    /** The number of points the snake earns if it collects the danger.
     * */
    private static final int PLUS_POINTS = 10;

    /** The Bonus cannot be created without a labyrinth
     * */
    public Bonus(Labyrinth labyrinth) {
        super(labyrinth);
    }

    /** Performs a step of the Bonus. After performing what every extra does during a step it removes itself from the
     * labyrinth if it's lifespan reached
     * */
    @Override
    public void step(){
        super.step();
        if(super.getLifeLength()  <= 0)
            super.getLabyrinth().removeBonus();
    }

    /** If the Bonus is hit by a SnakeHead, the Snake shall earn some points.
     * */
    @Override
    public void hitBy(SnakeHead sh){
        sh.addPoints(PLUS_POINTS);
        super.getLabyrinth().removeBonus();
        getField().removeThing();
    }
}
