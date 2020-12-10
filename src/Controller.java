import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuItem;

import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    @FXML
    public MenuItem startButton;
    @FXML
    public MenuItem stopButton;

    Settings settings;


    Painter painter;

    @FXML
    public Canvas canvas;

    @FXML
    public void StartOnClick() throws InterruptedException {
        startButton.setDisable(true);
        stopButton.setDisable(false);
        painter.start();

    }

    @FXML
    public void StopOnClick() {
        painter.setStop();
        stopButton.setDisable(true);
    }

    @FXML
    public void SettingsOnClick() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SettingsController settingsController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.setScene(scene);
        stage.showAndWait();
        settings = settingsController.settings;
        prepareGraph(settings.getVertex(), settings.getEdges(), settings.getSum(),
                settings.getFlies(), settings.getTime());
        startButton.setDisable(false);
        stopButton.setDisable(true);
    }




    private void prepareGraph(int vertexCount, int edgeCount, int weightSum, int countOfFlies, int time) {
        Graph graph = new Graph(vertexCount, edgeCount, weightSum);
        graph.generateFlies( countOfFlies);
        int radius = 10;
        graph.generateSpider();
        ArrayList<Integer> path = graph.getClosestFlyPath();
        painter = new Painter(canvas, graph, radius);
        painter.initVertex();

        painter.setSpider(time*1000);
        painter.plotGraph(graph);
        for (Integer i : path) {
            System.out.println(i);
        }
    }






}





