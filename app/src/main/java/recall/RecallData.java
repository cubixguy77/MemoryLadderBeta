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
        return getData()[getRow(position)][getCol(position)];
    }

    public void onKeyPress(int digit, int position, int cursorStart, int cursorEnd) {
        String[][] data = getData();
        String currentText = getStringAt(position);
        String newChar = Integer.toString(digit);

        if (cursorStart == cursorEnd && cursorEnd == numDigitsPerColumn)
            return;

        data[getRow(position)][getCol(position)] = currentText == null ? newChar : currentText.substring(0, cursorStart) + newChar + currentText.substring(cursorEnd);

        if (getStringAt(position).length() == numDigitsPerColumn) { // Just entered last character of the group
            if (getCol(position) == numCols-1) {
                Bus.getBus().onRowFilled();
            }
            else {
                Bus.getBus().onNextRecallCell();
            }
        }
    }

    public void onBackSpace(int position) {
        String[][] data = getData();
        String currentText = getStringAt(position);
        int curLength = currentText == null ? 0 : currentText.length();

        if (curLength == 0 && getCol(position) > 1) {
            Bus.getBus().onPrevRecallCell();
            return;
        }
        else if (curLength == 0)
            return;

        data[getRow(position)][getCol(position)] = currentText.substring(0, curLength-1);

        if (curLength == 1 && getCol(position) > 1) {
            Bus.getBus().onPrevRecallCell();
        }
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
