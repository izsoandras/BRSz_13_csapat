package model.map.things.snake;

import model.map.things.Thing;
import model.map.things.pickups.Extra;
import model.util.Directions;

/** Represents the head of the snake. It stores the direction where it goes and has a pointer to the whole snake.
 * */
public class SnakeHead extends SnakePart {
    /** The direction in which the snake is going
     * */
    private Directions dir;

    private Directions lastDir;
    /** The snake to which the head belongs
     * */
    private Snake snek;

    public SnakeHead(Directions dir){
        this.dir = dir;
        this.lastDir = dir;
    }

    /** Performs a step of the snake head
     * */
    @Override
    public void step(){
        getField().removeThing();

        if(!getField().getNeighbor(dir).accept(this)){
            getField().accept(this);
        }
        this.lastDir = this.dir;
    }

    @Override
    public void collideWith(Thing t){
        t.hitBy(this);
    }

    @Override
    public void hitBy(SnakeBodyPart sbp) {
        snek.die();
    }

    @Override
    public void hitBy(Extra e){
        e.hitBy(this);
    }

    /** Tis function is called when the snake hits a wall or itself.
     * */
    public void behead(){
        snek.die();
    }

    /** This function should be called when a snake picks up a food or bonus. Increases the points of the snake.
     * @param newPoints the amount of points earned
     * */
    public void addPoints(int newPoints){
        snek.addPoints(newPoints);
    }

    /** This function is called when the snake picks up a danger. Decreases the points of the snake.
     * @param lostPoints the amount of the points lost
     * */
    public void removePoints(int lostPoints){
        snek.removePoints(lostPoints);
    }

    /** This is called when the snake picks up a food. The snake grows by 1 field
     * */
    public void grow(){
        snek.grow();
    }

    /** Increases the snake's speed
     * */
    public void speedUp(){
        snek.speedUp();
    }

    /** Setter and getter methods
     * */

    public void setSnake(Snake snake){
        this.snek = snake;
    }

    public Directions getDir(){
        return dir;
    }


    public void setDirection(Directions dir){
        if((dir.ordinal()+2)%4 != lastDir.ordinal()) {
            this.dir = dir;
        }
    }
}
