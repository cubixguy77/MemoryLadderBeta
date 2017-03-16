package playChallenge.writtenNumbersChallenge.memorization;

import android.os.Bundle;

import playChallenge.Bus;
import playChallenge.SaveInstanceStateListener;
import playChallenge.writtenNumbersChallenge.recall.RecallData;

public class NumberMemoryModel implements SaveInstanceStateListener {

    private GridData memoryData;
    private RecallData recallData;
    private int highlightPosition;

    public NumberMemoryModel() {
        Bus.getBus().subscribe(this);
    }

    public GridData getMemoryData() {
        return memoryData;
    }

    public void setMemoryData(GridData memoryData) {
        this.memoryData = memoryData;
    }

    public RecallData getRecallData() {
        return recallData;
    }

    public void setRecallData(RecallData recallData) {
        this.recallData = recallData;
    }

    public int getHighlightPosition() {
        return highlightPosition;
    }

    public void setHighlightPosition(int highlightPosition) {
        this.highlightPosition = highlightPosition;
    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("NumberGridMemoryAdapter.highlightPosition", getHighlightPosition());
        Bus.memoryData = getMemoryData();
        Bus.recallData = getRecallData();
    }
}
