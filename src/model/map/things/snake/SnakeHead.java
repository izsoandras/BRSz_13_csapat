package model.map.things.snake;

import model.util.Directions;

public class SnakeHead extends SnakePart {
    private Directions dir;
    private Snake snek;

    public void setDirection(Directions dir){
        this.dir = dir;
    }

    @Override
    public void step(){
        getField().removeThing();
        getField().getNeighbor(dir).accept(this);
    }

    public void behead(){
        snek.die();
    }

    public void addPoints(int newPoints){
        snek.addPoints(newPoints);
    }

    public void removePoints(int lostPoints){
        snek.removePoints(lostPoints);
    }

    public void grow(){
        snek.grow();
    }

    public void setSnake(Snake snake){
        this.snek = snake;
    }

    public Directions getDir(){
        return dir;
    }
}
