package model.map.things.snake;

import model.map.Field;

public class SnakeBodyPart extends SnakePart {
    private SnakePart previousSnakePart;

    public SnakeBodyPart(SnakePart perviousSnakePart){
        this.previousSnakePart = perviousSnakePart;
    }

    public void setPreviousSnakePart(SnakeBodyPart previousSnakePart) {
        this.previousSnakePart = previousSnakePart;
    }

    @Override
    public void step() {
        getField().removeThing();
        Field potentialNextField = previousSnakePart.getField();
        previousSnakePart.step();
        potentialNextField.accept(this);
    }

    @Override
    public void hitBy(SnakeHead sh){
        sh.behead();
    }
}
