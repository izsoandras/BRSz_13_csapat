package model.map;

import model.map.things.Thing;
import model.util.Directions;
import model.util.Steppable;

public class Field {
    private Thing thing;
    private Field[] neighbors = new Field[4];

    public boolean accept(Thing candidate){
        if(thing != null)
            candidate.collideWith(thing);

        if(thing == null) {
            thing = candidate;
            candidate.setField(this);
            return true;
        }else {
            return false;
        }
    }

    public boolean softAccept(Thing candidate){
        if(thing == null){
            thing = candidate;
            return true;
        }else{
            return false;
        }
    }

    public void removeThing(){
        thing = null;
    }

    public Field getNeighbor(Directions dir) {
        return neighbors[dir.ordinal()];
    }

    public void setNeighbors(Field[] neighbors) {
        this.neighbors = neighbors;
    }

    public void setNeighbor(Directions dir, Field neighbor){
        neighbors[dir.ordinal()] = neighbor;
    }

    public  boolean isEmpty(){
        return thing == null;
    }


}
