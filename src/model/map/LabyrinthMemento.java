package model.map;

import model.util.Directions;
import model.util.LabyrinthType;
import model.util.Point;
import model.util.SnakeMemento;

import java.util.List;

/**
 * */
public class LabyrinthMemento {
    private SnakeMemento snakeMemento;
    private Point food;
    private Point bonus;
    private Point danger;

    private LabyrinthType type;
    private int timeSinceLastExtra;


    public LabyrinthMemento(Labyrinth labyrinth) {
        this.snakeMemento = new SnakeMemento(labyrinth.getSnake());
        this.food = labyrinth.getFood().getField().getKoord();
        this.bonus = labyrinth.getBonus().getField().getKoord();
        this.danger = labyrinth.getDanger().getField().getKoord();
        this.type = labyrinth.getType();
        this.timeSinceLastExtra = labyrinth.getTimeSinceLastExtra();
    }

    public Point getFood() {
        return food;
    }

    public Point getBonus() {
        return bonus;
    }

    public Point getDanger() {
        return danger;
    }

    public LabyrinthType getType() {
        return type;
    }

    public int getTimeSinceLastExtra() {
        return timeSinceLastExtra;
    }

    public SnakeMemento getSnakeMemento() {
        return snakeMemento;
    }
}
