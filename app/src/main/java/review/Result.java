package review;

import memorization.GridData;
import recall.RecallData;

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
        numDigitsAttempted = memory.getNumDigitsAttempted();
        calcNumDigitsRecalledCorrectly();
        accuracy = (int) ((double) 100*numDigitsRecalledCorrectly / numDigitsAttempted);
        memTime = 4;
        digitsPerMinute = (int) (numDigitsRecalledCorrectly / ((double) memTime / 60));
    }

    private void calcNumDigitsRecalledCorrectly() {
        String[][] memArray = memory.getData();
        String[][] recArray = recall.getData();
        int digitsPerCell = memory.getNumDigitsPerColumn();

        for (int row=0; row<memory.numRows; row++) {
            for (int col=1; col<memory.numCols; col++) {
                if (memArray[row][col] == null || recArray[row][col] == null)
                    continue;

                for (int c=0; c<digitsPerCell; c++) {
                    if (c >= recArray[row][col].length())
                        continue;

                    if (memArray[row][col].charAt(c) == recArray[row][col].charAt(c))
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
        return this.memTime;
    }

    public int getDigitsPerMinute() {
        return this.digitsPerMinute;
    }



}
