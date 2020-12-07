import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

public class Controller {

    @FXML
    public AnchorPane pane;

    @FXML
    public void StartOnClick() throws InterruptedException {
        Graph graph = new Graph(5, 6, 30);
        graph.generateFlies(3);
        ArrayList<Point2D> vertex = getPoint2DVertex(graph, 100);
        Painter painter = new Painter(pane, vertex);
        painter.plotGraph(graph, 100, vertex);
        painter.start();

    }

    @FXML
    public void StopOnClick() {

    }

    @FXML
    public void SettingsOnClick() {

    }

    @FXML
    public void initialize() {


    }




    public ArrayList<Point2D> getPoint2DVertex(Graph graph, int radius) {
        int vertexCount = graph.getMatrix().length;
        ArrayList<Point2D> vertex = new ArrayList<>();
        double phi = 0;
        double dPhi = ((2 * Math.PI / (vertexCount)));

        for (int i = 0; i < vertexCount; i++) {

            int x = (int) (radius * Math.cos(phi) + pane.getWidth() / 2);
            int y = (int) (radius * Math.sin(phi) + pane.getHeight() / 2);
            Point2D v = new Point2D(x, y);
            vertex.add(v);
            phi += dPhi;
        }

        return vertex;
    }


}





