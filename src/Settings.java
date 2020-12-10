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

    public Settings(String str) {
        String[] arr = str.split(" ");
        vertex = Integer.parseInt(arr[0]);
        edges = Integer.parseInt(arr[1]);
        flies = Integer.parseInt(arr[2]);
        sum = Integer.parseInt(arr[3]);
        time = Integer.parseInt(arr[4]);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(vertex);
        sb.append(" ");
        sb.append(edges);
        sb.append(" ");
        sb.append(flies);
        sb.append(" ");
        sb.append(sum);
        sb.append(" ");
        sb.append(time);
        return sb.toString();
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
