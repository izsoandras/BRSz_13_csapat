package model.map.things.snake;

import model.Game;
import model.util.Steppable;

import java.util.List;

public class Snake implements Steppable {
    private Game game;
    private SnakeHead head;
    private List<SnakeBodyPart> body;
    private int points = 0;

    public Snake(SnakeHead head, List<SnakeBodyPart> body){
        this.head = head;
        this.body = body;
    }

    @Override
    public void step() {
        body.get(body.size()-1).step();
    }

    protected void addPoints(int earnedPoints){
        points += earnedPoints;
    }

    protected void removePoints(int lostPoints){
        points = Math.max(0, points-lostPoints);
    }

    public int getPoints() {
        return points;
    }

    protected void grow(){
        SnakeBodyPart currentLastPart = body.get(body.size()-1);
        SnakeBodyPart newSbp = new SnakeBodyPart(currentLastPart);
        //The snake parts should remove them from their Field BEFORE trying to accept them to the next field
        //If the Snake cannot be accepted, then it is end of game
        //If the call sequence reaches grow(), the field of the last snake part is supposed to be empty,
        // and the field of the last part is supposed to be set by it's new field during the return sequence
        newSbp.setField(currentLastPart.getField());
        currentLastPart.getField().accept(newSbp);
    }

    protected void die(){
        game.endGame();
    }
}
