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
    double curTime = 0;
    double x;
    double y;
    int i = 0;
    int steps;
    int counter;
    double dy;
    double dx;
    Graph graph;
    ArrayList<Integer> path;
    Group root;
    public Painter(AnchorPane p, ArrayList<Point2D> vertex, Graph graph) {
        pane = p;
        this.graph = graph;
        this.vertex = vertex;
        root = new Group();
        calculateNextPath();
        pane.getChildren().add(root);
    }



    public void setSpider() {
        Point2D point =vertex.get(graph.getSpiderPos());
        x = point.getX();
        y = point.getY();
        counter = 0;
    }

    public boolean moveSpider( int t, int radius) {
        counter++;
        y += dy;
        x += dx;
        if (counter >= steps) {
            counter = 0;
            return false;
        }
        return true;
    }


    @Override
    public void handle(long l) {
            if (path.size() == 1) { // TODO fix
                stop();
            } else {
                int t = 2000;
                setSpiderDirection(vertex.get(path.get(i)), vertex.get(path.get(i + 1)), t);
                if (!moveSpider(t, 20)) {
                    int pos = path.get(i + 1);
                    graph.removeFly(pos);
                    if (path.size() == 2) {
                        graph.setSpiderPos(pos);
                        calculateNextPath();
                    } else {
                        path.remove(0);
                    }
                }
                drawSpider(20);
            }

    }

    private void drawSpider(int radius) {
        Circle circle = new Circle();
        circle.setRadius(radius / 8);
        circle.setCenterY(y);
        circle.setCenterX(x);
        circle.setFill(Color.BLACK);

        root.getChildren().add(circle);

    }

    private void calculateNextPath() {
        path = graph.getClosestFlyPath();
    }

    public void setSpiderDirection(Point2D p1, Point2D p2, int t) {
        double x1  = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();
        steps = (int) (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) / 3);


        dy = (y2 - y1) / steps;
        dx = (x2 - x1) / steps;
    }

    public void plotGraph(Graph graph, int radius, ArrayList<Point2D> vertex) {

        int spiderIndex = graph.getSpiderPos();
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
            if (i == spiderIndex) circle.setFill(Color.GREEN);
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

                    //DEBUG

                    Text debug = new Text();
                    debug.setText(String.valueOf(i));
                    debug.setX(x1 + 10);
                    debug.setY(y1 + 10);
                    root.getChildren().add(debug);
                    // /DEBUG

                    root.getChildren().add(line);
                    root.getChildren().add(text);
                }

            }
        }
    }




}
