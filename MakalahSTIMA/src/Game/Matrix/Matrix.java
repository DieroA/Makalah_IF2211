package Game.Matrix;

import java.util.ArrayList;

public class Matrix<T> {
    /*
     *  ATTRIBUTES
     */
    private ArrayList<ArrayList<T>> data;
    private final int nRow;
    private final int nCol;

    /*
     *  METHODS
     */
    public Matrix(int nRow, int nCol) {
        this.nRow = nRow;
        this.nCol = nCol;

        this.data = new ArrayList<>();
        for (int i = 0; i < this.nRow; i++) {
            ArrayList<T> temp = new ArrayList<>();
            for (int j = 0; j < this.nCol; j++)
                temp.add(null);
            data.add(temp);
        }
    }

    // Getter & Setter
    public int getRow() { return this.nRow; }
    public int getCol() { return this.nCol; }
    public ArrayList<ArrayList<T>> getData() { return this.data; }
    public T getEl(int row, int col) { return data.get(row).get(col); }
    public void setEl(int row, int col, T val) { data.get(row).set(col, val); }
    public void removeEl(int row, int col) { setEl(row, col, null); }
}