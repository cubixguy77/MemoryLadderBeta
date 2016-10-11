package memorization;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Toast;

import recall.PositionChangeListener;
import recall.RecallCell;
import recall.RecallData;
import review.Result;
import review.ReviewCell;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public class NumberGridAdapter extends BaseAdapter implements GameStateListener, GridEvent.Memory.UserEvents, GridEvent.Recall, PositionChangeListener {

    private Context context;
    private GridData memoryData;
    private RecallData recallData;
    private int highlightPosition = 1;
    private Challenge challenge;
    private NumberGridView gridView;

    public NumberGridAdapter(Context context)
    {
        this.context = context;
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

    public void onRowFilled() {
        System.out.println("Row filled");
    }

    public void onHighlightPrev() {
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

    public void onHighlightNext() {
        highlightPosition++;
        if (highlightPosition >= memoryData.getNumCells() - 1) {
            highlightPosition = memoryData.getNumCells() - 1;
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

    public void onHighlightPosition(int position, boolean forward) {
        this.highlightPosition = position;
        if (forward)
            onHighlightNext();
        else
            onHighlightPrev();
    }

    private View getRowMarkerView(int position, View convertView) {
        MemoryCell view;
        if (convertView == null) {
            view = new MemoryCell(context, null);
        }
        else if (convertView instanceof RecallCell || convertView instanceof ReviewCell)
            view = new MemoryCell(context, null);
        else
            view = (MemoryCell) convertView;

        view.setAsRowMarker();
        view.setText(Integer.toString(memoryData.getRowNumber(position)));

        return view;
    }

    public View getViewMemorization(int position, View convertView) {
        MemoryCell view;
        if (convertView == null) {
            view = new MemoryCell(context, null);
            view.setGravity(Gravity.CENTER);
        }
        else
            view = (MemoryCell) convertView;

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

    public View getViewRecall(int position, View convertView) {
        RecallCell view;
        if (convertView == null) {
            view = new RecallCell(context, null);
        }
        else if (convertView instanceof MemoryCell || convertView instanceof ReviewCell)
            view = new RecallCell(context, null);
        else
            view = (RecallCell) convertView;

        view.setPosition(position);
        view.setHighlightedPosition(highlightPosition);
        view.setNumDigitsPerCell(memoryData.getNumDigitsPerColumn());
        view.finalizeSetup();
        view.setText(recallData.getText(position));
        view.setRecallFocusChangeListener(this);

        if (position == highlightPosition) {
            view.requestFocus();
            view.setSelection(view.getText().length());
        }
        else {
            view.clearFocus();
        }

        return view;
    }

    public View getViewReview(int position, View convertView) {
        ReviewCell view;
        if (convertView == null) {
            view = new ReviewCell(context, null);
        }
        else if (convertView instanceof MemoryCell || convertView instanceof  RecallCell)
            view = new ReviewCell(context, null);
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

        if (Bus.gameSate == GameState.PRE_MEMORIZATION) {
            return getViewPreMemorization(convertView);
        }

        if (Bus.gameSate == GameState.MEMORIZATION) {
            return getViewMemorization(position, convertView);
        }

        if (Bus.gameSate == GameState.RECALL) {
            if (recallData.isReviewCell(position))
                return getViewReview(position, convertView);
            else
                return getViewRecall(position, convertView);
        }

        return null;
    }

    private View getViewPreMemorization(View convertView) {
        MemoryCell view;
        if (convertView == null) {
            view = new MemoryCell(context, null);
        }
        else
            view = (MemoryCell) convertView;

        view.setAsHiddenDataCell();
        return view;
    }




    @Override
    public void onLoad(Challenge challenge) {
        this.challenge = challenge;
        memoryData = new GridData(challenge);
        memoryData.loadData();
        memoryData.setAdapter(this);
        notifyDataSetChanged();
    }

    @Override
    public void onMemorizationStart() {
        highlightPosition = 1;
        notifyDataSetChanged();
    }

    @Override
    public void onTimeExpired() {

    }

    @Override
    public void onTransitionToRecall() {
        highlightPosition = 1;
        recallData = new RecallData(challenge);
        recallData.setAdapter(this);

        notifyDataSetChanged();

        gridView.scrollToTop();
    }

    @Override
    public void onRecallComplete(Result result) {
    }

    @Override
    public void onPlayAgain() {

    }



    @Override
    public void onPrev() {
        onHighlightPrev();
    }

    @Override
    public void onNext() {
        onHighlightNext();
    }

    @Override
    public void onPositionChange(int newPosition) {
        highlightPosition = newPosition;
    }




    ///////////// Recall Methods /////////////

    @Override
    public void onNextRow() {
        moveHighlightPositionToNextRow(this.highlightPosition);
    }

    @Override
    public void onSubmitRow() {
        recallData.onSubmitRow(recallData.getRow(highlightPosition));
        notifyDataSetChanged();

        if (recallData.allRowsSubmitted()) {
            Bus.getBus().onRecallComplete(new Result(memoryData, recallData));
        }
        else {
            moveHighlightPositionToNextRow(this.highlightPosition);
        }
    }

    @Override
    public void onSubmitAllRows() {
        recallData.submitAll();
        notifyDataSetChanged();
        Bus.getBus().onRecallComplete(new Result(memoryData, recallData));
    }

    @Override
    public void onKeyPress(int digit) {
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
}