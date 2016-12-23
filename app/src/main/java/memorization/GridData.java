package memorization;

import java.io.Serializable;
import java.util.Random;

import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.domain.model.NumberChallenge;

public class GridData implements Serializable {

    private String[][] data;
    public int numRows;
    public int numCols;
    protected int numDigitsPerColumn;
    private int numDigits;
    private int numDigitsPerRow;
    private final String ROW_MARKER = "X";

    public GridData(Challenge challenge) {
        this(
            NumberChallenge.getNumDigitsSetting(challenge).getValue(),
            NumberChallenge.getDigitsPerGroupSetting(challenge).getValue()
        );
    }

    public GridData(int numDigits, int numDigitsPerColumn)  {
        this.numDigits = numDigits;
        this.numDigitsPerColumn = numDigitsPerColumn;
        this.numCols = calculateNumColumns(numDigits, numDigitsPerColumn);
        this.numDigitsPerRow = (numCols - 1) * numDigitsPerColumn;
        this.numRows = numDigits / numDigitsPerRow + (numDigits % numDigitsPerRow == 0 ? 0 : 1);
        data = new String[numRows][numCols];
    }

    void loadData() {
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

    protected String getText(int position) {
        return getValue(position) == null ? "" : getValue(position);
    }

    int getNumCells() {
        return numRows * numCols;
    }

    public int getNumDigitsAttempted() {
        return this.numDigits;
    }

    public int getNumDigitsPerColumn() {
        return this.numDigitsPerColumn;
    }

    int getRowNumber(int position) {
        return this.getRow(position) + 1;
    }

    boolean isRowMarker(int position) {
        return getCol(position) == 0;
    }

    private String getValue(int position) {
        int row = getRow(position);
        int col = getCol(position);
        return data[row][col];
    }

    private int leastOf(int a, int b) {
        return a < b ? a : b;
    }

    private int calculateNumColumns(int numDigits, int numDigitsPerColumn) {
        int numChunks = numDigits / numDigitsPerColumn + (numDigits % numDigitsPerColumn == 0 ? 0 : 1);
        if (numDigitsPerColumn == 1) {
            return 1 + leastOf(numChunks, 5);
        }
        if (numDigitsPerColumn == 2) {
            return 1 + leastOf(numChunks, 5);
        }
        if (numDigitsPerColumn == 3) {
            return 1 + leastOf(numChunks, 5);
        }

        return -1;
    }

    protected int getCol(int pos) {
        return pos % (numCols);
    }

    protected int getRow(int pos) {
        return pos / (numCols);
    }

    public String[][] getData() {
        return data;
    }
}
