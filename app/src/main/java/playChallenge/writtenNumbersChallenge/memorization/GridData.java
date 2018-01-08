package playChallenge.writtenNumbersChallenge.memorization;

import java.io.Serializable;

import selectChallenge.viewChallengeCard.Challenge;
import selectChallenge.viewChallengeCard.NumberChallenge;
import selectChallenge.viewChallengeCard.challengeSettings.digitsource.DigitSource;

public class GridData implements Serializable {

    private char[] data;
    public int numRows;
    public int numCols;
    private int numDigitsPerColumn;
    private int numDigits;
    private int maxValidHighlightPosition;
    private DigitSource digitSource;
    public static char empty = '\u0000';

    public GridData(Challenge challenge) {
        this(
            NumberChallenge.getNumDigitsSetting(challenge).getValue(),
            NumberChallenge.getDigitsPerGroupSetting(challenge).getValue(),
            DigitSource.values()[NumberChallenge.getDigitSourceSetting(challenge).getValue()]
        );
    }

    private GridData(int numDigits, int numDigitsPerColumn, DigitSource digitSource)  {
        this.numDigits = numDigits;
        this.numDigitsPerColumn = numDigitsPerColumn;
        this.digitSource = digitSource;
        this.numCols = calculateNumColumns(numDigits, numDigitsPerColumn);
        int numDigitsPerRow = (numCols - 1) * numDigitsPerColumn;
        this.numRows = numDigits / numDigitsPerRow + (numDigits % numDigitsPerRow == 0 ? 0 : 1);
        maxValidHighlightPosition = (numDigits / numDigitsPerColumn) + numRows - 1 + (numDigits % numDigitsPerColumn == 0 ? 0 : 1);
        data = new char[numDigits];
    }

    public void loadData() {
        if (digitSource == DigitSource.DECIMAL) {
            data = MemoryDataSetFactory.getDecimalNumberData(numDigits);
        }
        else if (digitSource == DigitSource.PI) {
            data = MemoryDataSetFactory.getPiData(numDigits);
        }
        else if (digitSource == DigitSource.BINARY) {
            data = MemoryDataSetFactory.getBinaryData(numDigits);
        }
    }

    public String getText(int position) {
        return getValue(position) == null ? "" : getValue(position);
    }

    public int getNumCells() {
        return numRows * numCols;
    }

    public int getMaxValidHighlightPosition() {
        return this.maxValidHighlightPosition;
    }

    public int getNumDigitsAttempted() {
        return this.numDigits;
    }

    public int getRowNumber(int position) {
        return this.getRow(position) + 1;
    }

    public boolean isRowMarker(int position) {
        return getCol(position) == 0;
    }

    protected int getStartIndexFromPosition(int position) {
        int row = getRow(position);
        int col = getCol(position);
        if (col <= 0)
            return -1;

        return ((col - 1) * numDigitsPerColumn) + ((numCols - 1) * numDigitsPerColumn * row);
    }

    public int numDigitsAtCell(int position) {
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

    public int getCol(int pos) {
        return pos % (numCols);
    }

    public int getRow(int pos) {
        return pos / (numCols);
    }

    public char[] getData() {
        return data;
    }
}
