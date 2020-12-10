public class Settings {

   private int vertex;
   private int edges;
   private int flies;
   private int sum;
   private int time;

    public Settings(int vertex, int edges, int flies, int sum, int time) {
        this.vertex = vertex;
        this.edges = edges;
        this.flies = flies;
        this.sum = sum;
        this.time = time;
    }

    public int getVertex() {
        return vertex;
    }

    public void setVertex(int vertex) {
        this.vertex = vertex;
    }

    public int getEdges() {
        return edges;
    }

    public void setEdges(int edges) {
        this.edges = edges;
    }

    public int getFlies() {
        return flies;
    }

    public void setFlies(int flies) {
        this.flies = flies;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
