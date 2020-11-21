package sample;

import javafx.scene.control.Button;

public class Style {
    public static void styleButton(Button button) {
        button.setPrefWidth(150);
        button.setPrefHeight(100);
        button.setStyle("-fx-font-size: 18; -fx-padding: 4 12 4 12; -fx-height: 10;");
    }
}
