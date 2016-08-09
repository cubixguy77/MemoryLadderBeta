package recall;

import memorization.GridData;
import memorization.NumberGridAdapter;
import recall.RecallTextWatcher;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public class RecallData extends GridData implements RecallTextWatcher {

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

    public boolean allRowsSubmitted() {
        return rowsRecalled >= numRows;
    }

    public boolean isReviewCell(int position) {
        return reviewCell[getRow(position)][getCol(position)];
    }

    @Override
    public void onTextChanged(int position, String newText) {
        String[][] data = getData();
        NumberGridAdapter adapter = getAdapter();

        System.out.println("Update position: " + position + " - New Text: " + newText);
        String currentText = data[getRow(position)][getCol(position)];

        boolean actualChange =
                (isNullOrEmpty(currentText) && !isNullOrEmpty(newText)) // first character entered
                        || (!isNullOrEmpty(currentText) && !isNullOrEmpty(newText) && !currentText.equals(newText)) // second character entered or erased
                        || (!isNullOrEmpty(currentText) && isNullOrEmpty(newText)); // first character erased

        if (actualChange) {
            data[getRow(position)][getCol(position)] = newText;
            if (newText.length() == numDigitsPerColumn) {
                if (getCol(position) == numCols-1) {
                    adapter.onRowFilled();
                }
                else {
                    adapter.onHighlightPosition(position, true);
                }

            }
            else if (newText.length() == 0 && getCol(position) > 1) {
                adapter.onHighlightPosition(position, false);
            }
        }
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
