import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

public class Graph {

    private int[][] matrix;
    public Graph(int vertexCount, int edgeCount, int weightSum) {
        int possibleEdgeCount = vertexCount * (vertexCount-1) / 2;
        if (possibleEdgeCount < edgeCount) throw new IllegalArgumentException("edgeCount > possible edge count");
        matrix = new int[vertexCount][vertexCount];
        Random random = new Random();
        int maxEdgeWeight = weightSum / edgeCount;
        ArrayList<Integer> edges = new ArrayList<>();

        for (int i = 0; i < edgeCount; i++) {
            edges.add(random.nextInt(maxEdgeWeight) + 1);
        }
        while (edges.stream().mapToInt(Integer::intValue).sum() < weightSum) {
            int i = random.nextInt(edges.size() - 1);
            int edge = edges.get(i);
            edges.set(i, edge + 1);
        }


        for (int i = edgeCount; i < possibleEdgeCount; i++) {
            edges.add(0);
        }
        Collections.shuffle(edges);
        int k = 0;
        for (int i = 0; i < vertexCount; i++) {
            for (int j = i + 1 ; j < vertexCount; j++) {
                matrix[i][j] = edges.get(k);
                matrix[j][i] = edges.get(k);
                k++;
            }
        }

        printGraphMatrix(this);




    }

    public static void main(String[] args) {
        Graph graph = new Graph(4, 6, 30);
        Graph graph1 = new Graph(4, 3, 30);
        printGraphMatrix(graph);
        printGraphMatrix(graph1);
    }


    private static void printGraphMatrix(Graph graph) {
        for (int i = 0; i < graph.matrix.length; i++) {
            for (int j = 0; j < graph.matrix[i].length; j++) {
                System.out.print(graph.matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int[][] getMatrix() {
        return matrix;
    }
}
