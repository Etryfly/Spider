import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

public class Controller {
    Painter painter;

    @FXML
    public Canvas canvas;

    @FXML
    public void StartOnClick() throws InterruptedException {
        painter.start();

    }

    @FXML
    public void StopOnClick() {
        painter.setStop();
    }

    @FXML
    public void SettingsOnClick() {
        Graph graph = new Graph(5, 6, 30);
        graph.generateFlies(3);
        int radius = 10;
        graph.generateSpider();
        ArrayList<Integer> path = graph.getClosestFlyPath();
        painter = new Painter(canvas, graph, radius);
        painter.initVertex();

        painter.setSpider(20*1000);
        painter.plotGraph(graph);
        for (Integer i : path) {
            System.out.println(i);
        }

    }









}





