package model.map.things.snake;

import model.map.Field;
import model.map.things.Thing;

/** A part of the snake's body, that's following the head
 * */
public class SnakeBodyPart extends SnakePart {
    /** The previous snake part (head or body)
     * */
    private SnakePart previousSnakePart;

    /** The previous snake part has to be given to create a SnakeBodyPart
     * @param perviousSnakePart The previous part of the snake (starting by the head)
     * */
    public SnakeBodyPart(SnakePart perviousSnakePart){
        this.previousSnakePart = perviousSnakePart;
    }

    /** Changes the previous snake part
     * @param previousSnakePart The previous part of the snake (starting by the head)
     * */
    public void setPreviousSnakePart(SnakeBodyPart previousSnakePart) {
        this.previousSnakePart = previousSnakePart;
    }

    /** Performs a step of the snakepart.
     * It should just simply follow the previous part.
     * */
    @Override
    public void step() {
        getField().removeThing();
        Field potentialNextField = previousSnakePart.getField();
        previousSnakePart.step();
        if(!potentialNextField.accept(this)){
            getField().accept(this);
        }
    }

    /** If the snake is hit by it's head, the snake dies.
     * @param sh The snake's head
     * */
    @Override
    public void hitBy(SnakeHead sh){
        sh.behead();
    }

    @Override
    public void collideWith(Thing t){
        t.hitBy(this);
    }

    public void collideWith(SnakeHead sh){
        sh.behead();
    }
}
