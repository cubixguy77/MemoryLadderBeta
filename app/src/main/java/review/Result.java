package review;

import memorization.GridData;
import recall.RecallData;

public class Result {

    private final GridData memory;
    private final RecallData recall;
    private int numDigitsRecallAttempted;
    private int numDigitsRecalledCorrectly;
    private int numDigitsTotal;
    private int accuracy;
    private int memTime;
    private int digitsPerMinute;

    public Result(GridData memory, RecallData recall) {
        this.memory = memory;
        this.recall = recall;
        runCalculations();
    }

    private void runCalculations() {
        numDigitsTotal = memory.getNumDigitsAttempted();
        calcNumDigitsRecallAttempted();
        calcNumDigitsRecalledCorrectly();
        accuracy = (int) ((double) 100*numDigitsRecalledCorrectly / numDigitsRecallAttempted);
        memTime = 73;
        digitsPerMinute = (int) (numDigitsRecalledCorrectly / ((double) memTime / 60));
    }

    private void calcNumDigitsRecallAttempted() {
        char[] recArray = recall.getData();

        for (char digit : recArray) {
            if (digit != GridData.empty) {
                numDigitsRecallAttempted++;
            }
        }
    }

    private void calcNumDigitsRecalledCorrectly() {
        char[] memArray = memory.getData();
        char[] recArray = recall.getData();

        for (int i=0; i<memArray.length; i++) {
            if (memArray[i] == recArray[i]) {
                numDigitsRecalledCorrectly++;
            }
        }
    }

    int getNumDigitsRecalledCorrectly() {
        return this.numDigitsRecalledCorrectly;
    }

    public int getNumDigitsTotal() {
        return this.numDigitsTotal;
    }

    int getAccuracy() {
        return this.accuracy;
    }

    int getMemTime() {
        return this.memTime;
    }

    int getDigitsPerMinute() {
        return this.digitsPerMinute;
    }

    public int getNumDigitsRecallAttempted() {
        return numDigitsRecallAttempted;
    }
}
