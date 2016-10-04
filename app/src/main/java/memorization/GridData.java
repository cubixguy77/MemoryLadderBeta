package memorization;

import java.util.Random;

import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.domain.model.NumberChallenge;

public class GridData {

    private String[][] data;
    public int numRows;
    public int numCols;
    public int numDigitsPerColumn;
    public int numDigits;
    public int numDigitsPerRow;
    private final String ROW_MARKER = "X";
    private NumberGridAdapter adapter;

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
        this.numRows = numDigits / numDigitsPerRow;
        data = new String[numRows][numCols];
    }

    public void setAdapter(NumberGridAdapter adapter) {
        this.adapter = adapter;
    }

    public void loadData() {
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
        return getValue(position) == null ? "" : getValue(position);
    }

    public int getNumCells() {
        return numRows * numCols;
    }

    public int getNumDigitsAttempted() {
        return this.numDigits;
    }

    public int getNumDigitsPerColumn() {
        return this.numDigitsPerColumn;
    }

    public int getRowNumber(int position) {
        return this.getRow(position) + 1;
    }

    public boolean isRowMarker(int position) {
        return getCol(position) == 0;
    }

    private String getValue(int position) {
        int row = getRow(position);
        int col = getCol(position);
        return data[row][col];
    }

    private int calculateNumColumns(int numDigits, int numDigitsPerColumn) {
        return 6;
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

    protected NumberGridAdapter getAdapter() {
        return adapter;
    }
}