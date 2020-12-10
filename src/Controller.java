import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuItem;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
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
        settingsController.load(settings);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.setScene(scene);

        stage.showAndWait();
        settings = settingsController.settings;

        prepareGraph(settings);
        startButton.setDisable(false);
        stopButton.setDisable(true);
    }




    private void prepareGraph(Settings settings) {
        Graph graph = new Graph( settings.getVertex(), settings.getEdges(), settings.getSum());
        graph.generateFlies(  settings.getFlies());
        int radius = 10;
        graph.generateSpider();
        ArrayList<Integer> path = graph.getClosestFlyPath();
        painter = new Painter(canvas, graph, radius);
        painter.initVertex();

        painter.setSpider(settings.getTime()*1000);
        painter.plotGraph(graph);
    }


    public void LoadSettingsOnClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                settings = new Settings(reader.readLine());
                prepareGraph(settings);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void SaveSettingsOnClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(settings.toString());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void SaveWebOnClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(painter.getGraph().getWeb().toString());
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void LoadWebOnClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                Web web = new Web(reader.readLine());
                Graph graph = new Graph(web);
                int radius = 10;
                painter = new Painter(canvas, graph, radius);
                painter.initVertex();
                painter.setSpider(1000*1000);
                painter.plotGraph(graph);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}





