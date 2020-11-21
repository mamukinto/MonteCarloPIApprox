package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Style {
    public static void styleButton(Button button) {
        button.setStyle("-fx-font-size: 18; -fx-padding: 4 12 4 12; -fx-height: 10px;");
    }
    public static Label styledLabel(String s) {
        Label label = new Label(s);
        label.setAlignment(Pos.BASELINE_LEFT);
        label.setTextAlignment(TextAlignment.LEFT);
        label.setTextFill(Color.web("#9b9b9b"));
        label.setStyle("-fx-font-size: 16px;");
        return label;
    }
}
