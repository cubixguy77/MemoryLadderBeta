package recall;

import memorization.Bus;
import memorization.GridData;
import memorization.GridEvent;
import memorization.GridEvent.Keyboard;
import memorization.NumberMemoryModel;

public class RecallKeyboardActionPresenter implements Keyboard.UserKeyboardActions, PositionChangeListener {

    private GridEvent.Grid grid;
    private NumberMemoryModel model;

    public RecallKeyboardActionPresenter(GridEvent.Grid grid, NumberMemoryModel model) {
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

        grid.refresh();

        if (this.isLastRow(model.getHighlightPosition())) {
            grid.scrollDown();
        }
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
            grid.refresh();
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

        recallData.onKeyPress(digit, highlightPosition, grid.getCursorStart(highlightPosition), grid.getCursorEnd(highlightPosition));
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
        if (newPosition != model.getHighlightPosition()) {
            model.setHighlightPosition(newPosition);
            grid.refresh();
        }
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