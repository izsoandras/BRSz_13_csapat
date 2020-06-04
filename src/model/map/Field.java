package model.map;

import javafx.geometry.Point2D;
import model.map.things.Thing;
import model.util.Directions;
import model.util.Point;
import model.util.Steppable;

/** Represents a Field of the map.
 *  Can contain zero or one Thing
 * */
public class Field {
    /** The Thing currently occupying the field
     * */
    private Thing thing;

    /** Neighbouring fields (UP, RIGHT, DOWN, LEFT)
     * */
    private Field[] neighbors = new Field[4];

    private Point koord;    // TODO: bal felso legyen a 0,0

    public Field(Point koord){
        this.koord = koord;
    }

    /** To be called when a Thing wants to step on this Field.
     *  Collides the incoming and the occupying Thing. If the Thing can step on this perform the change.
     * @param candidate The thing that wants to step on the field
     * @return If the thing were able to step on this field
     * */
    public boolean accept(Thing candidate){
        // If the field is not empty perform collision
        if(thing != null)
            candidate.collideWith(thing);

        // If the field is empty after the collision the new one can step on it
        if(thing == null) {
            thing = candidate;
            candidate.setField(this);
            return true;
        }else {
            return false;
        }
    }

    /** Only tries to step on the field if it's empty
     * @param candidate The thing that wants to step on the field
     * @return If the thing were able to step on this field
     * */
    public boolean softAccept(Thing candidate){
        if(thing == null){
            thing = candidate;
            return true;
        }else{
            return false;
        }
    }

    /** Remove the currently occupying Thing from the field
     * */
    public void removeThing(){
        thing = null;
    }

    /** Returns the neighbor of the field in the given direction
     * @param dir The direction of the neighbor
     * @return The neighbor of the field in the given direction
     * */
    public Field getNeighbor(Directions dir) {
        return neighbors[dir.ordinal()];
    }

    /** Sets the neighbours according to the given array
     * @param neighbors The array of the neighboring fields
     * */
    public void setNeighbors(Field[] neighbors) {
        this.neighbors = neighbors;
    }

    /** Sets the neighbor in the given direction to the given Field
     * @param dir The direction we want to change
     * @param neighbor The intended neighbor in the given direction
     * */
    public void setNeighbor(Directions dir, Field neighbor){
        neighbors[dir.ordinal()] = neighbor;
    }
    /** Returns if the Field is not occupied
     * @return True if the Field is not occupied, False if the field is occupied
     * */
    public  boolean isEmpty(){
        return thing == null;
    }

    public Point getKoord(){
        return koord;
    }
}
