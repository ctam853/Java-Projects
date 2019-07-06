//In order to help learn course concepts, I worked on the homework with Justin Hu,
// discussed homework topics and issues with Justin Hu, and/or consulted related material that can be found at none.

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;

/**
 * This class represents a CSPaint GUI
 * @author Colin Tam
 * @version 1.0
 */
public class CSPaint extends Application {
    private int count = 0;

    private void draw(Pane pane, TextField t1) {
        t1.setOnKeyPressed(ke -> {
                if (ke.getCode() == KeyCode.ENTER) {
                    try {
                        Color.valueOf(t1.getText());
                    } catch (Exception e) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Invalid Color");
                        alert.setContentText("Invalid Color Entered");
                        alert.show();
                    }
                }
            });

        pane.setOnMouseClicked(me -> {
                Circle c = new Circle(me.getX(), me.getY(), 2.0, Color.valueOf(t1.getText()));
                pane.getChildren().add(c);
            });
        pane.setOnMouseDragged(me -> {
                if (me.getX() > 0 && me.getY() < 449) {
                    Circle c = new Circle(me.getX(), me.getY(), 2.0, Color.valueOf(t1.getText()));
                    pane.getChildren().add(c);
                }
            });
    }

    private void erase(Pane pane) {
        pane.setOnMouseClicked(me -> {
                Circle c = new Circle(10.0, Color.WHITE);
                c.setTranslateX(me.getX());
                c.setTranslateY(me.getY());
                pane.getChildren().add(c);
            });
        pane.setOnMouseDragged(me -> {
                if (me.getX() > 0 && me.getY() < 449) {
                    Circle c = new Circle(me.getX(), me.getY(), 10.0, Color.WHITE);
                    pane.getChildren().add(c);
                }
            });
    }

    private void circle(Pane pane, TextField t1, Label shape) {
        t1.setOnKeyPressed(ke -> {
                if (ke.getCode() == KeyCode.ENTER) {
                    try {
                        Color.valueOf(t1.getText());
                    } catch (Exception e) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Invalid Color");
                        alert.setContentText("Invalid Color Entered");
                        alert.show();
                    }
                }
            });
        pane.setOnMouseClicked(me -> {
                Circle c = new Circle(me.getX(), me.getY(), 15.0, Color.valueOf(t1.getText()));
                pane.getChildren().add(c);
                count++;
                shape.setText("Number of shape: " + count);
            });
    }

    private Label createCoords(VBox base) {
        Label update = new Label();
        base.setOnMouseMoved(e -> {
                String s = "(" + e.getX() + "," + e.getY() + ")";
                update.setText(s);

            });
        base.setOnMouseDragged(e -> {
                String s = "(" + e.getX() + "," + e.getY() + ")";
                update.setText(s);

            });
        return update;
    }


    @Override
    public void start(Stage primaryStage) {
        // creating all the layouts
        VBox base = new VBox();
        HBox bottom = new HBox(20);
        HBox top = new HBox();
        VBox left = new VBox(10);
        Pane pane = new Pane();
        pane.setPrefSize(650.0, 450.0);

        // adding controls to left vbox
        ToggleGroup group = new ToggleGroup();
        RadioButton rbDraw = new RadioButton();
        rbDraw.setText("Draw");
        RadioButton rbErase = new RadioButton();
        rbErase.setText("Erase");
        RadioButton rbCircle = new RadioButton();
        rbCircle.setText("Circle");
        rbDraw.setToggleGroup(group);
        rbErase.setToggleGroup(group);
        rbCircle.setToggleGroup(group);
        TextField t1 = new TextField();
        left.getChildren().addAll(rbDraw, rbErase, rbCircle, t1);
        left.setStyle("-fx-background-color: #DCDCDC");
        pane.setStyle("-fx-background-color: white");
        // adding labels to bottom
        Label coords = createCoords(base);
        Label shape = new Label("Number of shape: 0");
        bottom.getChildren().addAll(coords, shape);



        // radio button and text field handlers
        rbDraw.setOnAction(e -> {
                pane.setOnMouseDragged(null);
                pane.setOnMouseClicked(null);
                if (rbDraw.isSelected()) {
                    draw(pane, t1);
                }
            });
        rbErase.setOnAction(e -> {
                pane.setOnMouseDragged(null);
                pane.setOnMouseClicked(null);
                if (rbErase.isSelected()) {
                    erase(pane);
                }
            });
        rbCircle.setOnAction(e -> {
                pane.setOnMouseClicked(null);
                pane.setOnMouseDragged(null);
                if (rbCircle.isSelected()) {
                    circle(pane, t1, shape);
                }
            });




        // adding all the layouts together
        top.getChildren().add(left);
        top.getChildren().add(pane);
        base.getChildren().add(top);
        base.getChildren().add(bottom);

        Scene scene = new Scene(base);
        primaryStage.setTitle("CSPaint");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This is the main method that is meant to run the program.
     * @param args command line arguments from user
     */
    public static void main(String[] args) {
        launch(args);
    }
}


