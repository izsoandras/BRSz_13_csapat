package model.map.things.pickups;

import model.map.Labyrinth;
import model.map.things.snake.SnakeHead;

public class Bonus extends Extra {
    private static final int PLUS_POINTS = 10;

    public Bonus(Labyrinth labyrinth) {
        super(labyrinth);
    }

    @Override
    public void step(){
        super.step();
        if(super.getLifeLength()  <= 0)
            super.getLabyrinth().removeBonus();
    }

    @Override
    public void hitBy(SnakeHead sh){
        sh.addPoints(PLUS_POINTS);
        super.getLabyrinth().removeBonus();
        getField().removeThing();
    }
}
