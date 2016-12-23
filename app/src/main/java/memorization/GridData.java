package memorization;

import java.io.Serializable;
import java.util.Random;

import speednumbers.mastersofmemory.challenges.domain.model.Challenge;
import speednumbers.mastersofmemory.challenges.domain.model.NumberChallenge;

public class GridData implements Serializable {

    private char[] data;
    public int numRows;
    public int numCols;
    protected int numDigitsPerColumn;
    private int numDigits;
    protected static char empty = '\u0000';

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
        int numDigitsPerRow = (numCols - 1) * numDigitsPerColumn;
        this.numRows = numDigits / numDigitsPerRow + (numDigits % numDigitsPerRow == 0 ? 0 : 1);
        data = new char[numDigits];
    }

    void loadData() {
        Random rand = new Random();

        for (int i=0; i<numDigits; i++) {
            data[i] = Character.forDigit(rand.nextInt(10), 10);
        }
    }

    public String getText(int position) {
        return getValue(position) == null ? "" : getValue(position);
    }

    int getNumCells() {
        return numRows * numCols;
    }

    public int getMaxValidHighlightPosition() {
        return (numDigits / numDigitsPerColumn) + numRows - 1 + (numDigits % numDigitsPerColumn == 0 ? 0 : 1);
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

    protected int getStartIndexFromPosition(int position) {
        int row = getRow(position);
        int col = getCol(position);
        if (col <= 0)
            return -1;

        return ((col - 1) * numDigitsPerColumn) + ((numCols - 1) * numDigitsPerColumn * row);
    }

    protected int numDigitsAtCell(int position) {
        int startIndex = getStartIndexFromPosition(position);
        if (startIndex < 0)
            return 0;

        if (startIndex >= data.length) {
            return 0;
        }

        if (startIndex + (numDigitsPerColumn - 1) >= data.length) {
            return data.length - startIndex;
        }
        else {
            return numDigitsPerColumn;
        }

    }

    protected String getValue(int position) {
        int length = numDigitsAtCell(position);
        int startIndex = getStartIndexFromPosition(position);

        if (length > 0 && data[startIndex] == GridData.empty)
            return "";

        if (length == 1 || (length > 1 && data[startIndex+1] == GridData.empty))
            return new String(new char[] { data[startIndex] });

        if (length == 2 || (length > 2 && data[startIndex+2] == GridData.empty))
            return new String(new char[] { data[startIndex], data[startIndex+1] });

        if (length == 3)
            return new String(new char[] { data[startIndex], data[startIndex+1], data[startIndex+2] });

        return "";
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

    public char[] getData() {
        return data;
    }
}
