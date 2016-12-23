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

    public RecallData(int numDigits, int numDigitsPerColumn) {
        super(numDigits, numDigitsPerColumn);
        reviewCell = new boolean[numRows][numCols];
    }

    public void onSubmitRow(int row) {
        for (int col=0; col<numCols; col++) {
            reviewCell[row][col] = true;
        }

        rowsRecalled++;
    }

    public void submitAll() {
        for (int row=0; row<numRows; row++) {
            for (int col=0; col<numCols; col++) {
                reviewCell[row][col] = true;
            }
        }
    }

    public boolean allRowsSubmitted() {
        return rowsRecalled >= numRows;
    }

    public boolean isReviewCell(int position) {
        return reviewCell[getRow(position)][getCol(position)];
    }

    private String getStringAt(int position) {
        return getValue(position);
    }

    public void onKeyPress(int digit, int position, int cursorStart, int cursorEnd) {
        char[] data = getData();
        char newChar = Character.forDigit(digit, 10);
        int startIndex = getStartIndexFromPosition(position) + cursorStart;

        int maxDigits = numDigitsAtCell(position);

        /* cursor is at the end of a filled cell */
        if (cursorStart == cursorEnd && cursorEnd == maxDigits)
            return;

        /* erase any highlighted digits, they will be overwritten */
        if (cursorEnd > cursorStart) {
            for (int i=0; i<cursorEnd - cursorStart; i++) {
                data[startIndex + i] = GridData.empty;
            }
        }

        data[startIndex] = newChar; //(currentText == null || currentText.length() == 0) ? newChar : currentText.substring(0, cursorStart) + Character.toString(newChar) + currentText.substring(cursorEnd);

        if (cursorStart+1 == maxDigits) { // Just entered last character of the group
            if (numDigitsAtCell(position + 1) <= 0) {
                Bus.getBus().onRowFilled();
            }
            else {
                Bus.getBus().onNextRecallCell();
            }
        }
    }

    public void onBackSpace(int position) {
        char[] data = getData();
        String currentText = getStringAt(position);
        int curLength = currentText == null ? 0 : currentText.length();

        if (curLength == 0 && getCol(position) > 1) {
            Bus.getBus().onPrevRecallCell();
            return;
        }
        else if (curLength == 0)
            return;

        int maxDigits = numDigitsAtCell(position);
        int startIndex = getStartIndexFromPosition(position);

        for (int i=maxDigits; i>=0; i--) {
            if (data[startIndex + i] != GridData.empty) {
                data[startIndex + i] = GridData.empty;
                break;
            }
        }

        if (curLength == 1 && getCol(position) > 1) {
            Bus.getBus().onPrevRecallCell();
        }
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
