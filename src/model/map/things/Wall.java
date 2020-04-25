package model.map.things;

import model.map.things.snake.SnakeHead;

public class Wall extends Thing {
    @Override
    public void hitBy(SnakeHead sh){
        sh.behead();
    }
}
