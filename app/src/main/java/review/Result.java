package review;

import memorization.GridData;
import memorization.RecallData;

public class Result {

    private final GridData memory;
    private final RecallData recall;
    private int numDigitsRecalledCorrectly;
    private int numDigitsAttempted;
    private int accuracy;
    private int memTime;
    private int digitsPerMinute;

    public Result(GridData memory, RecallData recall) {
        this.memory = memory;
        this.recall = recall;
        runCalculations();
    }

    private void runCalculations() {
        numDigitsAttempted = memory.getNumCells() - memory.numRows;
        calcNumDigitsRecalledCorrectly();
        accuracy = numDigitsRecalledCorrectly / numDigitsAttempted;
        memTime = 4;
        digitsPerMinute = (int) (numDigitsRecalledCorrectly / ((double) memTime / 60));
    }

    private void calcNumDigitsRecalledCorrectly() {
        String[][] memArray = memory.getData();
        String[][] recArray = recall.getData();

        for (int row=0; row<memory.numRows; row++) {
            for (int col=1; col<memory.numCols; col++) {
                if (memArray[row][col].equals(recArray[row][col])) {
                    numDigitsRecalledCorrectly++;
                }
            }
        }
    }

    public int getNumDigitsRecalledCorrectly() {
        return this.numDigitsRecalledCorrectly;
    }

    public int getNumDigitsAttempted() {
        return this.numDigitsAttempted;
    }

    public int getAccuracy() {
        return this.accuracy;
    }

    public int getMemTime() {
        return this.getMemTime();
    }

    public int getDigitsPerMinute() {
        return this.digitsPerMinute;
    }



}
