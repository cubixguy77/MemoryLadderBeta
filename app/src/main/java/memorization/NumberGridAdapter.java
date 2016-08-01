package memorization;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;

import recall.RecallCell;
import review.ReviewCell;
import speednumbers.mastersofmemory.challenges.domain.model.Challenge;

public class NumberGridAdapter extends BaseAdapter implements GameStateListener {

    private Context context;
    private GridData data;
    private int highlightPosition = 1;
    private RecallData recallData;

    public NumberGridAdapter(Context context, GridData data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.getNumCells();
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
        System.out.println("Move highlight to " + (highlightPosition - 1));
        highlightPosition--;

        if (highlightPosition < 1) {
            highlightPosition = 1;
        }
        else if (data.isRowMarker(highlightPosition)) {
            highlightPosition--;
        }

        notifyDataSetChanged();
    }

    public void onHighlightNext() {
        System.out.println("Move highlight to " + (highlightPosition + 1));
        highlightPosition++;
        if (data.isRowMarker(highlightPosition)) {
            highlightPosition++;
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
        view.setText(Integer.toString(data.getRowNumber(position)));

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
        view.setText(data.getText(position));

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

        System.out.println("set view pos: " + position + "  to value " + recallData.getText(position));

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

        view.setModel(data.getText(position), recallData.getText(position));

        return view;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        boolean isViewRowMarker = data.isRowMarker(position);
        if (isViewRowMarker)
        {
            return getRowMarkerView(position, convertView);
        }

        if (GameStateDispatch.gameSate == GameState.PRE_MEMORIZATION) {
            return getViewPreMemorization(convertView);
        }

        if (GameStateDispatch.gameSate == GameState.MEMORIZATION) {
            return getViewMemorization(position, convertView);
        }

        if (GameStateDispatch.gameSate == GameState.RECALL) {
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
        System.out.println("Adapter: Transition to RECALL");
        highlightPosition = 1;
        recallData = new RecallData(500, 2);
        recallData.setAdapter(this);
        notifyDataSetChanged();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
    }

    @Override
    public void onNextRow() {

    }

    @Override
    public void onSubmitRow() {
        recallData.onSubmitRow(recallData.getRow(highlightPosition));
        onHighlightNext();
        notifyDataSetChanged();

    }
}