import java.util.ArrayList;

public class Web {
    private int[][] matrix;
    private int spiderPos;
    private ArrayList<Boolean> flies;


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(matrix.length);
        sb.append(" ");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                sb.append(matrix[i][j]);
                sb.append(" ");
            }
        }

        sb.append(spiderPos);
        sb.append(" ");
        sb.append(flies.size());
        sb.append(" ");
        for (int i = 0; i < flies.size(); i++) {
           sb.append(flies.get(i));
           sb.append(" ");
        }

        return sb.toString();
    }

    public Web(String str) {
        String[] arr = str.split(" ");
        int k = 0;
        int len = Integer.parseInt(arr[k]);
        k++;
        matrix = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                matrix[i][j] = Integer.parseInt(arr[k]);
                k++;
            }

        }

        spiderPos = Integer.parseInt(arr[k]);
        k++;

        int fliesLen = Integer.parseInt(arr[k]);
        k++;
        flies = new ArrayList<>();
        for (int i = 0; i < fliesLen; i++) {
            flies.add(Boolean.valueOf(arr[k]));
            k++;
        }


    }

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
