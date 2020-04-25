package model.map.things;

import model.map.Field;
import model.map.things.pickups.Extra;
import model.map.things.snake.SnakeHead;

public abstract class Thing {
    private Field field;

    public void collideWith(Thing t){
        t.hitBy(this);
    }
    public void hitBy(Thing t){}
    public void hitBy(SnakeHead sh){}
    public void hitBy(Extra e){}

    public void setField(Field field) {
        this.field = field;
    }
    public Field getField() {
        return field;
    }
}
