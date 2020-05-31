//package model.util;
//
//import javafx.geometry.Point2D;
//import model.GameParams;
//import model.map.Field;
//import model.map.Labyrinth;
//import model.map.things.snake.Snake;
//import model.map.things.snake.SnakeBodyPart;
//import model.map.things.snake.SnakeHead;
//
//import java.util.LinkedList;
//import java.util.List;
//
///** Provides interface to build the standard labyrinths in the game
// * */
//public abstract class LabyrinthFactory {
//    /** Create a labyrinth without any walls, so the snake can go outside the screen and come back on the other side.
//     * */
//    public static Labyrinth createWallessLabyrinth(){
//        final int SNAKE_BODY_LENGTH = 5;
//        final int SNAKE_START_X = 30;
//        final int SNAKE_START_Y = 30;
//
//        Field[][] fields = makeFields(GameParams.LABYRINTH_WIDTH, GameParams.LABYRINTH_HEIGHT);
//        SnakeHead snakeHead = new SnakeHead();
//        snakeHead.setDirection(Directions.RIGHT);
//        List<SnakeBodyPart> sbp = new LinkedList<>();
//        sbp.add(new SnakeBodyPart(snakeHead));
//        for(int i = 1; i < SNAKE_BODY_LENGTH; i++){
//            sbp.add( new SnakeBodyPart( sbp.get(i-1) ) );
//        }
//
//        snakeHead.setField(fields[SNAKE_START_X][SNAKE_START_Y]);
//        fields[SNAKE_START_X][SNAKE_START_Y].accept(snakeHead);
//        for(int i = 0; i < sbp.size(); i++){
//            sbp.get(i).setField(fields[SNAKE_START_X+i+1][SNAKE_START_Y]);
//            fields[SNAKE_START_X+i+1][SNAKE_START_Y].accept(sbp.get(i));
//        }
//
//        Snake snake = new Snake(snakeHead, sbp);
//        Labyrinth labyrinth = new Labyrinth(snake);
//
//        return labyrinth;
//    }
//
//    /** Generates and joins the empty fields of the labyrinth
//     * */
//    private static Field[][] makeFields(int labyrinthWidht, int labyrinthHeight){
//        //Create the fields
//        Field[][] fields = new Field[labyrinthWidht][labyrinthHeight];
//        for(int i = 0; i < labyrinthWidht; i++){
//            for(int j = 0; j < labyrinthHeight; j++){
//                fields[i][j] = new Field(new Point(i,j));
//            }
//        }
//        //Connect the fields as they are neighbor to each other
//        for(int i = 0; i < labyrinthWidht; i++){
//            for(int j = 0; j < labyrinthHeight; j++){
//                fields[i][j].setNeighbor(Directions.UP, fields[i][(j-1 + labyrinthHeight)%labyrinthHeight]);
//                fields[i][j].setNeighbor(Directions.DOWN, fields[i][(j+1)%labyrinthHeight]);
//                fields[i][j].setNeighbor(Directions.LEFT, fields[(i-1 + labyrinthWidht)%labyrinthWidht][j]);
//                fields[i][j].setNeighbor(Directions.RIGHT, fields[(i+1)%labyrinthWidht][j]);
//            }
//        }
//
//        return  fields;
//    }
//}
