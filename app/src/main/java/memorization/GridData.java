package memorization;

import java.util.Random;

public class GridData {

    private String[][] data;
    public int numRows;
    public int numCols;
    public int numDigitsPerColumn;
    public int numDigits;
    public int numDigitsPerRow;
    private final String ROW_MARKER = "ROW_MARKER";

    public GridData(int numDigits, int numDigitsPerColumn) {
        this.numDigits = numDigits;
        this.numDigitsPerColumn = numDigitsPerColumn;
        this.numCols = calculateNumColumns(numDigits, numDigitsPerColumn);
        this.numDigitsPerRow = (numCols - 1) * numDigitsPerColumn;
        this.numRows = numDigits / numDigitsPerRow;
        this.loadData();
    }

    public void loadData() {
        data = new String[numRows][numCols];
        Random rand = new Random();

        int randMax = (int) Math.pow(10, numDigitsPerColumn);

        for (int i=0; i<numRows; i++) {
            for (int j=0; j<numCols; j++) {
                if (j > 0) {
                    data[i][j] = String.format("%0" + numDigitsPerColumn + "d", rand.nextInt(randMax));
                }
                else
                    data[i][j] = ROW_MARKER;
            }
        }
    }

    public String getText(int position) {
        return getValue(position);
    }

    public int getNumCells() {
        return numRows * numCols;
    }

    public int getRowNumber(int position) {
        return this.getRow(position) + 1;
    }

    public boolean isRowMarker(int position) {
        return getValue(position).equals(ROW_MARKER);
    }

    private String getValue(int position) {
        int row = getRow(position);
        int col = getCol(position);
        return data[row][col];
    }

    private int calculateNumColumns(int numDigits, int numDigitsPerColumn) {
        return 6;
    }

    private int getCol(int pos) {
        return pos % (numCols);
    }

    private int getRow(int pos) {
        return pos / (numCols);
    }
}
