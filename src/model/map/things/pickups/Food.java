package model.map.things.pickups;

import model.map.Labyrinth;
import model.map.things.Thing;
import model.map.things.snake.SnakeHead;

/** Represents the food in the labyrinth. If he snakes eats a food a fixed amount of points shall be given to it.
 * */
public class Food extends Thing {
    /** The labyrinth in which the food can be found
     * */
    private Labyrinth labyrinth;
    /** The amount of points the snake earns when it eats the food
     * */
    private static final int POINTS_PER_FOOD = 5;

    /** The food cannot be created without a labyrinth
     * */
    public Food(Labyrinth labyrinth){
        this.labyrinth = labyrinth;
    }

    /** If the snake reached a food it shall earn points and grow.
     * @param sh The head of the snake.
     * */
    @Override
    public void hitBy(SnakeHead sh) {
        labyrinth.removeFood();
        getField().removeThing();
        sh.addPoints(POINTS_PER_FOOD);
        sh.grow();
    }
}
