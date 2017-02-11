package recall;

import android.os.Handler;

import memorization.Bus;
import memorization.GridData;
import memorization.GridEvent.Recall;
import memorization.NumberMemoryModel;

public class RecallKeyboardActionPresenter implements Recall.UserEvents, PositionChangeListener {

    private Recall.Grid grid;
    private NumberMemoryModel model;

    public RecallKeyboardActionPresenter(Recall.Grid grid, NumberMemoryModel model) {
        this.grid = grid;
        this.model = model;
        Bus.getBus().subscribe(this);
    }

    @Override
    public void onNextRow() {
        int position = model.getHighlightPosition();
        RecallData recallData = model.getRecallData();

        if (recallData.getRow(position) >= recallData.numRows-1) // final row - don't advance position
            return;

        model.setHighlightPosition(position + (recallData.numCols - recallData.getCol(position)) + 1);

        if (this.isLastRow(model.getHighlightPosition())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    grid.scrollDown();
                }
            }, 200);
        }

        grid.refresh();
    }

    @Override
    public void onSubmitRow() {
        int highlightPosition = model.getHighlightPosition();
        RecallData recallData = model.getRecallData();

        recallData.submitRow(highlightPosition);

        if (recallData.allRowsSubmitted()) {
            grid.refresh();
            dispatchRecallCompleteEvent();
        }
        else {
            this.onNextRow();
        }
    }

    @Override
    public void onSubmitAllRows() {
        model.getRecallData().submitAll();
        grid.refresh();
        dispatchRecallCompleteEvent();
    }

    @Override
    public void onKeyPress(char digit) {
        int highlightPosition = model.getHighlightPosition();
        RecallData recallData = model.getRecallData();

        if (recallData.isReviewCell(highlightPosition))
            return;

        recallData.onKeyPress(digit, highlightPosition, grid.getCursorStart(), grid.getCursorEnd());
        grid.refresh();
    }

    @Override
    public void onBackSpace() {
        int highlightPosition = model.getHighlightPosition();
        RecallData recallData = model.getRecallData();

        if (recallData.isReviewCell(highlightPosition))
            return;

        recallData.onBackSpace(highlightPosition);
        grid.refresh();
    }

    @Override
    public void onPositionChange(int newPosition) {
        model.setHighlightPosition(newPosition);
    }

    private boolean isLastRow(int position) {
        GridData memoryData = model.getMemoryData();
        return (memoryData.getRowNumber(position) >= memoryData.getRowNumber(grid.getLastVisiblePosition()) - 1);
    }

    private void dispatchRecallCompleteEvent() {
        Bus.result.setMemoryData(model.getMemoryData());
        Bus.result.setRecallData(model.getRecallData());
        Bus.result.runCalculations();

        Bus.getBus().onRecallComplete(Bus.result);
    }
}