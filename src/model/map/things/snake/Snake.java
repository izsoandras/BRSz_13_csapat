package model.map.things.snake;

import model.Game;
import model.map.Field;
import model.map.Labyrinth;
import model.util.Directions;
import model.util.Point;
import model.util.SnakeMemento;
import model.util.Steppable;

import java.util.LinkedList;
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

    public Snake(SnakeMemento sm, Labyrinth l){
        head = new SnakeHead();
        head.setDirection(sm.getDir());
        head.setSnake(this);
        Field headField = l.getFields()[sm.getHead().getX()][sm.getHead().getY()];
        head.setField(headField);
        headField.accept(head);

        body = new LinkedList<>();
        Field bodyField = l.getFields()[sm.getBodyParts().get(0).getX()][sm.getBodyParts().get(0).getY()];
        SnakeBodyPart sbp = new SnakeBodyPart(head);
        sbp.setField(bodyField);
        bodyField.accept(sbp);
        for (int i = 1; i < sm.getBodyParts().size(); i++){
            sbp = new SnakeBodyPart(body.get(i-1));
            bodyField = l.getFields()[sm.getBodyParts().get(i).getX()][sm.getBodyParts().get(i).getY()];
            sbp.setField(bodyField);
            bodyField.accept(sbp);
            body.add(sbp);
        }

        points = sm.getPoints();
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public SnakeHead getHead() {
        return head;
    }

    public List<SnakeBodyPart> getBody() {
        return body;
    }

    public Snake(){

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

    public Directions getDir(){
        return head.getDir();
    }
}
