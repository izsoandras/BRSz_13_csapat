package model.map;

import model.GameParams;
import model.map.things.pickups.Bonus;
import model.map.things.pickups.Danger;
import model.map.things.pickups.Food;
import model.map.things.snake.Snake;
import model.map.things.snake.SnakeBodyPart;
import model.map.things.snake.SnakeHead;
import model.util.Directions;
import model.util.LabyrinthType;
import model.util.Point;
import model.util.Steppable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/** Represents the all-time game state
 * */
public class Labyrinth implements Steppable {
    /** The Food item that is always present in the labyrinth
     * */
    private Food food;
    /** The Bonus that appears from time to time on the map
     * */
    private Bonus bonus;
    /** The Danger that appears from time to time on the map
     * */
    private Danger danger;
    /** The Snake that is controlled by the user
     * */
    private Snake snake;
    /** Count the steps since the last Bonus and Danger appeared
     * */
    private int timeSinceLastExtra = 0;
    /** The fix step count between Bonus and Danger appearing
     * */
    private final int timeBetweenExtras = GameParams.STEPS_BETWEEN_EXTRAS;


    /** The Fields that make up the labyrinth
     * */
    private Field[][] fields;
    /** Random number generator for Food and Extra placement
     * */
    private Random rng = new Random();

    LabyrinthType type;

//    public Labyrinth(Map descriptor){
//    }

    /** The main constructor. Requires a ready-built map and a Snake that is in this map
     * */
    public Labyrinth(LabyrinthType type){
        this.type = type;
        switch (type){
            case WALLESS:
                createWallessLabyrinth();
                break;
            case WALLED:
                break;
            case EXTRA:
                break;
        }
        generateNewFood();
        bonus = null;
        danger = null;
    }

    public Labyrinth(LabyrinthMemento memento){
        timeSinceLastExtra = memento.getTimeSinceLastExtra();

        this.type = memento.getType();
        switch (type){
            case WALLESS:
                fields = makeFields(GameParams.LABYRINTH_WIDTH,GameParams.LABYRINTH_HEIGHT);
                break;
            case WALLED:
                break;
            case EXTRA:
                break;
        }

        snake = new Snake(memento.getSnakeMemento(), this);

        food = new Food(this);
        Field foodField = fields[memento.getFood().getX()][memento.getFood().getY()];
        food.setField(foodField);
        foodField.accept(food);

        bonus = new Bonus(this);
        Field bonusField = fields[memento.getBonus().getX()][memento.getBonus().getY()];
        food.setField(bonusField);
        bonusField.accept(food);

        danger = new Danger(this);
        Field dangerField = fields[memento.getDanger().getX()][memento.getDanger().getY()];
        food.setField(dangerField);
        dangerField.accept(food);
    }

    /** To be called when the Snake eats the food. Generates a new one
     * The Food should already remove itself from it's Field by this time
     * */
    public void removeFood(){
        generateNewFood();
    }

    /** To be called when the Snake eats the Bonus. Removes the bonus from the map
     * The Bonus should already remove itself from it's Field by this time
     * */
    public void removeBonus(){
        bonus = null;
    }

    /** To be called when the Snake eats the Bonus. Removes the bonus from the map
     * The Danger should already remove itself from it's Field by this time
     * */
    public void removeDanger(){
        danger = null;
    }

    /** Perform the step of the whole labyrinth
     * Increment time counter, call step for snake, and Extras if they are present. If not decide if a new one is t
     * place, and create them.
     * */
    @Override
    public void step() {
        //increment time
        timeSinceLastExtra++;
        //call step for snake
        snake.step();
        //call step for bonuses
        if(bonus != null)
            bonus.step();
        if(danger != null)
            danger.step();
        //if enough time passed, place new extras
        if(timeSinceLastExtra >= timeBetweenExtras){
            if(bonus == null){
                bonus = new Bonus(this);
                Field place = chooseRandomEmptyField();
                if(place != null) {
                    bonus.setField(place);
                    //the place Field should accept the Bonus since it was chosen as an empty Field
                    place.accept(bonus);
                }
            }
            if(danger == null){
                danger = new Danger(this);
                Field place = chooseRandomEmptyField();
                if(place != null) {
                    danger.setField(place);
                    //the place Field should accept the Danger since it was chosen as an empty Field
                    place.accept(danger);
                }
            }
            //reset timer
            timeSinceLastExtra = 0;
        }
    }

