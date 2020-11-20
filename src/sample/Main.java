package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    private static double WIDTH = 800;
    private static double HEIGHT = 800;
    private static final double SQUARE_SIDE = 500;
    private static final double CELL_SIZE = 5;
    private static final double RADIUS = SQUARE_SIDE*Math.sqrt(2);
    private static final double CIRCLE_X = WIDTH/2 - RADIUS/2;
    private static final double CIRCLE_Y = HEIGHT/2 - RADIUS/2;
    private static final double SQUARE_X = WIDTH/2 - SQUARE_SIDE/2;
    private static final double SQUARE_Y = HEIGHT/2 - SQUARE_SIDE/2;


    private static List<Dot> dotsInSquare = new ArrayList<>();
    private static List<Dot> dotsInCircle = new ArrayList<>();



    private static final Canvas canvas = new Canvas(WIDTH,HEIGHT);
    private static final GraphicsContext g = canvas.getGraphicsContext2D();

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        Scene scene = new Scene(root,WIDTH,HEIGHT);
        stage.setScene(scene);




        stage.show();


        scene.setOnKeyPressed(press -> {
            if (press.getCode().equals(KeyCode.K)) {
                initGrid();
            }
            if (press.getCode().equals(KeyCode.J)) {
                draw();
            }
            if (press.getCode().equals(KeyCode.SPACE)) {
                startSim();
            }
        });

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            WIDTH = newVal.doubleValue();
            draw();
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            HEIGHT = newVal.doubleValue();
            draw();
        });


    }

    private static void initGrid() {
        g.setLineWidth(1);
        for (int i = 0; i < WIDTH; i += CELL_SIZE) {
            for (int j = 0; j < HEIGHT; j += CELL_SIZE) {
                g.strokeRect(i, j, CELL_SIZE, CELL_SIZE);
            }
        }
        g.setLineWidth(6);
    }


    private static void draw() {
        g.clearRect(0,0,WIDTH,HEIGHT);

        g.setLineWidth(15);


        g.strokeOval(CIRCLE_X,CIRCLE_Y, RADIUS, RADIUS);
        g.setFill(Color.web("#69A297"));
        g.fillOval(CIRCLE_X,CIRCLE_Y, RADIUS, RADIUS);

        g.strokeRect(SQUARE_X,SQUARE_Y,SQUARE_SIDE,SQUARE_SIDE);
        g.setFill(Color.web("#A3C9A8"));
        g.fillRect(SQUARE_X,SQUARE_Y,SQUARE_SIDE,SQUARE_SIDE);


    }

    private static void startSim() {
        class Refresh extends TimerTask {
            public void run() {
                double x = CIRCLE_X + Math.random()*RADIUS;
                double y = CIRCLE_Y + Math.random()*RADIUS;
                Dot dot = new Dot(x,y);


                double centerX = CIRCLE_X + RADIUS;
                double centerY = CIRCLE_Y + RADIUS;

                if  (Math.sqrt(Math.pow(x - centerX,2) + Math.pow(y - centerY,2)) < RADIUS) {
                    dotsInCircle.add(dot);
                    if ((SQUARE_X < x && x < SQUARE_X + SQUARE_SIDE) && (SQUARE_Y < y && y < SQUARE_Y + SQUARE_SIDE)) {
                        dotsInSquare.add(dot);
                    }
                }


                g.setFill(Color.web("black"));
                g.fillOval(x,y,SQUARE_SIDE/50,SQUARE_SIDE/50);

                System.out.println("CIRLCE: " + dotsInCircle.size() + "    SQUARE: " + dotsInSquare.size());

            }
        }

        Timer timer = new Timer();
        TimerTask refresh = new Refresh();
        timer.schedule(refresh, 0, 100);
    }




    public static void main(String[] args) {
        launch(args);
    }
}
