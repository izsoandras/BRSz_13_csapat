package model.map.things;

import model.map.things.snake.SnakeHead;

/** Represents the wall of the labyrinth. The wall does not move, the bonuses and dangers can't step on it,
 * and if the snake steps on it it dies.
 * */
public class Wall extends Thing {
    /** If the Wall is hit by the Snake, the snake shall die
     * */
    @Override
    public void hitBy(SnakeHead sh){
        sh.behead();
    }
}
