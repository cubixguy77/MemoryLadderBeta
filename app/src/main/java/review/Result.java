package review;

import memorization.GridData;
import recall.RecallData;

public class Result {

    private int challengeKey;
    private GridData memory;
    private RecallData recall;
    private int numDigitsRecallAttempted;
    private int numDigitsRecalledCorrectly;
    private int numDigitsTotal;
    private int accuracy;
    private int memTime;
    private int digitsPerMinute;

    public Result() {
    }

    public Result(GridData memory, RecallData recall) {
        this.memory = memory;
        this.recall = recall;
        runCalculations();
    }

    public void runCalculations() {
        numDigitsTotal = memory.getNumDigitsAttempted();
        calcNumDigitsRecallAttempted();
        calcNumDigitsRecalledCorrectly();
        accuracy = (int) ((double) 100*numDigitsRecalledCorrectly / numDigitsRecallAttempted);
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

    public void setMemoryData(GridData memory) {
        this.memory = memory;
    }

    public void setRecallData(RecallData recall) {
        this.recall = recall;
    }

    public int getNumDigitsRecalledCorrectly() {
        return this.numDigitsRecalledCorrectly;
    }

    public int getNumDigitsTotal() {
        return this.numDigitsTotal;
    }

    int getAccuracy() {
        return this.accuracy;
    }

    /* Sets the memorization time used by the user, in seconds */
    public void setMemTime(int memTime) { this.memTime = memTime; }

    public int getMemTime() {
        return this.memTime;
    }

    int getDigitsPerMinute() {
        return this.digitsPerMinute;
    }

    public int getNumDigitsRecallAttempted() {
        return numDigitsRecallAttempted;
    }

    public int getChallengeKey() {
        return challengeKey;
    }

    public void setChallengeKey(int challengeKey) {
        this.challengeKey = challengeKey;
    }
}
