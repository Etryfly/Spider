import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;

public class Painter extends AnimationTimer {
    public Canvas canvas;
    public ArrayList<Point2D> vertex;
    double x;
    double y;
    private ArrayList<Integer> visitedVertexIndex;
    private ArrayList<Pair<Integer, Integer>> visitedEdges;
    int i = 0;
    int steps;
    int t, curT;
    int counter;
    int countOfCatchedFly;
    double dy;
    double dx;
    double dt;
    Graph graph;
    ArrayList<Integer> path;
    int radius;
    GraphicsContext context;
    public Painter(Canvas canvas, Graph graph, int radius) {
        context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        this.canvas = canvas;
        this.graph = graph;
        this.radius = radius;
        countOfCatchedFly = 0;
        calculateNextPath();
    }



    public void setSpider(int t) {
        Point2D point =vertex.get(graph.getSpiderPos());
        visitedVertexIndex.add(graph.getSpiderPos());
        x = point.getX();
        y = point.getY();
        counter = 0;
        curT = 0;
        this.t = t;
    }

    public boolean moveSpider( ) {
        counter++;
        y += dy;
        x += dx;
        t -= dt;
        if (counter >= steps + 1) {
            counter = 0;
            return false;
        }
        return true;
    }


    @Override
    public void handle(long l) {
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        plotGraph(graph);
            if (path.size() <= 1 || !graph.isFliesAlive() || t <= 0) {
                stop();
            } else {
                setSpiderDirection(vertex.get(path.get(i)), vertex.get(path.get(i + 1)), graph.getMatrix()[path.get(i)][path.get(i+1)]);
                if (!moveSpider()) {
                    try {
                        Thread.sleep(1000);
                        t -= 1000;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int pos = path.get(i + 1);
                    if (graph.checkFly(pos)) {
                        countOfCatchedFly++;
                    };
                    graph.removeFly(pos);
                    visitedVertexIndex.add(pos);
                    visitedEdges.add(new Pair(path.get(i), pos));
                    if (path.size() == 2) {
                        graph.setSpiderPos(pos);
                        calculateNextPath();
                    } else {
                        path.remove(0);
                    }
                }
                drawSpider();
            }

    }

    public void initVertex() {
        vertex = getPoint2DVertex(graph);
        visitedVertexIndex = new ArrayList<>();
        visitedEdges = new ArrayList<>();
    }

    public void drawSpider() {
        context.setFill(Color.GREEN);
        context.fillOval(x - radius, y-radius, 2*radius, 2*radius);
    }

    private void calculateNextPath() {
        path = graph.getClosestFlyPath();
    }

    public void setSpiderDirection(Point2D p1, Point2D p2, int weight) {
        double x1  = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();
        steps = (int) (Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) / 3);
        dt = 1.0*weight*1000/steps;

        dy = (y2 - y1) / steps;
        dx = (x2 - x1) / steps;
    }

    public void plotGraph(Graph graph) {
        context.strokeText(String.valueOf(t/1000), 10,10);
        context.strokeText(String.valueOf(countOfCatchedFly), 10,40);
        ArrayList<Boolean> flies = graph.getFlies();


        for (int i = 0; i < vertex.size(); i++) {
            int x = (int) vertex.get(i).getX();
            int y = (int) vertex.get(i).getY();




            if (flies.get(i)) {

                context.setFill(Color.RED);
            } else {
                if (visitedVertexIndex.contains(i)) {
                    context.setFill(Color.BLUE);
                } else {
                    context.setFill(Color.BLACK);
                }
            }


            context.fillOval(x - radius, y-radius, 2*radius, 2*radius);

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
                    Pair edgeForCheck1 = new Pair(i, j);
                    Pair edgeForCheck2 = new Pair(j, i);
                    if (visitedEdges.contains(edgeForCheck1) || visitedEdges.contains(edgeForCheck2)) {
                        context.setStroke(Color.RED);
                    } else {
                        context.setStroke(Color.BLACK);
                    }
                    context.strokeLine(x1,y1,x2,y2);
                    context.strokeText(String.valueOf(edgeWeight),(x1 + x2) / 2,(y1 + y2) / 2 + 15  );


                }

            }
        }

        drawSpider();
    }

    public ArrayList<Point2D> getPoint2DVertex(Graph graph) {
        int vertexCount = graph.getMatrix().length;
        int r = radius * 10;
        ArrayList<Point2D> vertex = new ArrayList<>();
        double phi = 0;
        double dPhi = ((2 * Math.PI / (vertexCount)));

        for (int i = 0; i < vertexCount; i++) {

            int x = (int) (r * Math.cos(phi) + canvas.getWidth() / 2);
            int y = (int) (r * Math.sin(phi) + canvas.getHeight() / 2);
            Point2D v = new Point2D(x, y);
            vertex.add(v);
            phi += dPhi;
        }

        return vertex;
    }




}
