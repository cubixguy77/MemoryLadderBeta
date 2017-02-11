package recall;

import memorization.Bus;
import memorization.GridData;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public class RecallData extends GridData {

    private boolean[][] reviewCell;
    private int rowsRecalled = 0;

    public RecallData(Challenge challenge) {
        super(challenge);
        reviewCell = new boolean[numRows][numCols];
    }

    void submitRow(int position) {
        int row = getRow(position);

        for (int col=0; col<numCols; col++) {
            reviewCell[row][col] = true;
        }

        rowsRecalled++;
    }

    void submitAll() {
        for (int row=0; row<numRows; row++) {
            for (int col=0; col<numCols; col++) {
                reviewCell[row][col] = true;
            }
        }
    }

    boolean allRowsSubmitted() {
        return rowsRecalled >= numRows;
    }

    public boolean isReviewCell(int position) {
        return reviewCell[getRow(position)][getCol(position)];
    }

    private String getStringAt(int position) {
        return getValue(position);
    }

    public void onKeyPress(char digit, int position, int cursorStart, int cursorEnd) {
        char[] data = getData();
        int startIndex = getStartIndexFromPosition(position) + cursorStart;

        int maxDigits = numDigitsAtCell(position);

        /* cursor is at the end of a filled cell */
        if (cursorStart == cursorEnd && cursorEnd == maxDigits) {
            /* if the next cell is empty, then throw the entered character there */
            if (numDigitsAtCell(position + 1) > 0 && isNullOrEmpty(getStringAt(position+1))) {
                Bus.getBus().onNextCell();
                onKeyPress(digit, position+1, 0, 0);
            }

            return;
        }

        /* erase any highlighted digits, they will be overwritten */
        if (cursorEnd > cursorStart) {
            for (int i=0; i<cursorEnd - cursorStart; i++) {
                data[startIndex + i] = GridData.empty;
            }
        }

        data[startIndex] = digit;

        if (cursorStart+1 == maxDigits) { // Just entered last character of the group
            if (numDigitsAtCell(position + 1) <= 0) {
                /* Row Filled, do not advance cursor */
            }
            else {
                Bus.getBus().onNextCell();
            }
        }
    }

    public void onBackSpace(int position) {
        char[] data = getData();
        String currentText = getStringAt(position);
        int curLength = currentText == null ? 0 : currentText.length();

        if (curLength == 0 && getCol(position) > 1) {
            Bus.getBus().onPrevCell();
            return;
        }
        else if (curLength == 0)
            return;

        int maxDigits = numDigitsAtCell(position);
        int startIndex = getStartIndexFromPosition(position);

        for (int i=maxDigits-1; i>=0; i--) {
            if (data[startIndex + i] != GridData.empty) {
                data[startIndex + i] = GridData.empty;
                break;
            }
        }

        if (curLength == 1 && getCol(position) > 1) {
            Bus.getBus().onPrevCell();
        }
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
