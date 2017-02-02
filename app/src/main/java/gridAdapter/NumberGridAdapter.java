package gridAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Locale;

import memorization.Bus;
import memorization.GameState;
import memorization.GridData;
import memorization.GridEvent;
import memorization.MemoryCell;
import memorization.NumberGridView;
import memorization.SaveInstanceStateListener;
import recall.PositionChangeListener;
import recall.RecallCell;
import recall.RecallData;
import review.ReviewCell;

public class NumberGridAdapter extends BaseAdapter implements GridEvent.Memory.UserEvents, GridEvent.Recall.UserEvents, GridEvent.Recall.ViewEvents, PositionChangeListener, SaveInstanceStateListener {

    private Context context;
    private GridData memoryData;
    private RecallData recallData;
    private int highlightPosition = 1;
    private NumberGridView gridView;
    private NumberGridPresenter presenter;

    public NumberGridAdapter(Context context)
    {
        this.context = context;
        this.presenter = new NumberGridPresenter(this);

        Bus.getBus().subscribe(this);
    }

    public void setGridView(NumberGridView gridView) {
        this.gridView = gridView;
    }

    @Override
    public int getCount() {
        return memoryData == null ? 0 : memoryData.getNumCells();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private void scrollMemorization() {
        if (memoryData.getRowNumber(highlightPosition) >= memoryData.getRowNumber(gridView.getLastVisiblePosition()) - 1) {
            gridView.scrollGrid();
        }
    }

    private void scrollRecall() {
        if (memoryData.getRowNumber(highlightPosition) >= memoryData.getRowNumber(gridView.getLastVisiblePosition())) {
            gridView.scrollGrid();
        }
    }




    private void moveHighlightPositionToNextRow(int position) {
        if (recallData.getRow(position) >= recallData.numRows-1) // final row - don't advance position
            return;

        this.highlightPosition += (recallData.numCols - recallData.getCol(position)) + 1;
        scrollRecall();
        notifyDataSetChanged();
    }

    private void onHighlightPrev() {
        highlightPosition--;

        if (highlightPosition < 1) {
            highlightPosition = 1;
            Bus.getBus().onDisablePrev();
        }
        else if (memoryData.isRowMarker(highlightPosition)) {
            highlightPosition--;
        }
        else if (highlightPosition == memoryData.getNumCells() - 2) {
            Bus.getBus().onEnableNext();
        }

        notifyDataSetChanged();
    }

    private void onHighlightNext() {
        highlightPosition++;
        if (highlightPosition >= memoryData.getMaxValidHighlightPosition()) {
            highlightPosition = memoryData.getMaxValidHighlightPosition();
            Bus.getBus().onDisableNext();
        }
        else if (memoryData.isRowMarker(highlightPosition)) {
            highlightPosition++;
            scrollMemorization();
        }
        else if (highlightPosition == 2) {
            Bus.getBus().onEnablePrev();
        }

        notifyDataSetChanged();
    }



    @Override
    public void onPrevMemoryCell() {
        onHighlightPrev();
    }

    @Override
    public void onNextMemoryCell() {
        onHighlightNext();
    }

    @Override
    public void onPositionChange(int newPosition) {
        highlightPosition = newPosition;
    }







    /////////// View Constructors ////////////

    private View getRowMarkerView(int position, View convertView) {
        MemoryCell view;
        if (convertView == null || !(convertView instanceof  MemoryCell)) {
            view = new MemoryCell(context, null);
        }
        else
            view = (MemoryCell) convertView;

        view.setAsRowMarker();
        view.setText(String.format(Locale.getDefault(), "%d", memoryData.getRowNumber(position)));

        return view;
    }

    private View getEmptyView(View convertView) {
        if (convertView == null) {
            convertView = new View(context);
            convertView.setVisibility(View.INVISIBLE);
            return convertView;
        }
        else {
            convertView.setVisibility(View.INVISIBLE);
            return convertView;
        }
    }

    private View getViewPreMemorization(View convertView) {
        MemoryCell view;
        if (convertView == null || !(convertView instanceof MemoryCell)) {
            view = new MemoryCell(context, null);
        }
        else
            view = (MemoryCell) convertView;

        view.setAsHiddenDataCell();
        return view;
    }

    private View getViewMemorization(int position, View convertView) {
        MemoryCell view;

        if (convertView == null || !(convertView instanceof MemoryCell)) {
            view = new MemoryCell(context, null);
            view.setGravity(Gravity.CENTER);
        }
        else
            view = (MemoryCell) convertView;

        view.setVisibility(View.VISIBLE);
        view.setAsDataCell();
        view.setText(memoryData.getText(position));

        if (position == highlightPosition) {
            view.setSelected(true);
        }
        else {
            view.setSelected(false);
        }

        return view;
    }

    private View getViewRecall(int position, View convertView) {
        RecallCell view;
        if (convertView == null || !(convertView instanceof RecallCell)) {
            view = new RecallCell(context, null);
        }
        else
            view = (RecallCell) convertView;

        view.setPosition(position);
        view.setHighlightedPosition(highlightPosition);
        view.setNumDigitsPerCell(memoryData.numDigitsAtCell(position));
        view.finalizeSetup();
        view.setText(recallData.getText(position));

        if (position == highlightPosition) {
            view.requestFocus();
            view.setSelection(view.getText().length());
        }
        else {
            view.clearFocus();
        }

        return view;
    }

    private View getViewReview(int position, View convertView) {
        ReviewCell view;
        if (convertView == null || !(convertView instanceof ReviewCell)) {
            view = new ReviewCell(context, null);
        }
        else
            view = (ReviewCell) convertView;

        view.setModel(memoryData.getText(position), recallData.getText(position));

        return view;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        boolean isViewRowMarker = memoryData.isRowMarker(position);

        if (isViewRowMarker)
        {
            return getRowMarkerView(position, convertView);
        }

        if (position > memoryData.getMaxValidHighlightPosition()) {
            return getEmptyView(convertView);
        }

        if (Bus.gameState == GameState.PRE_MEMORIZATION) {
            return getViewPreMemorization(convertView);
        }

        if (Bus.gameState == GameState.MEMORIZATION) {
            return getViewMemorization(position, convertView);
        }

        if (Bus.gameState == GameState.RECALL || Bus.gameState == GameState.REVIEW) {
            if (recallData.isReviewCell(position))
                return getViewReview(position, convertView);
            else
                return getViewRecall(position, convertView);
        }

        return null;
    }


    void setMemoryData(GridData memoryData) {
        this.memoryData = memoryData;
    }

    void setRecallData(RecallData recallData) {
        this.recallData = recallData;
    }

    void setHighlightPosition(int highlightPosition) {
        this.highlightPosition = highlightPosition;
    }

    void setNumGridColumns(int numColumns) {
        gridView.setNumColumns(numColumns);
    }

    void scrollToTop() {
        gridView.scrollToTop();
    }

    void refresh() {
        notifyDataSetChanged();
    }







    ///////////// Recall Methods /////////////

    @Override
    public void onRowFilled() { }

    @Override
    public void onPrevRecallCell() {
        onHighlightPrev();
    }

    @Override
    public void onNextRecallCell() {
        onHighlightNext();
    }

    @Override
    public void onNextRow() {
        moveHighlightPositionToNextRow(this.highlightPosition);
    }

    @Override
    public void onSubmitRow() {
        recallData.onSubmitRow(recallData.getRow(highlightPosition));
        notifyDataSetChanged();

        if (recallData.allRowsSubmitted()) {
            dispatchRecallCompleteEvent();
        }
        else {
            moveHighlightPositionToNextRow(this.highlightPosition);
        }
    }

    @Override
    public void onSubmitAllRows() {
        recallData.submitAll();
        notifyDataSetChanged();
        dispatchRecallCompleteEvent();
    }

    private void dispatchRecallCompleteEvent() {
        Bus.result.setMemoryData(memoryData);
        Bus.result.setRecallData(recallData);
        Bus.result.runCalculations();

        Bus.getBus().onRecallComplete(Bus.result);
    }

    @Override
    public void onKeyPress(char digit) {
        if (recallData.isReviewCell(highlightPosition))
            return;

        recallData.onKeyPress(digit, highlightPosition, gridView.getCursorStart(highlightPosition), gridView.getCursorEnd(highlightPosition));
        notifyDataSetChanged();
    }

    @Override
    public void onBackSpace() {
        if (recallData.isReviewCell(highlightPosition))
            return;

        recallData.onBackSpace(highlightPosition);
        notifyDataSetChanged();
    }

    @Override
    public void onRestoreInstanceState(Bundle inState) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("NumberGridAdapter.highlightPosition", highlightPosition);
        Bus.memoryData = memoryData;
        Bus.recallData = recallData;
    }
}