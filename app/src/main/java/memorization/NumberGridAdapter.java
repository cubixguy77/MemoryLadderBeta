package memorization;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;

import recall.RecallCell;
import review.Result;
import review.ReviewCell;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public class NumberGridAdapter extends BaseAdapter implements GameStateListener, GridEvent.Memory.UserEvents, GridEvent.Recall {

    private Context context;
    private GridData memoryData;
    private RecallData recallData;
    private int highlightPosition = 1;
    private Challenge challenge;

    public NumberGridAdapter(Context context)
    {
        this.context = context;
        Bus.getBus().subscribe(this);
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

    public void onHighlightNext() {
        highlightPosition++;
        if (highlightPosition >= memoryData.getNumCells() - 1) {
            Bus.getBus().onDisableNext();
        }
        else if (memoryData.isRowMarker(highlightPosition)) {
            highlightPosition++;
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
        view.setText(recallData.getText(position));
        view.addRecallTextWatcher(recallData);

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
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
    }

    @Override
    public void onRecallComplete(Result result) {

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
    public void onNextRow() {
        onHighlightNext();
    }

    @Override
    public void onSubmitRow() {
        recallData.onSubmitRow(recallData.getRow(highlightPosition));
        if (recallData.allRowsSubmitted()) {
            Bus.getBus().onRecallComplete(new Result(memoryData, recallData));
        }
        else {
            onHighlightNext();
        }

        notifyDataSetChanged();
    }
}