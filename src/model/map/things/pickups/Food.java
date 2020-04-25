package model.map.things.pickups;

import model.map.Labyrinth;
import model.map.things.Thing;
import model.map.things.snake.SnakeHead;

public class Food extends Thing {
    private Labyrinth labyrinth;
    private static final int POINTS_PER_FOOD = 5;

    public Food(Labyrinth labyrinth){
        this.labyrinth = labyrinth;
    }

    @Override
    public void hitBy(SnakeHead sh) {
        labyrinth.removeFood();
        getField().removeThing();
        sh.addPoints(POINTS_PER_FOOD);
        sh.grow();
    }
}
