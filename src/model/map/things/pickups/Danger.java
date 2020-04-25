package model.map.things.pickups;

import model.map.Labyrinth;
import model.map.things.snake.SnakeHead;

public class Danger extends Extra {
    private static final int MINUS_POINTS = 10;

    public Danger(Labyrinth labyrinth) {
        super(labyrinth);
    }

    @Override
    public void step(){
        super.step();
        if(super.getLifeLength() <= 0)
            super.getLabyrinth().removeDanger();
    }

    @Override
    public void hitBy(SnakeHead sh){
        sh.removePoints(MINUS_POINTS);
        super.getLabyrinth().removeBonus();
        getField().removeThing();
    }
}
