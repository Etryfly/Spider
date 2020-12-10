import java.util.ArrayList;

public class Web {
    private int[][] matrix;
    private int spiderPos;
    private ArrayList<Boolean> flies;

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getSpiderPos() {
        return spiderPos;
    }

    public void setSpiderPos(int spiderPos) {
        this.spiderPos = spiderPos;
    }

    public ArrayList<Boolean> getFlies() {
        return flies;
    }

    public void setFlies(ArrayList<Boolean> flies) {
        this.flies = flies;
    }

    public Web(int[][] matrix, int spiderPos, ArrayList<Boolean> flies) {
        this.matrix = matrix;
        this.spiderPos = spiderPos;
        this.flies = flies;
    }
}
