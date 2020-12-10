import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.IntStream;

public class Graph {

    private int[][] matrix;
    private ArrayList<Boolean> flies = new ArrayList<>();
    private int spiderPos;

    public boolean isFliesAlive() {
        for (Boolean fly : flies) {
            if (fly) return true;
        }

        return false;
    }


    public int getSpiderPos() {
        return spiderPos;
    }

    public void removeFly(int pos) {
        flies.set(pos, false);
    }

    public boolean checkFly(int pos) {
        return flies.get(pos);
    }

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

    public void generateFlies(int countOfFlies) {
        if (countOfFlies > matrix.length) throw new IllegalArgumentException();
        flies.clear();
        for (int i = 0; i < countOfFlies; i++) {
            flies.add(true);
        }
        for (int i = countOfFlies; i < matrix.length; i++) {
            flies.add(false);
        }

        Collections.shuffle(flies);
    }

    public void setSpiderPos(int pos) {
        spiderPos = pos;
    }

    public void generateSpider() {
        Random random = new Random();
        spiderPos = random.nextInt(matrix.length);
        flies.set(spiderPos, false);
    }

    public ArrayList<Integer> getClosestFlyPath() {

        int[] p = deikstra(spiderPos);
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < flies.size(); i++) {
            if (flies.get(i)) {
                int pathSize = countPath(getShortestPath(p, i));
                if (pathSize < min) {
                    min = pathSize;
                    index = i;
                }
            }
        }



        return getShortestPath(p, index);
    }

    private int countPath(ArrayList<Integer> path) {
        int size = 0;
        for (int i = 0; i < path.size(); i++) {
            size += matrix[spiderPos][path.get(i)];
        }

        return size;
    }

    private ArrayList<Integer> getShortestPath(int[] p, int end) {
        ArrayList<Integer> path = new ArrayList<>();
        int cur = end;
        path.add(cur);

        while (p[cur] != -1) {
            cur = p[cur];
            path.add(cur);
        }

        Collections.reverse(path);

        return path;

    }

    private int[] deikstra(int i) {
        int[] ans = new int[matrix.length];
        int[] pr = new int[matrix.length];
        for (int j = 0; j < matrix.length; j++) {
            ans[j] = Integer.MAX_VALUE;
            pr[j] = -1;
        }
        ans[i] = 0;
        PriorityQueue<Pair<Integer, Integer>> queue = new PriorityQueue<>(matrix.length, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        queue.add(new Pair<Integer, Integer>(i, 0));

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> pair = queue.poll();

            if (ans[pair.getKey()] < pair.getValue()) {
                continue;
            }

            for (Pair<Integer, Integer> vertex : getGraphAsPairs().get(pair.getKey())) {
                int n_dst = pair.getValue() + vertex.getValue();
                int u = vertex.getKey();
                if (n_dst < ans[u]) {
                    ans[u] = n_dst;
                    queue.add(new Pair(u, n_dst));
                    pr[u] = pair.getKey();
                }
            }
        }

        return pr;

    }

    private ArrayList<ArrayList<Pair<Integer, Integer>>> getGraphAsPairs() {
        ArrayList<ArrayList<Pair<Integer, Integer>>> result = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            ArrayList<Pair<Integer, Integer>> arr = new ArrayList<>();
            for (int j =0; j < matrix.length; j++) {

                if (matrix[i][j] != 0)
                    arr.add(new Pair<>(j, matrix[i][j]));
            }
            result.add(arr);

        }

        return result;
    }

    private static void printGraphMatrix(Graph graph) { // for debug
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


    public ArrayList<Boolean> getFlies() {
        return flies;
    }
}
