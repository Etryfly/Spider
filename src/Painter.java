import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

public class Painter extends AnimationTimer {
    public AnchorPane pane;
    public ArrayList<Point2D> vertex;
    public Painter(AnchorPane p, ArrayList<Point2D> vertex) {
        pane = p;
        this.vertex = vertex;
    }

    public void moveSpider(Point2D p1, Point2D p2, int t, int radius) {
        // double dx = 10;
        int step = 3;
        double x1  = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();
        int count = (int) (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(x1 - x2, 2)) / step);
        double dt = t*1.0 / count;
        // double k = (y2 - y1)/(x2 - x1);
        //double b = y1 - x1*(y2 - y1)/(x2 - x1);
        //System.out.println(k + " " + b);
        double dy = (y2 - y1) / count;
        double dx = (x2 - x1) / count;
        double[] x = new double[]{x1};
        double[] y = new double[]{y1};
        Group root = new Group();
        pane.getChildren().add(root);
        Timeline timeline = new Timeline (
                new KeyFrame(
                        Duration.millis(dt),
                        ae -> {

                            Circle circle = new Circle();
                            circle.setRadius(radius / 8);
                            circle.setCenterY(y[0]);
                            circle.setCenterX(x[0]);
                            circle.setFill(Color.GREENYELLOW);
                            y[0] += dy;
                            x[0] += dx;
                            root.getChildren().add(circle);
                        }
                )
        );

        timeline.setCycleCount(count); //Ограничим число повторений
        timeline.play(); //Запускаем
    }

    public void plotGraph(Graph graph, int radius, ArrayList<Point2D> vertex) {


        ArrayList<Boolean> flies = graph.getFlies();
        Group root = new Group();

        pane.getChildren().add(root);


        for (int i = 0; i < vertex.size(); i++) {
            Circle circle = new Circle();
            int x = (int) vertex.get(i).getX();
            int y = (int) vertex.get(i).getY();

            circle.setCenterX(x);
            circle.setCenterY(y);
            if (flies.get(i)) {
                circle.setFill(Color.RED);
            } else {
                circle.setFill(Color.BLACK);
            }
            circle.setRadius(radius / 8);

            root.getChildren().add(circle);
        }


        for (int i = 0; i < vertex.size(); i++) {
            for (int j = i + 1; j < vertex.size(); j++) {
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
                    text.setY((y1 + y2) / 2 + 15);
                    root.getChildren().add(line);
                    root.getChildren().add(text);
                }

            }
        }
    }



    @Override
    public void handle(long l) {
        for (int i = 0; i < vertex.size() - 1; i++) {
            int t = 2000;
            moveSpider(vertex.get(i), vertex.get(i + 1), t, 100);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
