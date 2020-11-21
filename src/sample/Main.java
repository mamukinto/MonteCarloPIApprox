package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    private static double WIDTH = 800;
    private static double HEIGHT = 800;
    private static final double CELL_SIZE = 5;
    private static final double RADIUS = 700;
    private static final double SQUARE_SIDE = RADIUS;
    private static final double CIRCLE_X = WIDTH / 2 - RADIUS / 2;
    private static final double CIRCLE_Y = HEIGHT / 2 - RADIUS / 2;
    private static final double SQUARE_X = WIDTH / 2 - SQUARE_SIDE / 2;
    private static final double SQUARE_Y = HEIGHT / 2 - SQUARE_SIDE / 2;
    private static final double DOT_RADIUS = SQUARE_SIDE/100;


    private static final int MIN_SPEED = 0;
    private static final int MAX_SPEED = 100;
    private static final int START_SPEED = 50;
    private static int SPEED = 50;



    private static int dotsInSquare = 0;
    private static int dotsInCircle = 0;
    private static double approxPI = 0;

    private static boolean isSimOn = false;
    private static Timer timer = new Timer();


    private static final Canvas canvas = new Canvas(WIDTH, HEIGHT);
    private static final GraphicsContext g = canvas.getGraphicsContext2D();


    private static Label dotsInSquareLabel = Style.styledLabel("Dots in square: " + dotsInSquare);
    private static Label dotsInCircleLabel = Style.styledLabel("Dots in circle: " + dotsInCircle);
    private static Label approximation = Style.styledLabel("Approximation of PI: " + approxPI);
    private static Label realPI = Style.styledLabel("Real PI: " + Math.PI);


    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();


        // side menu
        VBox sideMenu = new VBox();
        sideMenu.setPrefWidth(350);
        sideMenu.setPrefHeight(stage.getHeight());
        sideMenu.setAlignment(Pos.BASELINE_LEFT);
        sideMenu.setSpacing(25);
        sideMenu.setPadding(new Insets(200, 25, 0, 25));

        Button drawButton = new Button("Redraw");
        drawButton.setOnAction(click -> {
            dotsInCircle = 0;
            dotsInSquare = 0;
            draw();
        });
        Style.styleButton(drawButton);

        Button startButton = new Button("Start simulation");
        startButton.setOnAction(click -> {
            if (!isSimOn) {
                startButton.setText("Stop simulation");
                isSimOn = true;
                startSim();
            } else {
                startButton.setText("Start simulation");
                isSimOn = false;
                timer.cancel();
            }

        });
        Style.styleButton(startButton);


        Label speedLabel = Style.styledLabel("Adjust dots spawning speed:");

        Slider speedSlider = new Slider(MIN_SPEED, MAX_SPEED, START_SPEED);
        speedSlider.setMinorTickCount(5);
        speedSlider.setMajorTickUnit(10);
        speedSlider.setBlockIncrement(2);
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setOnMouseClicked(dragDone -> {
            SPEED = 1001 - ((int) speedSlider.getValue() * 10);
            System.out.println(SPEED = 1001 - ((int) speedSlider.getValue() * 10));
        });




        sideMenu.getChildren().add(drawButton);
        sideMenu.getChildren().add(startButton);
        sideMenu.getChildren().add(speedLabel);
        sideMenu.getChildren().add(speedSlider);
        sideMenu.getChildren().addAll(dotsInSquareLabel,dotsInCircleLabel,approximation,realPI);

        root.setRight(sideMenu);
        root.setCenter(canvas);
        Scene scene = new Scene(root, WIDTH + 400, HEIGHT);
        stage.setScene(scene);
        root.setStyle("-fx-background-color: #2b2b2b");


        draw();
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

//        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
//            WIDTH = newVal.doubleValue();
//            draw();
//        });
//
//        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
//            HEIGHT = newVal.doubleValue();
//            draw();
//        });


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
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.setLineWidth(15);


        g.strokeRect(SQUARE_X, SQUARE_Y, SQUARE_SIDE, SQUARE_SIDE);
        g.setFill(Color.web("#A3C9A8"));
        g.fillRect(SQUARE_X, SQUARE_Y, SQUARE_SIDE, SQUARE_SIDE);


        g.strokeOval(CIRCLE_X, CIRCLE_Y, RADIUS, RADIUS);
        g.setFill(Color.web("#69A297"));
        g.fillOval(CIRCLE_X, CIRCLE_Y, RADIUS, RADIUS);

    }

    private static void startSim() {
        class Refresh extends TimerTask {
            public void run() {
                double x = SQUARE_X + Math.random() * SQUARE_SIDE - DOT_RADIUS/2;
                double y = CIRCLE_Y + Math.random() * SQUARE_SIDE - DOT_RADIUS/2;

                double centerX = CIRCLE_X + RADIUS;
                double centerY = CIRCLE_Y + RADIUS;

                if ((SQUARE_X < x && x < SQUARE_X + SQUARE_SIDE) && (SQUARE_Y < y && y < SQUARE_Y + SQUARE_SIDE)) {
                    dotsInSquare++;
                    if (Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) < RADIUS) {
                        dotsInCircle++;
                    }
                }


                g.setFill(Color.web("black"));
                g.fillOval(x, y, DOT_RADIUS, DOT_RADIUS);

                approxPI = (double) 4 * dotsInCircle / dotsInSquare;

                System.out.println("CIRLCE: " + dotsInCircle + "    SQUARE: " + dotsInSquare + "     APPROX: " + approxPI);

                Platform.runLater(() -> {
                    dotsInCircleLabel.setText("Dots in square: " + dotsInSquare);
                    dotsInSquareLabel.setText("Dots in circle: " + dotsInCircle);
                    approximation.setText("Approximation of PI: " + approxPI);
                });

            }
        }

        TimerTask refresh = new Refresh();
        timer = new Timer();
        timer.schedule(refresh, 0, SPEED);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