    /** Create and place new Food object
     * */
    private void generateNewFood(){
        Field place = chooseRandomEmptyField();
        if(place != null) {
            food = new Food(this);
            food.setField(place);
            //the place Field should accept the Food since it was chosen as an empty Field
            place.accept(food);
        }
    }

    /** Collects the empty fields of the map for deterministic random choosing
     * */
    private Field chooseRandomEmptyField(){
        List<Field> empties = getEmptyFields();
        if(!empties.isEmpty()) {
            return empties.get(rng.nextInt(empties.size()));
        }else{
            return null;
        }
    }

    /** Collect the empty fields of the map
     * */
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

    private void createWallessLabyrinth(){
        final int SNAKE_BODY_LENGTH = 5;
        final int SNAKE_START_X = 30;
        final int SNAKE_START_Y = 30;

        this.fields = makeFields(GameParams.LABYRINTH_WIDTH, GameParams.LABYRINTH_HEIGHT);
        SnakeHead snakeHead = new SnakeHead();
        snakeHead.setDirection(Directions.RIGHT);
        List<SnakeBodyPart> sbp = new LinkedList<>();
        sbp.add(new SnakeBodyPart(snakeHead));
        for(int i = 1; i < SNAKE_BODY_LENGTH; i++){
            sbp.add( new SnakeBodyPart( sbp.get(i-1) ) );
        }

        snakeHead.setField(fields[SNAKE_START_X][SNAKE_START_Y]);
        fields[SNAKE_START_X][SNAKE_START_Y].accept(snakeHead);
        for(int i = 0; i < sbp.size(); i++){
            sbp.get(i).setField(fields[SNAKE_START_X+i+1][SNAKE_START_Y]);
            fields[SNAKE_START_X+i+1][SNAKE_START_Y].accept(sbp.get(i));
        }

        this.snake = new Snake(snakeHead, sbp);
    }

    /** Generates and joins the empty fields of the labyrinth
     * */
    private static Field[][] makeFields(int labyrinthWidht, int labyrinthHeight){
        //Create the fields
        Field[][] fields = new Field[labyrinthWidht][labyrinthHeight];
        for(int i = 0; i < labyrinthWidht; i++){
            for(int j = 0; j < labyrinthHeight; j++){
                fields[i][j] = new Field(new Point(i,j));
            }
        }
        //Connect the fields as they are neighbor to each other
        for(int i = 0; i < labyrinthWidht; i++){
            for(int j = 0; j < labyrinthHeight; j++){
                fields[i][j].setNeighbor(Directions.UP, fields[i][(j-1 + labyrinthHeight)%labyrinthHeight]);
                fields[i][j].setNeighbor(Directions.DOWN, fields[i][(j+1)%labyrinthHeight]);
                fields[i][j].setNeighbor(Directions.LEFT, fields[(i-1 + labyrinthWidht)%labyrinthWidht][j]);
                fields[i][j].setNeighbor(Directions.RIGHT, fields[(i+1)%labyrinthWidht][j]);
            }
        }

        return  fields;
    }

    public LabyrinthMemento makeMemento(){
        return new LabyrinthMemento(this);
    }

    public Food getFood() {
        return food;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public Danger getDanger() {
        return danger;
    }

    public Snake getSnake() {
        return snake;
    }

    public int getTimeSinceLastExtra() {
        return timeSinceLastExtra;
    }

    public LabyrinthType getType() {
        return type;
    }

    public Field[][] getFields() {
        return fields;
    }
}
