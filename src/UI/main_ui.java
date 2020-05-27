package UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static model.GameParams.LABYRINTH_HEIGHT;
import static model.GameParams.LABYRINTH_WIDTH;

public class main_ui extends Application {

    static int block_size = 10; //10pixel egy blokk mérete

    int width=LABYRINTH_WIDTH;       //blokkok száma
    int height=LABYRINTH_HEIGHT;

    @Override
    public void start(Stage ps){
        VBox root = new VBox(10);

        Scene scene = new Scene(root);

        ps.setResizable(false);
        ps.setScene(scene);
        ps.setTitle("Snake Game");

        ps.show();

    }
}
