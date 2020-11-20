package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private static double WIDTH = 800;
    private static double HEIGHT = 800;
    private static final double SQUARE_SIDE = 400;
    private static double RADIUS = SQUARE_SIDE*Math.sqrt(2);


    private static final Canvas canvas = new Canvas(WIDTH,HEIGHT);
    private static final GraphicsContext g = canvas.getGraphicsContext2D();

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        Scene scene = new Scene(root,WIDTH,HEIGHT);
        stage.setScene(scene);

        g.setLineWidth(6);


        draw();
        stage.show();



        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            WIDTH = newVal.doubleValue();
            draw();
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            HEIGHT = newVal.doubleValue();
            draw();
        });


    }


    private static void draw() {
        g.clearRect(0,0,WIDTH,HEIGHT);


        g.strokeRect(WIDTH/2 - SQUARE_SIDE/2,HEIGHT/2 - SQUARE_SIDE/2,SQUARE_SIDE,SQUARE_SIDE);
        g.strokeOval(WIDTH/2 - RADIUS/2,HEIGHT/2 - RADIUS/2, RADIUS, RADIUS);
    }




    public static void main(String[] args) {
        launch(args);
    }
}
