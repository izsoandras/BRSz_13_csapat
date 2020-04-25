package model.map;

import model.GameParams;
import model.map.things.pickups.Bonus;
import model.map.things.pickups.Danger;
import model.map.things.pickups.Food;
import model.map.things.snake.Snake;
import model.util.Steppable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Labyrinth implements Steppable {

    private Food food;
    private Bonus bonus;
    private Danger danger;
    private Snake snake;
    private int timeSinceLastExtra = 0;
    private final int timeBetweenExtras = GameParams.STEPS_BETWEEN_EXTRAS;
    private Field[][] fields;
    private Random rng = new Random();

//    public Labyrinth(Map descriptor){
//    }

    public Labyrinth(Snake snake, Field[][] fields){
        this.snake = snake;
        this.fields = fields;
        generateNewFood();
        bonus = null;
        danger = null;
    }

    public void removeFood(){
        generateNewFood();
    }

    public void removeBonus(){
        bonus = null;
    }

    public void removeDanger(){
        danger = null;
    }

    @Override
    public void step() {
        timeSinceLastExtra++;

        snake.step();
        if(bonus != null)
            bonus.step();
        if(danger != null)
            danger.step();

        if(timeSinceLastExtra >= timeBetweenExtras){
            if(bonus == null){
                bonus = new Bonus(this);
                Field place = chooseRandomEmptyField();
                if(place != null) {
                    bonus.setField(place);
                    place.accept(bonus);
                }
            }
            if(danger == null){
                danger = new Danger(this);
                Field place = chooseRandomEmptyField();
                if(place != null) {
                    danger.setField(place);
                    place.accept(danger);
                }
            }
        }
    }

    private void generateNewFood(){
        Field place = chooseRandomEmptyField();
        if(place != null) {
            food = new Food(this);
            food.setField(place);
            place.accept(food);
        }
    }

    private Field chooseRandomEmptyField(){
        List<Field> empties = getEmptyFields();
        if(!empties.isEmpty()) {
            return empties.get(rng.nextInt(empties.size()));
        }else{
            return null;
        }
    }

    private List<Field> getEmptyFields(){
        List<Field> emptyFields = new LinkedList<>();
        for (Field[] field : fields) {
            for (int j = 0; j < fields[0].length; j++) {
                if (field[j].isEmpty())
                    emptyFields.add(field[j]);
            }
        }
        return  emptyFields;
    }
}
