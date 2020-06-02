package model.map.things;

import model.map.Field;
import model.map.things.pickups.Extra;
import model.map.things.snake.SnakeHead;

/** Represents the stuff that can be found in the labyrinth.
 * */
public abstract class Thing {
    /** The field which is occupied by the THing
     * */
    private Field field;

    /** When an other Thing tries to step on a Field occupied by this Thing, the two Things collide,
     * and the other one gets hit by this.
     * @param t the other thing
     * */
    public void collideWith(Thing t){
        t.hitBy(this);
    }

    /** The general Thing does nothing when hit by any other thing. Should be overridden to perform an action.
     * @param t The other thing
     * */
    public void hitBy(Thing t){}
    /** The general Thing does nothing when hit by any other thing. Should be overridden to perform an action.
     * @param sh The other thing
     * */
    public void hitBy(SnakeHead sh){}
    /** The general Thing does nothing when hit by any other thing. Should be overridden to perform an action.
     * @param e The other thing
     * */
    public void hitBy(Extra e){}

    /** Setter and getter methods
     * */

    public void setField(Field field) {
        this.field = field;
    }
    public Field getField() {
        return field;
    }
}
