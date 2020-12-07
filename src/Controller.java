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

import java.util.ArrayList;

public class Controller {

    @FXML
    public AnchorPane pane;

    @FXML
    public void StartOnClick() {
        Graph graph = new Graph(5, 6, 30);
        plotGraph(graph, 100);
    }

    @FXML
    public void initialize() {


    }

    public void plotGraph(Graph graph, int radius) {
        int vertexCount = graph.getMatrix().length;

        double phi = 0;
        double dPhi = ((2*Math.PI / (vertexCount)));


        Group root = new Group();

        pane.getChildren().add(root);
        ArrayList<Point2D> vertex = new ArrayList<>();

        for (int i = 0; i < vertexCount; i++) {
            Circle circle = new Circle();
            int x = (int) (radius*Math.cos(phi) + pane.getWidth() / 2) ;
            int y = (int) (radius*Math.sin(phi) + pane.getHeight() / 2);
            Point2D v = new Point2D(x, y);
            vertex.add(v);
            phi += dPhi;
            circle.setCenterX(x);
            circle.setCenterY(y);
            circle.setStroke(Color.BLACK);
            circle.setRadius(radius / 8);
            root.getChildren().add(circle);
        }
        for (int i = 0; i < vertexCount; i++) {
            for (int j = i + 1; j < vertexCount; j++) {
                int edgeWeight = graph.getMatrix()[i][j];
                if (edgeWeight != 0) {
                    Point2D first = vertex.get(i);
                    Point2D second = vertex.get(j);
                    double x1 = first.getX();
                    double y1 = first.getY();
                    double x2 = second.getX();
                    double y2 = second.getY();

                    Line line = new Line(x1, y1, x2, y2);

                    Text text = new Text();
                    text.setText(String.valueOf(edgeWeight));
                    text.setX((x1 + x2) / 2);
                    text.setY((y1 + y2) / 2 + 15 );
                    root.getChildren().add(line);
                    root.getChildren().add(text);
                }

            }
        }
    }




}
