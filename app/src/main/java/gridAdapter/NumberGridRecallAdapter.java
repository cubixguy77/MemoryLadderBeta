package gridAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Locale;

import memorization.Bus;
import memorization.GameState;
import memorization.GridEvent;
import memorization.MemoryCell;
import memorization.NumberGridView;
import memorization.NumberMemoryModel;
import recall.RecallCell;
import recall.RecallKeyboardActionPresenter;
import review.ReviewCell;

class NumberGridRecallAdapter extends BaseAdapter implements GridEvent.Recall.Grid {

    private NumberGridView gridView;
    private NumberMemoryModel model;
    private RecallKeyboardActionPresenter recallKeyboardActionPresenter;

    NumberGridRecallAdapter()
    {
        this.model = new NumberMemoryModel();

        new NumberGridGlobalStateChangePresenter(this, this.model);
        this.recallKeyboardActionPresenter = new RecallKeyboardActionPresenter(this, this.model);
    }

    public void setGridView(NumberGridView gridView) {
        this.gridView = gridView;
    }

    NumberMemoryModel getModel() {
        return this.model;
    }

    @Override
    public int getCount() {
        return model.getMemoryData() == null ? 0 : model.getMemoryData().getNumCells();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /* View Construction */

    private View getRowMarkerView(int position, View convertView, Context context) {
        MemoryCell view;
        if (convertView == null || !(convertView instanceof  MemoryCell)) {
            view = new MemoryCell(context, null);
        }
        else
            view = (MemoryCell) convertView;

        view.setAsRowMarker();
        view.setText(String.format(Locale.getDefault(), "%d", model.getMemoryData().getRowNumber(position)));

        return view;
    }

    private View getEmptyView(View convertView, Context context) {
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

    private View getViewRecall(int position, View convertView, Context context) {
        RecallCell view;
        if (convertView == null || !(convertView instanceof RecallCell)) {
            view = new RecallCell(context, null);
        }
        else
            view = (RecallCell) convertView;

        view.setPosition(position);
        view.setPositionChangeListener(this.recallKeyboardActionPresenter);
        view.setNumDigitsPerCell(model.getMemoryData().numDigitsAtCell(position));
        view.finalizeSetup();
        view.setText(model.getRecallData().getText(position));

        if (position == model.getHighlightPosition()) {
            view.requestFocus();
            view.setSelection(view.getText().length());
        }
        else {
            view.clearFocus();
        }

        return view;
    }

    private View getViewReview(int position, View convertView, Context context) {
        ReviewCell view;
        if (convertView == null || !(convertView instanceof ReviewCell)) {
            view = new ReviewCell(context, null);
        }
        else
            view = (ReviewCell) convertView;

        view.setModel(model.getMemoryData().getText(position), model.getRecallData().getText(position));

        return view;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        boolean isViewRowMarker = model.getMemoryData().isRowMarker(position);
        if (isViewRowMarker)
        {
            return getRowMarkerView(position, convertView, context);
        }

        if (position > model.getMemoryData().getMaxValidHighlightPosition()) {
            return getEmptyView(convertView, context);
        }

        if (Bus.gameState == GameState.RECALL || Bus.gameState == GameState.REVIEW) {
            if (model.getRecallData().isReviewCell(position))
                return getViewReview(position, convertView, context);
            else
                return getViewRecall(position, convertView, context);
        }

        return null;
    }

    /* End View Construction */

    /* Grid Events - Recall */

    @Override
    public void scrollDown() {
        gridView.scrollDown();
    }

    @Override
    public void scrollToTop() {
        gridView.scrollToTop();
    }

    @Override
    public void setNumGridColumns(int numGridColumns) {
        gridView.setNumColumns(numGridColumns);
    }

    @Override
    public int getCursorStart() {
        return gridView.getCursorStart(model.getHighlightPosition());
    }

    @Override
    public int getCursorEnd() {
        return gridView.getCursorEnd(model.getHighlightPosition());
    }

    @Override
    public int getLastVisiblePosition() {
        return gridView.getLastVisiblePosition();
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }

    /* End Grid Events - Recall */
}